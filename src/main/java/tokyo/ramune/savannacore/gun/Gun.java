package tokyo.ramune.savannacore.gun;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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
            Player player = event.getPlayer();
            new Bullet(Snowball.class).shoot(player, player.getLocation().add(0, 1, 0), player.getLocation().getDirection().multiply(4));
        }
    }
}
