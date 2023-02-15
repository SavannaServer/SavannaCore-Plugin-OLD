package tokyo.ramune.savannacore.inventory.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.inventory.InventoryHandler;

public class InventoryHandlerListener implements Listener {
    private final InventoryHandler inventoryHandler = SavannaCore.getInstance().getInventoryHandler();

    public InventoryHandler getInventoryHandler() {
        return inventoryHandler;
    }
}
