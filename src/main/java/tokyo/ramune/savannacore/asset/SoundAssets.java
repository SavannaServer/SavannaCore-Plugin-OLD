package tokyo.ramune.savannacore.asset;

import org.bukkit.Sound;

public enum SoundAssets implements SoundAsset {
    SUCCESS(Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1),
    FAILED(Sound.ENTITY_WOLF_DEATH,1, 0),
    JUMP_PAD(Sound.ENTITY_BAT_TAKEOFF, 1, 0),
    HIT(Sound.BLOCK_STONE_STEP, 2, 1),
    GAME_OVER(Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 0);

    final Sound sound;
    final float volume, pitch;

    SoundAssets(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public Sound getSound() {
        return sound;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getPitch() {
        return pitch;
    }
}