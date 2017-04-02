package fr.aneo.service;

import fr.aneo.model.CountByHour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private long battleCount;

    public List<CountByHour> getBattleCount() {

        String sql = "select time from battle_finished where time >= ?";

        java.util.Date startDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        //java.util.Date endDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        //System.out.println(startDate + " " + endDate);
        List<LocalDateTime> dates = arenaJdbcTemplate.queryForList(sql, new Date[]{
                new Date(startDate.getTime()),
        }, LocalDateTime.class);

        Map<Integer, Long> collect = dates.stream()
                .collect(
                        Collectors.groupingBy(d -> d.getHour(), Collectors.counting())
                );

        return collect.entrySet().stream().map(e -> CountByHour.builder().hour(e.getKey()).count(e.getValue()).build()).collect(Collectors.toList());
    }


}
