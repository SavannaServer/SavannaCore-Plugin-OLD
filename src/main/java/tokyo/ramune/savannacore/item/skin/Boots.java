package tokyo.ramune.savannacore.item.skin;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.ItemRarity;
import tokyo.ramune.savannacore.item.SavannaItem;

import javax.annotation.Nonnull;
import java.util.List;

public class Boots extends SavannaItem {
    private final SoundAsset stepSound;

    public Boots(@NotNull Material type,
                 @NotNull String name,
                 @NotNull ItemRarity rarity,
                 int customModelData,
                 @Nonnull SoundAsset stepSound) {
        super(type, name, rarity, customModelData);
        this.stepSound = stepSound;
    }

    @Nonnull
    public SoundAsset getStepSound(){
        return stepSound;
    }
}
