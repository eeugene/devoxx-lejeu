package fr.aneo.game.security.jwt;

public interface TokenExtractor {
    public String extract(String payload);
}