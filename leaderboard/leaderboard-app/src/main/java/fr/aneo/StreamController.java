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
        //updates.subscribe(n -> System.out.println(n));
    }

    @GetMapping(value = "/stream")
    Flux<LeaderBoard> stream() {
        return updates;
        //return createFlux().concatWith(Flux.never());
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public void update(@RequestBody LeaderBoard leaderBoard) {
        System.out.println("receive update " + leaderBoard);
        stream.onNext(leaderBoard);
    }

//    private Flux<LeaderBoard> createFlux() {
//        return Flux.interval(Duration.ofMillis(1000))
//                .map(i -> getResult())
//                .log()
//                ;
//    }

//    private LeaderBoard getResult() {
//        LeaderBoard leaderBoard = new LeaderBoard();
//        leaderBoard.list.add(new LeaderBoardLine("hero-1", RandomUtils.nextInt(4)));
//        leaderBoard.list.add(new LeaderBoardLine("hero-4", RandomUtils.nextInt(4)));
//        leaderBoard.list.add(new LeaderBoardLine("hero-5", RandomUtils.nextInt(4)));
//        leaderBoard.list.add(new LeaderBoardLine("hero-6", RandomUtils.nextInt(4)));
//        return leaderBoard;
//    }

}