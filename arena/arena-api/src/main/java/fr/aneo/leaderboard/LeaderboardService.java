package fr.aneo.leaderboard;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.eventstore.HeroStatsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
@Slf4j
public class LeaderboardService {

    LeaderboardApi leaderboardApi;
    @Autowired
    HeroStatsView heroStatsView;

    public LeaderboardService() {
        leaderboardApi = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate requestTemplate) {
                        log.info(requestTemplate.toString());
                    }
                })
                .target(LeaderboardApi.class, "http://localhost:8081");
    }

    public void updateLeaderboard() {
        List<LeaderBoardLine> list = heroStatsView.getStats()
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().getWinRatio().compareTo(o1.getValue().getWinRatio()))
                .limit(20)
                .map(kv -> new LeaderBoardLine(kv.getKey().getEmail(), kv.getKey().getName(), kv.getValue().getWinRatio()))
                .collect(Collectors.toList());

        LeaderBoard leaderBoard = new LeaderBoard(list);
        leaderboardApi.update(leaderBoard);
        printLeaderboard(leaderBoard);
    }

    public void printLeaderboard(LeaderBoard leaderBoard) {
        log.info("-- LEADERBOARD --\n" );
        int i = 0;
        for (LeaderBoardLine entry: leaderBoard.getList()) {
            log.info(
                    (++i) + "- " + entry.getHeroName() + " - " + entry.getWinRatio()
            );
        }
    }
}
