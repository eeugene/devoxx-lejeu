package fr.aneo.eventstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by eeugene on 14/03/2017.
 */
@Component
public class UserFightTotalView implements Consumer<BattleFinished> {

    Map<String, Integer> fightCount = new HashMap<>();

    @Override
    public void accept(BattleFinished battleFinished) {
        fightCount.compute(battleFinished.getHero1Email(), (k, v) -> v == null ? 1 : v+1);
        fightCount.compute(battleFinished.getHero2Email(), (k, v) -> v == null ? 1 : v+1);
    }

    public void init(List<BattleFinished> events ) {
        events.forEach(event -> this.accept(event));
    }

    public Integer getUserTotalFight(String userEmail) {
        return fightCount.get(userEmail);
    }
}
