package tokyo.ramune.savannacore.language;

import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.config.ConfigFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class LanguageFile extends ConfigFile {
    private final String language;

    public LanguageFile(String language) {
        super(SavannaCore.getInstance(), new File(SavannaCore.getInstance().getDataFolder().toPath() + "/language"), language);
        this.language = language;
    }

    @Override
    public void saveDefaultConfig() {
        if (!getConfigFile().exists()) {
            try {
                final InputStream in = Objects.requireNonNull(getPlugin().getResource("language.yml"));
                final Path target = Path.of(SavannaCore.getInstance().getDataFolder().toPath() + "/language/" + language + ".yml");
                Files.copy(in, target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getLanguage() {
        return language;
    }
}