package fr.aneo.game.model;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
public class Hero {

    @Id
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

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
