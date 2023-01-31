package tokyo.ramune.savannacore.item;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class Glow extends Enchantment {

    public Glow() {
        super(new NamespacedKey(SavannaCore.getInstance(), "glow"));
    }

    @Override
    public boolean canEnchantItem(@Nonnull ItemStack arg0) {
        return false;
    }

    @Override
    public @Nonnull Component displayName(int i) {
        return Component.text("");
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @Nonnull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }

    @Override
    public float getDamageIncrease(int i, @Nonnull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @Nonnull Set<EquipmentSlot> getActiveSlots() {
        return new HashSet<>();
    }

    @Override
    public boolean conflictsWith(@Nonnull Enchantment arg0) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public @Nonnull String getName() {
        return "";
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public @Nonnull String translationKey() {
        return "";
    }
}
