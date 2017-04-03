package fr.aneo.api.hero;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by eeugene on 24/03/2017.
 */
@Data
@Builder
public class UpdateHeroStats {
    @Tolerate UpdateHeroStats() {}
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime tournamentStartTime;
    private Collection<NewHeroStats> list;
}
