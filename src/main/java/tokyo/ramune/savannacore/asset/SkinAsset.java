package tokyo.ramune.savannacore.asset;

import tokyo.ramune.savannacore.item.ItemRarity;

import javax.annotation.Nonnull;

public interface SkinAsset {
    @Nonnull
    String getName();

    @Nonnull
    ItemRarity getRarity();

    int getCustomModelData();
}
