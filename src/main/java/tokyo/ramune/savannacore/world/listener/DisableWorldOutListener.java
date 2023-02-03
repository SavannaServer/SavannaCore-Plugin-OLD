package tokyo.ramune.savannacore.world.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.world.SavannaWorld;
import tokyo.ramune.savannacore.world.WorldHandler;

public class DisableWorldOutListener extends SavannaWorldListener {
    public DisableWorldOutListener(@NotNull WorldHandler worldHandler) {
        super(worldHandler);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location location = player.getLocation();
        SavannaWorld world = null;

        try { world = new SavannaWorld(player.getWorld()); } catch (Exception ignored) {};
        if (world == null) return;

        final int worldBoarder = world.getWorldBorder();

        if (Math.abs(location.getX()) >= worldBoarder) player.setVelocity(location.getX() > 0 ? new Vector(-0.5, 0.1, 0) : new Vector(0.5, 0.1, 0));
        if (Math.abs(location.getZ()) >= worldBoarder) player.setVelocity(location.getZ() > 0 ? new Vector(0, 0.1, -0.5) : new Vector(0, 0.1, 0.5));
    }
}
