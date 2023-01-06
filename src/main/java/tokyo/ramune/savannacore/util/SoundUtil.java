package tokyo.ramune.savannacore.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SoundUtil {
    public static void success(@Nonnull Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
    }

    public static void failed(@Nonnull Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_DEATH, 1, 0);
    }
}
