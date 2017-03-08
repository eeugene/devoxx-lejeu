package fr.aneo.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by eeugene on 08/03/2017.
 */
public interface EventStoreRepository extends JpaRepository<BattleState, Long> {
}
