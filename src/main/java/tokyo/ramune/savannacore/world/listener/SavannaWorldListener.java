package tokyo.ramune.savannacore.world.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.world.SavannaWorld;
import tokyo.ramune.savannacore.world.WorldHandler;

import javax.annotation.Nonnull;

public class SavannaWorldListener implements Listener {
    private final WorldHandler worldHandler;

    public SavannaWorldListener(@Nonnull WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    public final WorldHandler getWorldHandler() {
        return worldHandler;
    }
}
