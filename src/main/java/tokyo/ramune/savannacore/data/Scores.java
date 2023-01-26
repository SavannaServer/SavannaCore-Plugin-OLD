package tokyo.ramune.savannacore.data;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public final class Scores {
    private final Map<Player, Score> scores = new HashMap<>();
    private boolean allowReward = true;
    private int rewardPerScore = 100;
    private int maximumReward = 30;

    public Scores() {
    }

    public Map<Player, Score> getScores() {
        return scores;
    }

    public Score getScore(@Nonnull Player player) {
        return scores.get(player);
    }

    public void setScore(@Nonnull Score score) {

    }

    public int getReward(@Nonnull Player player) {
        if (!allowReward) return 0;
        return 0;
        //return Math.min(getScore(player).score() / rewardPerScore, maximumReward);
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
