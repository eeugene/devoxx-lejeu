package fr.aneo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aneo.model.CountByHour;
import fr.aneo.service.BattleStatsService;
import fr.aneo.service.HeroStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by eeugene on 02/04/2017.
 */
@Controller
@Slf4j
public class WebController {

    @Autowired
    private HeroStatsService heroStatsService;
    @Autowired
    private BattleStatsService battleStatsService;
    private static final ObjectMapper om = new ObjectMapper();

    @GetMapping(path = "/")
    String index(Map<String, Object> model) {
        List<CountByHour> heroCountByHour = heroStatsService.getHeroCount();
        Long totalHero = heroCountByHour.stream().map(c -> c.getCount()).collect(Collectors.summingLong(x -> x));
        model.put("totalHero", totalHero);
        model.put("heroCount", convertObjectToJson(heroCountByHour));
        model.put("battleCount", convertObjectToJson(battleStatsService.getBattleCount()));
        model.put("heroTop5", convertObjectToJson(heroStatsService.getTopFive()));
        return "index";
    }

    public static String convertObjectToJson(Object obj) {
        String result = "";
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error In JSON conversion : {}", e);
        }
        return result;
    }
}
