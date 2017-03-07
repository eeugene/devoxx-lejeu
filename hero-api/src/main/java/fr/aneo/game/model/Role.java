package fr.aneo.game.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Enumeration;

import static javax.persistence.GenerationType.AUTO;

/**
 * Created by raouf on 05/03/17.
 */
public enum Role {

    ADMIN,
    PLAYER
}
