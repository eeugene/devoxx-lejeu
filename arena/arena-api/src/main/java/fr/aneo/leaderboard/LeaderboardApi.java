package fr.aneo.leaderboard;

import feign.Headers;
import feign.RequestLine;
import fr.aneo.domain.BattleResults;
import fr.aneo.domain.Hero;

import java.util.List;

/**
 * Created by eeugene on 03/03/2017.
 */
public interface LeaderboardApi {

    @RequestLine("POST /update")
    @Headers("Content-Type: application/json")
    void update(LeaderBoard leaderBoard);
}
