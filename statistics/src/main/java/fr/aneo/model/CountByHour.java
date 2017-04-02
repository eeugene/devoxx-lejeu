package fr.aneo.model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by eeugene on 02/04/2017.
 */
@Data
@Builder
public class CountByHour {
    private int hour;
    private long count;
}
