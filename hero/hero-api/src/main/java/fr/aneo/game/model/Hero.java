package fr.aneo.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private static final int DEFAULT_ATTACK_LEVEL = 100;
    private static final int DEFAULT_HP_LEVEL = 100;

    @Id
    @Email
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotNull
    @Enumerated(STRING)
    private Role role = Role.PLAYER;

    @NotBlank
    private String nickname;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "AVATAR_ID", insertable = false, updatable = false)
    private Avatar avatar;

    @Column(name = "AVATAR_ID")
    private long avatarId;

    @NotNull
    @Column(name = "ATTACK")
    private int attackLevel = DEFAULT_ATTACK_LEVEL;

    @NotNull
    @Column(name = "HP")
    private int hpLevel = DEFAULT_HP_LEVEL;

    @Column(name = "BONUS")
    @Enumerated(value = STRING)
    private Bonus currentBonus;

    @Embedded
    private HeroStats heroStats = new HeroStats();

}
