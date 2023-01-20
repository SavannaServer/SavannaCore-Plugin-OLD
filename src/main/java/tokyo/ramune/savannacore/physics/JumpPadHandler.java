package tokyo.ramune.savannacore.physics;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.SoundAssets;
import tokyo.ramune.savannacore.utility.EventUtil;

import javax.annotation.Nonnull;

public final class JumpPadHandler {
    private final PhysicsHandler physicsHandler;

    public JumpPadHandler(@Nonnull PhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new JumpPadListener()
        );
    }

    // Jump pad system
    // TODO: 2023/01/07 This used a stone pressure pad temporarily to see how it work.
    //  Make original jump pad.
    private final class JumpPadListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();

            if (!physicsHandler.isAttached(player)) return;

            if (!((LivingEntity) player).isOnGround()) return;
            if (!player.getLocation().getBlock().getType().equals(Material.SCULK_SENSOR)) return;
            final JumpPad jumpPad = new JumpPad(player.getLocation().getBlock());
            if (jumpPad.getVelocity() <= 0) return;

            player.setVelocity(player.getVelocity().multiply(1.3).setY(jumpPad.getVelocity()));
            SoundAssets.JUMP_PAD.play(player);
        }

        @EventHandler
        public void onGenericGame(BlockReceiveGameEvent event) {
            if (!event.getBlock().getType().equals(Material.SCULK_SENSOR)) return;
            event.setCancelled(true);
            final Entity entity = event.getEntity();
            if (!(entity instanceof final Player player)) return;
            player.stopSound(Sound.BLOCK_SCULK_SENSOR_CLICKING_STOP);
        }
    }
}
