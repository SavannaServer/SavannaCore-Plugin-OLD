package tokyo.ramune.savannacore.language;

import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

public final class LanguageHandler {
    private final Set<LanguageFile> languageFiles = new HashSet<>();

    public LanguageHandler() {
        loadLanguages();
    }

    private void loadLanguages() {
        for (String name : getLanguageFilesNames()) {
            final LanguageFile file = new LanguageFile(name);
            file.saveDefaultConfig();
            languageFiles.add(file);
        }
    }

    public String getLanguageText(@Nonnull String language, @Nonnull String path) {
        for (LanguageFile languageFile : languageFiles) {
            if (!languageFile.getLanguage().equals(language)) continue;
            return languageFile.getConfig().getString("language." + path, "NoSuchFieldException.");
        }
        return "NoSuchFileException.";
    }

    public File getFileDirectry() {
        final File languageDirectry = new File(SavannaCore.getInstance().getDataFolder().toPath() + "/language");
        if (!languageDirectry.exists()) {
            try {
                Files.createDirectory(languageDirectry.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return languageDirectry;
    }

    public Set<String> getLanguageFilesNames() {
        final Set<String> files = new HashSet<>();
        final File[] existsFiles = getFileDirectry().listFiles();
        if (existsFiles == null) return new HashSet<>();

        for (File file : existsFiles) {
            if (!file.getName().endsWith(".yml")) continue;
            files.add(file.getName().replace(".yml", ""));
        }
        return files;
    }
}