package fr.aneo.game.model;

import static javax.persistence.EnumType.STRING;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
public class Hero {

    @Id
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(value = STRING)
    private Role role;

    @NotBlank
    private String nickname;

    @ManyToOne
    private Avatar avatar;

    @Embedded
    private HeroStats heroStats;
}
