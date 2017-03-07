package fr.aneo.game.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
public class Hero {

    @Id
    private String email;

    private String password;

    @Enumerated(value = STRING)
    private Role role;

    private String nickname;

    @ManyToOne
    private Avatar avatar;

    @Embedded
    private HeroStats heroStats;
}
