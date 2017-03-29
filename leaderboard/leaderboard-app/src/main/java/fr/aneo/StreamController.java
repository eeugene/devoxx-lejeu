package fr.aneo;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Log4j
@RestController
public class StreamController {
    SseEmitter sseEmitter;

    public StreamController() {
        sseEmitter = new SseEmitter(Long.MAX_VALUE);
    }

    @GetMapping(value = "/stream")
    SseEmitter stream() {
        return sseEmitter;
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public void update(@RequestBody LeaderBoard leaderBoard) throws IOException {
        log.info("receive update " + leaderBoard);
        sseEmitter.send(leaderBoard);
    }
}