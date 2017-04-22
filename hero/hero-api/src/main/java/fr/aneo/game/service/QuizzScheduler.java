package fr.aneo.game.service;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import fr.aneo.game.model.Quizz;
import fr.aneo.game.repository.QuizzRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 17/04/2017.
 */
@Slf4j
public class QuizzScheduler extends AbstractLoggingActor {
    private Quizz currentQuizz;
    private QuizzProperties quizzProperties;
    private QuizzRepository quizzRepository;
    private HeroService heroService;
    private boolean quizzSchedulerStarted = false;

    public QuizzScheduler(QuizzProperties quizzProperties, QuizzRepository quizzRepository, HeroService heroService) {
        this.quizzProperties = quizzProperties;
        this.quizzRepository = quizzRepository;
        this.heroService = heroService;
    }

    @Override
    public void preStart() throws Exception {
        if (quizzProperties.isSimuMode()) {
            startQuizzScheduler();
        } else {
            getSelf().tell(new StartQuizzScheduler(), ActorRef.noSender());
        }
    }

    private void startQuizzScheduler() {
        quizzSchedulerStarted = true;
        log.info("boom, start quizz at " + LocalTime.now());
        FiniteDuration interval = quizzProperties.isSimuMode() ? Duration.create(quizzProperties.getSimuInterval(), TimeUnit.valueOf(quizzProperties.getSimuIntervalTimeUnit())) : Duration.create(quizzProperties.getInterval(), TimeUnit.valueOf(quizzProperties.getIntervalTimeUnit()));
        getContext().system().scheduler().schedule(Duration.Zero(), interval, getSelf(), new QuizzScheduler.ChangeCurrentQuizz(), getContext().system().dispatcher(), ActorRef.noSender());
    }

    private void stopQuizz() {
        log.info("Stopping quizz...");
        getContext().system().stop(this.getSelf());
    }

    private boolean quizzExpired() {
        return LocalTime.now().getHour() >= quizzProperties.getEndAt();
    }

    private void startQuizz() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (!quizzSchedulerStarted) {
            if (hour > quizzProperties.getStartAt()) {
                startQuizzScheduler();
            } else {
                getContext().system().scheduler().scheduleOnce(Duration.create(1, TimeUnit.MINUTES), getSelf(), new StartQuizzScheduler(), getContext().system().dispatcher(), ActorRef.noSender());
            }
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ChangeCurrentQuizz.class, c -> {
                    changeCurrentQuizz();
                    if (!quizzProperties.isSimuMode() && quizzExpired()) {
                        stopQuizz();
                    }
                })
                .match(GetCurrentQuizz.class, c -> getSender().tell(getCurrentQuizz(), getSelf()))
                .match(StartQuizzScheduler.class, c -> {
                    if (quizzExpired()) {
                        stopQuizz();
                    } else {
                        startQuizz();
                    }
                })
                .match(StopQuizzScheduler.class, c -> stopQuizz())
                .build();
    }

    private void changeCurrentQuizz() {
        if (currentQuizz != null && currentQuizz.isActive()) {
            currentQuizz.setActive(false);
            quizzRepository.save(currentQuizz);
        }
        this.currentQuizz = getNextAvailableQuizz();
        heroService.resetBonusForAll();
    }

    private Optional<Quizz> getCurrentQuizz() {
        return Optional.ofNullable(currentQuizz);
    }

    private Quizz getNextAvailableQuizz() {
        Quizz quizz;
        List<Quizz> quizzes = quizzRepository.findAll()
                .stream().filter(q -> q.isActive() && q.isBonus()).collect(Collectors.toList());

        if (quizzes.isEmpty()) { // no bonus quizz, try normal quizz
            quizzes = quizzRepository.findAll()
                    .stream().filter(q -> q.isActive() && !q.isBonus()).collect(Collectors.toList());
        }
        quizz = getRandomQuizz(quizzes);
        log.info("Found NextQuizz: " + quizz);
        return quizz;
    }

    private Quizz getRandomQuizz(List<Quizz> quizzes) {
        if (quizzes == null || quizzes.isEmpty()) {
            return null;
        }
        int size = quizzes.size();
        int randomIndex = size > 1 ? RandomUtils.nextInt(size) : 0;
        return quizzes.get(randomIndex);
    }

    public static class StopQuizzScheduler {
    }

    public static class StartQuizzScheduler {
    }

    public static class ChangeCurrentQuizz {
    }

    public static class GetCurrentQuizz {
    }

    public static Props props(QuizzProperties quizzProperties, QuizzRepository quizzRepository, HeroService heroService) {
        // You need to specify the actual type of the returned actor
        // since Java 8 lambdas have some runtime type information erased
        return Props.create(QuizzScheduler.class, () -> new QuizzScheduler(quizzProperties, quizzRepository, heroService));
    }
}
