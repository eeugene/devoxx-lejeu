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

    @ManyToOne
    @JoinColumn(name = "AVATAR_ID", insertable = false, updatable = false)
    @JsonIgnore
    private Avatar avatar;

    @Column(name = "AVATAR_ID")
    private long avatarId;

    @Embedded
    private HeroStats heroStats = new HeroStats();
}
