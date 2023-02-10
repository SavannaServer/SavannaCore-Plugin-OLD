package tokyo.ramune.savannacore.item.gun;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.ItemRarity;
import tokyo.ramune.savannacore.item.SavannaItem;

import javax.annotation.Nonnull;
import java.util.List;

public class Gun extends SavannaItem {
    private final SoundAsset shootSound, reloadSound;

    public Gun(@NotNull Material type,
               @NotNull String name,
               @NotNull ItemRarity rarity,
               int customModelData,
               @Nonnull SoundAsset shootSound,
               @Nonnull SoundAsset reloadSound) {
        super(type, name, rarity, customModelData);
        this.shootSound = shootSound;
        this.reloadSound = reloadSound;
    }

    @Nonnull
    public SoundAsset getShootSound() {
        return shootSound;
    }

    @Nonnull
    public SoundAsset getReloadSound() {
        return reloadSound;
    }
}
