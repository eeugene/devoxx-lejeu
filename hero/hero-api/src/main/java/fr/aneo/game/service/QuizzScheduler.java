package fr.aneo.game.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.*;

@Service
@Slf4j
public class QuizzScheduler {
    private ScheduledExecutorService timer;
    private ScheduledExecutorService quizzScheduler;

    int startAt;
    int endAt;
    int interval;
    boolean inSimuMode;
    boolean quizzSchedulerStarted = false;

    @Autowired
    private QuizzService quizzService;
    @Autowired
    private HeroService heroService;

    public QuizzScheduler(@Value("${quizz.startAt}")int startAt,
                          @Value("${quizz.endAt}")int endAt,
                          @Value("${quizz.intervalInMinutes}")int interval,
                          @Value("${quizz.inSimuMode}")boolean inSimuMode
                          ) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.interval = interval;
        this.inSimuMode = inSimuMode;

        timer = Executors.newSingleThreadScheduledExecutor();
        quizzScheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        if (inSimuMode) {
            ScheduledFuture<?> handle = timer.scheduleAtFixedRate(this::incCurrentQuizz, 60, 60, TimeUnit.SECONDS);
            try {
                handle.get();
            } catch (ExecutionException e) {
                Exception rootException = (Exception) e.getCause();
                rootException.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            timer.scheduleAtFixedRate(this::startQuizzScheduler, 0, 60, TimeUnit.SECONDS);
        }
    }

    private void startQuizzScheduler() {
        if (quizzSchedulerStarted) {
            timer.shutdownNow();
        }
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= endAt) {
            log.info("Exceeded endAt parameter, shutting down quizz...");
            quizzScheduler.shutdownNow();
            timer.shutdownNow();
            return;
        }
        if (hour > startAt && !quizzSchedulerStarted) {
            quizzSchedulerStarted = true;
            log.info("boom, start quizz at " + hour);
            scheduleQuizz();
        }
    }

    private void scheduleQuizz() {
        quizzScheduler.scheduleAtFixedRate(this::incCurrentQuizz, interval, interval, TimeUnit.MINUTES);
    }

    private void incCurrentQuizz() {
        quizzService.changeCurrentQuizz();
        heroService.resetBonusForAll();
    }
}
