package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.gun.event.BulletHitEntityEvent;

public final class BulletHitSoundListener implements Listener {
    @EventHandler
    public void onBulletHitEntity(BulletHitEntityEvent event) {
        final Player player = event.getBullet().getShooter();
        if (event.isHeadshot()) {
            SoundAsset.HEADSHOT.play(player);
        } else {
            SoundAsset.HIT.play(player);
        }
    }
}
