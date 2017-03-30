package fr.aneo.game.model;

/**
 * Created by raouf on 04/03/17.
 */
public enum Bonus {
    BOOST_FIRST_START("Frapper le premier"),
    BOOST_ATTACK_20("Boost la puissance de ton attaque de 20%"),
    BOOST_HP_10("Boost ta capacité de vie de 10%"),
    HANDICAP_HP_50("Diminue la vie de ton adversaire de 50%"),
    HANDICAP_ATTACK_CANCEL("Annule la première attaque de ton adversaire");

    private String description;

    Bonus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
