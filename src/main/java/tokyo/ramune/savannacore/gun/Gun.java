package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gun.listener.*;
import tokyo.ramune.savannacore.utility.EventUtil;

public class Gun {
    public Gun() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new ThrowBulletListener(),
                new BulletHitListener(),
                new BulletMoveEventListener(),
                new BulletDistanceListener(),
                new BulletParticleListener(),
                new BulletFarRemoveListener()
        );
    }

    static class ThrowBulletListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            if (!event.getAction().isLeftClick()) return;

            final Player player = event.getPlayer();
            final Location location = player.getLocation().add(0, 1.5, 0);
            final Vector velocity = location.getDirection().multiply(10);

            for (int i = 0; i < 5; i++) {
                final Bullet bullet = new Bullet(Arrow.class);
                bullet.shoot(player, location, velocity);
            }
        }
    }
}
