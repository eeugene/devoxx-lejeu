package fr.aneo.eventstore;

import fr.aneo.domain.BattleResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
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
        return eventStoreRepository.findAll();
    }
}
