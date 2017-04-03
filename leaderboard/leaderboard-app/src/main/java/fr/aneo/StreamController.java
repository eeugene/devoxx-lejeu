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

@Log4j
@RestController
public class StreamController {
    SseEmitter sseEmitter;

    public StreamController() {
        sseEmitter = new MySseEmitter(Long.MAX_VALUE);
    }

    @GetMapping(value = "/stream")
    SseEmitter stream() {
        return sseEmitter;
    }

    @PostMapping(value = "/update", consumes = "application/json")
    public void update(@RequestBody LeaderBoard leaderBoard) throws IOException {
        log.debug("receive update " + leaderBoard);
        sseEmitter.send(leaderBoard);
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