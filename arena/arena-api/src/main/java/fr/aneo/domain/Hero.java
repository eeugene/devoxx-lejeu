package fr.aneo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class Hero {
    private String email;
    private String firstname;
    private String lastname;
    private Long avatarId;
    private String nickname;

    private int hpLevel;
    private int attackLevel;
    private String currentBonus;
}
