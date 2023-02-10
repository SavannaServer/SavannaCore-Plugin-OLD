package tokyo.ramune.savannacore.inventory;

import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.inventory.listener.DisableDropListener;
import tokyo.ramune.savannacore.inventory.listener.ForceItemSlotListener;
import tokyo.ramune.savannacore.utility.EventUtil;

public final class InventoryHandler {
    public InventoryHandler() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new DisableDropListener(),
                new ForceItemSlotListener()
        );
    }
}
