package tokyo.ramune.savannacore.data;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class Score {
    private final Player player;
    private int score;

    public Score(@Nonnull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }
}
