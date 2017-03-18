package fr.aneo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by eeugene on 18/03/2017.
 */
@Service
@Slf4j
public class ArenaScheduler {
    private static final int SECONDS_IN_HOUR = 3600;
    private static final Integer BATTLE_EXEC_RUN_TIME = 1;
    private ScheduledExecutorService battleExecutor;
    private ScheduledExecutorService timer;
    Map<Integer, Integer> schedule = new LinkedHashMap<>();

    @Value("${arena.startAt}")
    int startAt;
    @Value("${arena.endAt}")
    int endAt;
    @Value("${arena.inSimuMode}")
    boolean inSimuMode;

    int currentHour = 0;

    @Autowired
    Arena arena;

    public ArenaScheduler() {
        timer = Executors.newSingleThreadScheduledExecutor();
        battleExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        if (inSimuMode) {
            ScheduledFuture<?> handle = timer.scheduleAtFixedRate(arena::start, 0, 10, TimeUnit.SECONDS);
            try {
                handle.get();
            } catch (ExecutionException e) {
                Exception rootException = (Exception) e.getCause();
                rootException.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        Function<Integer, Integer> model = i -> 5 * (int) Math.pow(2d, i - 9);

        int[] planning = new int[endAt - startAt + 1];
        IntStream.rangeClosed(startAt, endAt).forEach(n -> planning[n - startAt] = model.apply(n));
        IntStream.rangeClosed(startAt, endAt - 1).forEach(n -> schedule.put(n, planning[n - startAt + 1]));

        timer.scheduleAtFixedRate(this::startBattle, 0, 60, TimeUnit.SECONDS);
    }

    public void startBattle() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= endAt) {
            log.info("Exceeded endAt parameter, shutting down arena...");
            battleExecutor.shutdownNow();
            timer.shutdownNow();
            return;
        }
        if (hour > startAt && hour > currentHour) {
            currentHour = hour;
            log.info("boom, start " + battleCountAt(now) + " battles at " + hour);
            scheduleBattles(battleCountAt(now));
        }
    }

    private void scheduleBattles(Optional<Integer> battleCount) {
        if (battleCount.isPresent()) {
            Integer battles = battleCount.get();
            int estimatedExecutionTime = battles * BATTLE_EXEC_RUN_TIME;
            int scheduleAtPeriod = 0;
            if (estimatedExecutionTime < SECONDS_IN_HOUR) {
                scheduleAtPeriod = SECONDS_IN_HOUR / estimatedExecutionTime;
            }
            battleExecutor.scheduleAtFixedRate(arena::start, 0, scheduleAtPeriod, TimeUnit.SECONDS);
        }
    }

    public Optional<Integer> battleCountAt(LocalTime time) {
        int hour = time.getHour();
        Integer integer = schedule.get(hour);
        if (integer != null) {
            return Optional.of(integer);
        }
        return Optional.empty();
    }

}
