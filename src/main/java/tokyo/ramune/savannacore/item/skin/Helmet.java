package tokyo.ramune.savannacore.item.skin;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.item.ItemRarity;
import tokyo.ramune.savannacore.item.SavannaItem;

import java.util.List;

public class Helmet extends SavannaItem {
    public Helmet(@NotNull Material type, @NotNull String name, @NotNull ItemRarity rarity, int customModelData) {
        super(type, name, rarity, customModelData);
    }
}
