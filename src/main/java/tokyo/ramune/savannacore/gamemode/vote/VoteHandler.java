package tokyo.ramune.savannacore.gamemode.vote;

import tokyo.ramune.savannacore.gamemode.GameMode;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.SavannaWorld;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class VoteHandler {
    private final List<VoteEntry> voteEntries = new ArrayList<>();

    public VoteHandler() {
    }

    public void detectRandom(@Nonnull Collection<GameMode> gameModes, @Nonnull Collection<SavannaWorld> worlds) {
        for (int i = 0; i < 4; i++) {
            voteEntries.add(
                    new VoteEntry(
                            Util.getRandom(gameModes),
                            Util.getRandom(worlds)
                    )
            );
        }
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