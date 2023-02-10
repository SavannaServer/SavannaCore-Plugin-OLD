package tokyo.ramune.savannacore.inventory.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import tokyo.ramune.savannacore.inventory.SavannaInventory;
import tokyo.ramune.savannacore.utility.Util;

public class ForceItemSlotListener implements Listener {
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        final int newSlot = event.getNewSlot();

        if (Util.isBetween(newSlot, 3, 5)) return;
        event.setCancelled(true);
        new SavannaInventory(player);
        switch (newSlot) {
            case 0, 6 -> player.getInventory().setHeldItemSlot(3);
            case 1, 7 -> player.getInventory().setHeldItemSlot(4);
            case 2, 8 -> player.getInventory().setHeldItemSlot(5);
        }
    }
}
