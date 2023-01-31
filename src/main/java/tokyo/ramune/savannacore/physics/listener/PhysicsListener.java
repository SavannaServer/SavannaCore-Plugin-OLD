package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;

public class PhysicsListener implements Listener {
    private final PhysicsHandler physicsHandler;

    public PhysicsListener(@Nonnull PhysicsHandler physicsHandler) {
        this.physicsHandler = physicsHandler;
    }

    protected PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
    }
}
