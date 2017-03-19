package fr.aneo.eventstore;

import fr.aneo.domain.BattleResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 08/03/2017.
 */
@Service
@Slf4j
public class EventStore {

    @Autowired
    EventStoreRepository eventStoreRepository;
    @Autowired
    EventPublisher eventPublisher;

    public void saveEvents(BattleResults battleResults) {
        List<BattleFinished> battleFinisheds = battleResults.getResults().stream()
                .map(br ->
                        BattleFinished.builder()
                                .hero1Email(br.getHero1().getEmail())
                                .hero2Email(br.getHero2().getEmail())
                                .hero1Won(br.isHero1Won())
                                .time(Date.from(br.getTime().atZone(ZoneId.systemDefault()).toInstant())).build())
                .collect(Collectors.toList());
        eventStoreRepository.save(battleFinisheds);
        battleFinisheds.forEach(event -> eventPublisher.publish(event));
    }

    public List<BattleFinished> load() {
        Date from = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(LocalDate.now().plus(Period.ofDays(1)).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        log.info(String.format("Loading events between %s and %s", from, to));
        return eventStoreRepository.findAllOfToday(from, to);
    }
}
