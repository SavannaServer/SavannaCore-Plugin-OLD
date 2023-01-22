package tokyo.ramune.savannacore.item;

import org.bukkit.Material;

import javax.annotation.Nonnull;

public interface Item {
    @Nonnull
    String getName();

    @Nonnull
    String getDescription();

    @Nonnull
    Material getMaterial();

    @Nonnull
    ItemRarity getRarity();

    int getCustomModelData();
}
