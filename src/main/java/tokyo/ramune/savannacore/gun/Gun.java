package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.BulletParticleAsset;
import tokyo.ramune.savannacore.gun.event.ToggleShootEvent;
import tokyo.ramune.savannacore.utility.EventUtil;

import java.util.HashMap;
import java.util.Map;

// TODO あとで消す
public class Gun {
    public Gun() {
        EventUtil.register(
                SavannaCore.getInstance(),

                new ThrowBulletListener()
        );
    }

    static class ThrowBulletListener implements Listener {
        private final Map<Player, BukkitRunnable> shootingPlayers = new HashMap<>();

        @EventHandler
        public void onToggleShoot(ToggleShootEvent event) {
            final Player player = event.getPlayer();


            if (event.isShooting()) {
                final BukkitRunnable task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        final Location location = player.getLocation().add(0, 1.5, 0);
                        final Vector velocity = location.getDirection().multiply(4);

                        final Bullet bullet = new Bullet(Arrow.class);
                        bullet.setShake(0.3);
                        bullet.shoot(player, BulletParticleAsset.ASH, location, velocity);
                    }
                };
                task.runTaskTimer(SavannaCore.getInstance(), 2, 1);
                shootingPlayers.put(player, task);
            } else {
                shootingPlayers.get(player).cancel();
                shootingPlayers.remove(player);
            }

        }
    }
}
