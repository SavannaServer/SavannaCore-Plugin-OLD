package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gun.event.ToggleShootEvent;
import tokyo.ramune.savannacore.utility.EventUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ToggleShootEventListener implements Listener {
    private final Map<Player, BukkitRunnable> shootingPlayers = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        if (!Objects.equals(event.getHand(), EquipmentSlot.HAND)) return;
        final Player player = event.getPlayer();
        if (shootingPlayers.containsKey(player)) {
            shootingPlayers.get(player).cancel();
        } else {
            EventUtil.callEvent(new ToggleShootEvent(player, true));
        }
        final BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                EventUtil.callEvent(new ToggleShootEvent(player, false));
                shootingPlayers.remove(player);
            }
        };
        task.runTaskLater(SavannaCore.getInstance(), 5);
        shootingPlayers.put(player, task);
    }
}
