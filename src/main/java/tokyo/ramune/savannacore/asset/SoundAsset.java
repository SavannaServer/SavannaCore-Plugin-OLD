package tokyo.ramune.savannacore.asset;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface SoundAsset {
    Sound getSound();
    float getVolume();
    float getPitch();

    default void play(@Nonnull Location location) {
        location.getWorld().playSound(location, getSound(), getVolume(), getPitch());
    }

    default void play(@Nonnull Player player) {
        player.playSound(player.getLocation(), getSound(), getVolume(), getPitch());
    }

    default void play(@Nonnull Player player, @Nonnull Location location) {
        player.playSound(location, getSound(), getVolume(), getPitch());
    }
}
