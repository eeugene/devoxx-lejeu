package fr.aneo.api.leaderboard;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by eeugene on 03/03/2017.
 */
public interface LeaderboardApi {

    @RequestLine("POST /update")
    @Headers("Content-Type: application/json")
    void update(LeaderBoard leaderBoard);
}
