package fr.aneo.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 04/03/17.
 */
@Data
@Builder
@Entity
@Cacheable
public class Avatar implements Serializable {

    @Tolerate
    public Avatar() {}

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Lob
    @JsonIgnore
    private byte[] depiction;

}
