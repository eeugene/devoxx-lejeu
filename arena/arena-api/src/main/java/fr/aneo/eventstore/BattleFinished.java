package fr.aneo.eventstore;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Entity
public class BattleFinished {
    @Tolerate
    BattleFinished() {}
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "hero1_email")
    @NotBlank String hero1Email;
    @Column(name = "hero2_email")
    @NotBlank String hero2Email;
    @Column(name = "hero1_won")
    @NotNull boolean hero1Won;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull Date time;
}