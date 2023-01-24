package tokyo.ramune.savannacore.world;

import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.config.ConfigFile;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class WorldConfig extends ConfigFile {
    private final String worldName;

    public WorldConfig(@Nonnull String worldName) {
        super(SavannaCore.getInstance(), new File("./" + worldName), "world-config.yml");
        this.worldName = worldName;
    }

    @Override
    public void saveDefaultConfig() {
        if (!getConfigFile().exists()) {
            try {
                Files.copy(Objects.requireNonNull(getPlugin().getResource(getFile())), new File("./" + worldName + "/world-config.yml").toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void saveConfig() {
        super.saveConfig();
    }
}
