package fr.aneo.eventstore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@Entity
@Builder
public class BattleState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    String hero1Email;
    @NotBlank
    String hero2Email;
    @NotNull
    boolean player1Won;
    @NotNull
    LocalDateTime time;
}