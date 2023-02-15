package tokyo.ramune.savannacore.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tokyo.ramune.savannacore.asset.SkinAsset;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public final class SavannaItem {
    private final Material type;
    private final String name;
    private final SkinAsset skin;

    public SavannaItem(@Nonnull Material type,
                       @Nonnull String name,
                       @Nonnull SkinAsset skin) {
        this.type = type;
        this.name = name;
        this.skin = skin;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Component> getLore() {
        return Arrays.asList(
                Util.coloredText(skin.getRarity().getTextColor(), "◆-----------------◆"),
                Util.coloredText(skin.getRarity().getTextColor(), "Rarity -> " + skin.getRarity().getName()),
                Util.coloredText(skin.getRarity().getTextColor(), "Skin   -> " + skin.getName()),
                Util.coloredText(skin.getRarity().getTextColor(), "◆-----------------◆")
        );
    }

    public SkinAsset getSkin() {
        return skin;
    }

    public void set(@Nonnull Inventory inventory, int slot) {
        inventory.setItem(slot, toItemStack());
    }

    private ItemStack toItemStack() {
        final ItemStack item = new ItemStack(type);
        final ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name));
        meta.lore(getLore());
        meta.setUnbreakable(true);
        meta.setCustomModelData(skin.getCustomModelData());
        item.setItemMeta(meta);
        return item;
    }
}
