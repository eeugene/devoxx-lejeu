package fr.aneo.domain;

import lombok.*;

/**
 * Created by eeugene on 03/03/2017.
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "email")
public class Hero {
    private String email;
    private String avatar;
    private String name;

    private int hp;
    private int attackForce;
    private String bonus;
}
