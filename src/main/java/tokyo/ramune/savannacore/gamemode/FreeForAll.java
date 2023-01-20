package tokyo.ramune.savannacore.gamemode;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class FreeForAll extends GameMode {
    public FreeForAll() {
        super("FFA", 10);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {
        super.onUnload();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(Component.text(getCurrentTime()));
        }
    }
}
