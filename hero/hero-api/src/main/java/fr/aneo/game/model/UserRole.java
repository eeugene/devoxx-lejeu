package fr.aneo.game.model;

import fr.aneo.game.security.user.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
    @Embeddable
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1322120000551624359L;
        
        @Column(name = "USER_ID")
        protected String userId;
        
        @Enumerated(EnumType.STRING)
        @Column(name = "ROLE")
        protected Role role;
        
        public Id() { }

        public Id(String userId, Role role) {
            this.userId = userId;
            this.role = role;
        }
    }
    
    @EmbeddedId
    Id id = new Id();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", insertable=false, updatable=false)
    protected Role role;

    public Role getRole() {
        return role;
    }
}