package tokyo.ramune.savannacore.gamemode;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.EventUtil;
import tokyo.ramune.savannacore.util.SoundUtil;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class GameOverPhase extends GameMode {
    private final Set<Player> nonGravityPlayers = new HashSet<>();

    public GameOverPhase() {
        super("Game Over", 15);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        EventUtil.register(SavannaCore.getPlugin(SavannaCore.class), new NoMoveListener());

        Bukkit.getOnlinePlayers().forEach(SoundUtil::gameOver);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        EventUtil.unregister(new NoMoveListener());

        for (Player player : nonGravityPlayers) {
            player.setGravity(true);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(Component.text(getCurrentTime()));
        }
    }

    @Override
    public void onJoin(@Nonnull Player player) {
        super.onJoin(player);
    }

    @Override
    public void onQuit(@Nonnull Player player) {
        super.onQuit(player);
    }

    private final class NoMoveListener implements Listener {
        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();
            if (!player.hasGravity()) {
                player.setGravity(false);
                nonGravityPlayers.add(player);
            }
            event.setTo(event.getFrom().setDirection(event.getTo().getDirection()));
        }
    }
}
