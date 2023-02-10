package tokyo.ramune.savannacore.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tokyo.ramune.savannacore.item.SavannaItem;

import javax.annotation.Nonnull;

public final class SavannaInventory {
    private final Player player;
    private SavannaItem firstWeapon, secondWeapon, thirdWeapon;
    private SavannaItem specialWeapon;

    public SavannaInventory(@Nonnull Player player) {
        this.player = player;
        apply();
    }

    public Player getPlayer() {
        return player;
    }

    private void apply() {
        for (int i = 9; i < 36; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
    }
}
