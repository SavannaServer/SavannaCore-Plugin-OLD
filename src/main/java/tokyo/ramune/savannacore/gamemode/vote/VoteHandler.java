package tokyo.ramune.savannacore.gamemode.vote;

import tokyo.ramune.savannacore.gamemode.GameMode;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.SavannaWorld;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class VoteHandler {
    private final Map<VoteEntry, Integer> votes = new HashMap<>();

    public VoteHandler() {
    }

    public void reset(@Nonnull Collection<GameMode> gameModes, @Nonnull Collection<SavannaWorld> worlds) {
        votes.clear();
        for (int i = 0; i < 4; i++) {
            votes.put(
                    new VoteEntry(
                            Util.getRandom(gameModes),
                            Util.getRandom(worlds)
                    ),
                    0
            );
        }
    }

    public Set<VoteEntry> getEntries() {
        return votes.keySet();
    }

    public void vote(@Nonnull VoteEntry entry, int vote) {
        if (votes.containsKey(entry)) return;
        votes.put(entry, votes.get(entry) + vote);
    }

    public VoteEntry getVoteResult() {
        return Util.getVoteResult(votes);
    }

    public final class VoteEntry {
        private final GameMode gameMode;
        private final SavannaWorld world;

        public VoteEntry(@Nonnull GameMode gameMode, @Nonnull SavannaWorld world) {
            this.gameMode = gameMode;
            this.world = world;
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        public SavannaWorld getWorld() {
            return world;
        }
    }
}