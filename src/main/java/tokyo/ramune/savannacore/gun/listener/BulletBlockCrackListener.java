package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.event.BulletHitBlockEvent;

public class BulletBlockCrackListener implements Listener {
    @EventHandler
    public void onBulletHitBlock(BulletHitBlockEvent event) {
        if (event.getBullet().getProjectile() == null) return;

        final Bullet bullet = event.getBullet();
        final Location location = bullet.getProjectile().getLocation();
        final BlockData blockData = event.getHitBlock().getBlockData();

        if (bullet.getDistance() > 0) {
            location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 5, 0, 0, 0, blockData);
        } else {
            final Location hitBlockLocation = event.getHitBlock().getLocation();
            location.getWorld().spawnParticle(Particle.BLOCK_CRACK, hitBlockLocation, 5, 0.1F, 0.1F, 0.1F, 0.1F, blockData);
        }
    }
}
