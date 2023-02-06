package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.BulletParticleAsset;
import tokyo.ramune.savannacore.data.PlayerData;
import tokyo.ramune.savannacore.utility.EventUtil;

// TODO あとで消す
public class Gun {
    public Gun() {
        EventUtil.register(
                SavannaCore.getInstance(),

                new ThrowBulletListener()
        );
    }

    static class ThrowBulletListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            if (!event.getAction().isLeftClick()) return;

            final Player player = event.getPlayer();
            final Location location = player.getLocation().add(0, 1.5, 0);
            final Vector velocity = location.getDirection().multiply(4);

            final Bullet bullet = new Bullet(Arrow.class);
            bullet.setShake(0.5);
            bullet.shoot(player, BulletParticleAsset.ASH, location, velocity);
        }
    }
}
