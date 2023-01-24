package tokyo.ramune.savannacore.world;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public final class WorldObjectEntityCreator<T extends Entity> {
    private Vector location;
    private Class<T> clazz;

    public WorldObjectEntityCreator(@Nonnull Vector location, @Nonnull Class<T> clazz) {
        this.location = location;
        this.clazz = clazz;
    }

    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
