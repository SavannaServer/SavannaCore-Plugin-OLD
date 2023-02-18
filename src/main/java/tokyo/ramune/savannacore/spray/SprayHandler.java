package tokyo.ramune.savannacore.spray;

import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SprayHandler {
    private final Map<Player, Spray> spraies = new HashMap<>();

    public SprayHandler() {
        initialize();
        load();
    }

    private void initialize() {
        final File spraysDirectory = new File(SavannaCore.getInstance().getDataFolder() + "/sparys");
        if (!spraysDirectory.exists()) spraysDirectory.mkdir();
        if (!spraysDirectory.isDirectory()) {
            spraysDirectory.delete();
            spraysDirectory.mkdir();
        }
    }

    private void load() {
        final File spraysDirectory = new File(SavannaCore.getInstance().getDataFolder() + "/sparys");
        final File[] sprayList = spraysDirectory.listFiles();
        if (sprayList == null || sprayList.length == 0) return;

        final List<File> sprays = new ArrayList<>();
        for (File file : sprayList) {
            if (!file.getName().endsWith(".png")) continue;
            sprays.add(file);
        }
    }
}
