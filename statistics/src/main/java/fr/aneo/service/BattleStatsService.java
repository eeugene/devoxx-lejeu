package fr.aneo.service;

import fr.aneo.model.Battle;
import fr.aneo.model.CountByHour;
import fr.aneo.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 02/04/2017.
 */
@Service
public class BattleStatsService {

    @Autowired
    @Qualifier("jdbcArena")
    JdbcTemplate arenaJdbcTemplate;

    @Autowired
    @Qualifier("jdbcHero")
    JdbcTemplate heroJdbcTemplate;

    public List<CountByHour> getBattleCount() {
        String sql = "select time from battle_finished where time >= ?";
        java.util.Date startDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<LocalDateTime> dates = arenaJdbcTemplate.queryForList(sql, new Date[]{
                new Date(startDate.getTime()),
        }, LocalDateTime.class);

        Map<Integer, Long> collect = dates.stream()
                .collect(
                        Collectors.groupingBy(d -> d.getHour(), Collectors.counting())
                );

        return collect.entrySet().stream().map(e -> CountByHour.builder().hour(e.getKey()).count(e.getValue()).build()).collect(Collectors.toList());
    }

    public List<String> getAllHeros(LocalDate asOfDay) {
        String sql =
                "select distinct hero1_email " +
                        "from battle_finished " +
                        "where time between ? and ?";
        java.util.Date startDate = Date.from(asOfDay.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        java.util.Date endDate = Date.from(asOfDay.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<String> herosEmail = arenaJdbcTemplate.queryForList(sql,
                new Object[]{
                        new Date(startDate.getTime()),
                        new Date(endDate.getTime()),
                }, String.class
        );
        return herosEmail;
    }

    public Map<Hero, Double> getPointsOfHeros(LocalDate asOfDay) {
        List<String> heros = getAllHeros(asOfDay);
        return heros.stream()
                .map(email -> getHero(email))
                .collect(Collectors.toMap(h -> h, h -> ratio(getBattlesOfHero(h.getEmail(), asOfDay))));
    }

    Hero getHero(String email) {
        String sql = "select h.EMAIL, h.FIRSTNAME, h.LASTNAME, h.NICKNAME, h.ROLE, h.WIN, h.LOSS, h.RANK from HERO h where h.ROLE = ? and h.EMAIL = ?";
        return heroJdbcTemplate.queryForObject(sql,
                new Object[]{
                        "PLAYER",
                        email
                },
                (ResultSet res, int rowNum) ->
                        Hero.builder()
                                .email(res.getString(1))
                                .firstname(res.getString(2))
                                .lastname(res.getString(3))
                                .nickname(res.getString(4))
                                .role(res.getString(5))
                                .wins(res.getInt(6))
                                .losses(res.getInt(7))
                                .rank(res.getInt(8))
                                .build()
        );
    }

    double ratio(List<Battle> battles) {
        return (double) battles.stream().filter(b -> b.getHero1Won()).count() / battles.size();
    }

    public List<Battle> getBattlesOfHero(String email, LocalDate asOfDay) {
        String sql =
                "select hero1_email,hero2_email,hero1_won,time from battle_finished " +
                        "where hero1_email = ? and time between ? and ?";
        java.util.Date startDate = Date.from(asOfDay.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        java.util.Date endDate = Date.from(asOfDay.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<Battle> battles = arenaJdbcTemplate.query(sql,
                new Object[]{
                        email,
                        new Date(startDate.getTime()),
                        new Date(endDate.getTime()),
                }
                , (ResultSet res, int rowNum) ->
                        Battle.builder()
                                .hero1Email(res.getString(1))
                                .hero2Email(res.getString(2))
                                .hero1Won(res.getBoolean(3))
                                .time(res.getDate(4)).build()
        );
        return battles;
    }
}
