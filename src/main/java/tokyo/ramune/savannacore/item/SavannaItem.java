package tokyo.ramune.savannacore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class SavannaItem {
    private final Material type;
    private final ItemRarity rarity;
    private final int customModelData;
    private final String name;

    public SavannaItem(@Nonnull Material type,
                       @Nonnull String name,
                       @Nonnull ItemRarity rarity,
                       int customModelData) {
        this.type = type;
        this.name = name;
        this.rarity = rarity;
        this.customModelData = customModelData;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return
                Arrays.asList(
                        rarity.getColor() + "◆--------------◆",
                        rarity.getColor() + "Rarity -> " + rarity.getName(),
                        rarity.getColor() + "Skin   -> "
                );
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
