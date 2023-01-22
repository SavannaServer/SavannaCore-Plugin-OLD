package tokyo.ramune.savannacore.asset;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public enum SoundAsset {
    SUCCESS(Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1),
    FAILED(Sound.ENTITY_WOLF_DEATH,1, 0),
    JUMP_PAD(Sound.ENTITY_BAT_TAKEOFF, 1, 0),
    HIT(Sound.BLOCK_STONE_STEP, 2, 1),
    GAME_OVER(Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);

    final Sound sound;
    final float volume, pitch;

    SoundAsset(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void play(@Nonnull Location location) {
        location.getWorld().playSound(location, getSound(), getVolume(), getPitch());
    }

    public void play(@Nonnull Player player) {
        player.playSound(player.getLocation(), getSound(), getVolume(), getPitch());
    }

    public void play(@Nonnull Player player, @Nonnull Location location) {
        player.playSound(location, getSound(), getVolume(), getPitch());
    }
}