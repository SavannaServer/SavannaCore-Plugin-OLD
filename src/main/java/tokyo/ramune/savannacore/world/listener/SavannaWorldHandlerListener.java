package tokyo.ramune.savannacore.world.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.world.WorldHandler;

import javax.annotation.Nonnull;

public class SavannaWorldHandlerListener implements Listener {
    private final WorldHandler worldHandler;

    public SavannaWorldHandlerListener(@Nonnull WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    public final WorldHandler getWorldHandler() {
        return worldHandler;
    }
}
