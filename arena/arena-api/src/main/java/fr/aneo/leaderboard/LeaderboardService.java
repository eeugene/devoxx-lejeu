package fr.aneo.leaderboard;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import fr.aneo.eventstore.HeroStatsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.support.FallbackCommand;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 05/03/2017.
 */
@Service
@Slf4j
public class LeaderboardService {

    LeaderboardApi leaderboardApi;
    @Value("${leaderboardUrl}")
    private String leaderboardUrl;
    @Autowired
    HeroStatsView heroStatsView;

    public LeaderboardService() {
        FallbackFactory<? extends LeaderboardApi> fallbackFactory =
                cause -> leaderBoard -> new LeaderBoard(Collections.emptyList());
        leaderboardApi = HystrixFeign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate requestTemplate) {
                        log.debug(requestTemplate.toString());
                    }
                })
                .target(LeaderboardApi.class, leaderboardUrl, fallbackFactory);
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
        if (log.isDebugEnabled()) {
            printLeaderboard(leaderBoard);
        }
    }

    public void printLeaderboard(LeaderBoard leaderBoard) {
        log.debug("-- LEADERBOARD --\n" );
        int i = 0;
        for (LeaderBoardLine entry: leaderBoard.getList()) {
            log.debug(
                    (++i) + "- " + entry.getHeroName() + " - " + entry.getWinRatio()
            );
        }
    }
}
