package fr.aneo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
public class StreamController {
    EmitterProcessor<LeaderBoard> stream;
    Flux<LeaderBoard> updates;

    public StreamController() {
        stream = EmitterProcessor.<LeaderBoard>create().connect();
        updates = stream.publishOn(Schedulers.newSingle("schedulor"));
    }

    @GetMapping(value = "/stream")
    Flux<LeaderBoard> stream() {
        return updates;
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public void update(@RequestBody LeaderBoard leaderBoard) {
        System.out.println("receive update " + leaderBoard);
        stream.onNext(leaderBoard);
    }
}