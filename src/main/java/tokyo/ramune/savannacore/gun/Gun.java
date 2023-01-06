package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.EventUtil;

public class Gun {
    public Gun() {
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new ThrowBulletListener(),
                new Bullet.BulletHitListener()
        );
    }

    static class ThrowBulletListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            if (!event.getAction().isLeftClick()) return;

            final Player player = event.getPlayer();
            final Location location = player.getLocation().add(0, 1.5, 0);
            final Vector velocity = location.getDirection().multiply(4);

            new Bullet(Snowball.class).shoot(player, location, velocity);
        }
    }
}
