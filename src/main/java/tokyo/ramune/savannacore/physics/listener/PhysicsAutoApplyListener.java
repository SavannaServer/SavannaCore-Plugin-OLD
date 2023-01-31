package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;

public final class PhysicsAutoApplyListener extends PhysicsListener {

    public PhysicsAutoApplyListener(@Nonnull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        Bukkit.getServer().unloadWorld(player.getWorld(), false);

        getPhysicsHandler().apply(player, true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        getPhysicsHandler().apply(player, false);
    }
}
