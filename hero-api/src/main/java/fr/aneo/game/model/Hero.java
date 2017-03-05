package fr.aneo.game.model;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by raouf on 04/03/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hero {

    private String email;

    private String password;

    private String nickname;

    @ManyToOne
    private Avatar avatar;

    @Embedded
    private HeroStats heroStats;
}
