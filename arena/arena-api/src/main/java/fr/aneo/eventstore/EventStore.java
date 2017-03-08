package fr.aneo.eventstore;

import fr.aneo.domain.BattleResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Created by eeugene on 08/03/2017.
 */
@Service
public class EventStore {

    @Autowired
    EventStoreRepository eventStoreRepository;

    public void store(BattleResults battleResults) {
        System.out.println("event store: start persist");
        eventStoreRepository.save(
                battleResults.getResults().stream().map(br ->
                BattleState.builder()
                        .hero1Email(br.getHero1().getEmail())
                        .hero2Email(br.getHero2().getEmail())
                        .player1Won(br.isPlayer1Won())
                        .time(br.getTime()).build())
                .collect(Collectors.toList())
        );
    }
}
