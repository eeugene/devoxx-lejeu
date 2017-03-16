package fr.aneo.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@NoArgsConstructor
@Entity
@Cacheable
public class Avatar implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Lob
    @Basic(fetch = LAZY)
    private byte[] depiction;
}
