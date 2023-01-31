package tokyo.ramune.savannacore.item;

import org.bukkit.Material;

import javax.annotation.Nonnull;

public interface SavannaItem {
    @Nonnull
    String getName();

    @Nonnull
    String getDescription();

    @Nonnull
    Material getType();

    @Nonnull
    ItemRarity getRarity();

    int getCustomModelData();
}
