package fr.aneo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by eeugene on 19/03/2017.
 */
@Data
@EqualsAndHashCode(of = "hero")
public class HeroStats {
    Hero hero;
    int rank;
    int totalFightCount;
    int totalVictoryCount;
    int totalLossCount;
    int bestRank = Integer.MAX_VALUE;
    SligingWindow<BattleDetail> lastFiveBattles = new SligingWindow<>(5);

    public HeroStats(Hero hero) {
        this.hero = hero;
    }

    public Double getWinRatio() {
        double ratio = (double) totalVictoryCount / totalFightCount;
        double value = (ratio * 100);
        double rounded = (double) Math.round(value * 100) / 100;
        return rounded;
    }

    Map<Hero, Integer> opponentCount = new HashMap<>();

    public void incTotalFightCount(int i) {
        totalFightCount += i;
    }

    public void incTotalVictoryCount(int i) {
        totalVictoryCount += i;
    }

    public void incTotalLossCount(int i) {
        totalLossCount += i;
    }

    public void setRank(int rank) {
        this.rank = rank;
        this.bestRank = Math.min(rank, bestRank);
    }

    @Data
    @Builder
    public static class BattleDetail {
        Hero opponent;
        boolean battleWined;
    }

    public static class SligingWindow<T> {
        Deque<T> window;

        public SligingWindow(int size) {
            this.window = new LinkedBlockingDeque<>(size);
        }

        public void add(T value) {
            boolean success = window.offerFirst(value);
            if (!success) {
                window.pollLast();
                window.offerFirst(value);
            }
        }

        public Deque<T> getWindow() {
            return window;
        }
    }
 }
