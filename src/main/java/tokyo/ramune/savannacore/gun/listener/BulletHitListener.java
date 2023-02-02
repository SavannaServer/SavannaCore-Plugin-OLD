package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.gun.Bullet;

public final class BulletHitListener implements Listener {
    @EventHandler
    public void onProjectiveHit(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        final Bullet bullet = Bullet.getBullet(event.getEntity());
        if (bullet == null) return;

        if (event.getHitBlock() != null) {
            projectile.remove();
            event.setCancelled(true);
            final Location location = projectile.getLocation();
            final BlockData blockData = event.getHitBlock().getBlockData();
            if (bullet.getDistance() > 0) {
                location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 5, 0, 0, 0, blockData);
            } else {
                final Location hitBlockLocation = event.getHitBlock().getLocation();
                location.getWorld().spawnParticle(Particle.BLOCK_CRACK, hitBlockLocation, 5, 0.1F, 0.1F, 0.1F, 0.1F, blockData);
            }
            return;
        }
        if (event.getHitEntity() != null) {
            event.setCancelled(true);
            final LivingEntity hitEntity = (LivingEntity) event.getHitEntity();
            hitEntity.playEffect(EntityEffect.HURT);
            SoundAsset.HIT.play(bullet.getShooter());
            projectile.remove();
            double damage = hitEntity.getHealth() - bullet.getDamage();
            hitEntity.setHealth(damage < 0 ? 0 : damage);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        System.out.println(1);
    }

}