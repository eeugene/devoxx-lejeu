package fr.aneo.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by eeugene on 08/03/2017.
 */
public interface EventStoreRepository extends JpaRepository<BattleFinished, Long> {
    @Query("from BattleFinished e where e.time BETWEEN :startDate AND :endDate")
    List<BattleFinished> findAllOfToday(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
