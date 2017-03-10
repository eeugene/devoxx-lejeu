package fr.aneo.game.model;

/**
 * Created by raouf on 05/03/17.
 */
public enum Role {
    ADMIN, PLAYER;
    public String authority() {
        return "ROLE_" + this.name();
    }
}
