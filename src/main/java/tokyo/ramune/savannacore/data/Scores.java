package tokyo.ramune.savannacore.data;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;

public final class Scores {
    private final HashMap<Player, Integer> scores = new HashMap<>();
    private boolean allowReward = true;
    private int rewardPerScore = 100;
    private int maximumReward = 30;

    public Scores() {
    }

    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    public int getScore(@Nonnull Player player) {
        return scores.get(player);
    }

    public Scores setScore(@Nonnull Player player, int score) {
        scores.put(player, score);
        return this;
    }

    public int getReward(@Nonnull Player player) {
        if (!allowReward) return 0;
        return Math.min(getScore(player) / rewardPerScore, maximumReward);
    }

    public boolean isAllowReward() {
        return allowReward;
    }

    public Scores setAllowReward(boolean allowReward) {
        this.allowReward = allowReward;
        return this;
    }

    public int getRewardPerScore() {
        return rewardPerScore;
    }

    public Scores setRewardPerScore(int rewardPerScore) {
        this.rewardPerScore = rewardPerScore;
        return this;
    }

    public int getMaximumReward() {
        return maximumReward;
    }

    public Scores setMaximumReward(int maximumReward) {
        this.maximumReward = maximumReward;
        return this;
    }
}
