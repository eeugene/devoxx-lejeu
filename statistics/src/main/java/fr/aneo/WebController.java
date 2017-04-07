package fr.aneo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.aneo.model.CountByHour;
import fr.aneo.service.BattleStatsService;
import fr.aneo.service.HeroStatsService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @ResponseBody
    @GetMapping("/topByDay/{stringDate}")
    List<String> rankingOfDay(@PathVariable String stringDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate date = LocalDate.parse(stringDate, dtf);
        List<String> rankingOfDay = battleStatsService.getPointsOfHeros(date)
                .entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .map(entry -> {
                    try {
                        return new JSONObject().put(entry.getKey().getEmail(), entry.getValue()).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(o -> Objects.nonNull(o))
                .collect(Collectors.toList());
        return rankingOfDay;
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
