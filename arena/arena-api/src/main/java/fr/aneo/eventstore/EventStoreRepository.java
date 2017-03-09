package fr.aneo.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by eeugene on 08/03/2017.
 */
public interface EventStoreRepository extends JpaRepository<BattleFinished, Long> {
}
