package fr.aneo.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

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

    @JsonIgnore
    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotNull
    @Enumerated(STRING)
    private Role role;

    @NotBlank
    private String nickname;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "AVATAR_ID")
    private Avatar avatar;

    @Embedded
    private HeroStats heroStats;
}
