package fr.aneo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@Builder
public class Hero {
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    private String nickname;
    private long avatarId;
    private int attackLevel;
    private int hpLevel;
    private String currentBonus;
    private Date currentBonusCreationTime;
    private Date registerTime;

    private int wins;
    private int losses;
    private int rank;
    private int bestRanking;
    private String lastFiveBattles;
    private long totalQuizzAnswer;
    private long totalGoodQuizzAnswer;

}
