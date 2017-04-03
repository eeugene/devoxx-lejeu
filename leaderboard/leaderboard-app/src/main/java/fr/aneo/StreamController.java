package fr.aneo;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Log4j
@RestController
public class StreamController {

    ConcurrentLinkedQueue<SseEmitter> emitters = new ConcurrentLinkedQueue<>();
    LeaderBoard leaderBoard;

    private MySseEmitter createSseEmitter() {
        MySseEmitter mySseEmitter = new MySseEmitter(Long.MAX_VALUE);
        emitters.add(mySseEmitter);
        mySseEmitter.onCompletion(() -> removeEmitter(mySseEmitter));
        return mySseEmitter;
    }

    @GetMapping(value = "/stream")
    SseEmitter stream() {
        MySseEmitter sseEmitter = createSseEmitter();
        try {
            sseEmitter.send(this.leaderBoard);
        } catch (Exception e) {
            log.error(e);
        }
        return sseEmitter;
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public void update(@RequestBody LeaderBoard leaderBoard) {
        log.debug("receive update " + leaderBoard);
        this.leaderBoard = leaderBoard;
        emitters.stream().forEach(e -> {
            try {
                e.send(leaderBoard);
            } catch (Exception e1) {
                log.error(e1);
            }
        });
    }

    void removeEmitter(MySseEmitter mySseEmitter) {
        emitters.remove(mySseEmitter);
    }

    static class MySseEmitter extends SseEmitter {
        public MySseEmitter(long timeout) {
            super(timeout);
        }

        @Override
        protected void extendResponse(ServerHttpResponse outputMessage) {
            super.extendResponse(outputMessage);

            HttpHeaders headers = outputMessage.getHeaders();
            headers.put("X-Accel-Buffering", Arrays.asList("no"));
        }
    }
}