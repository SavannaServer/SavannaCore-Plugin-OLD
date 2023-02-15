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
    }

    public SavannaItem getFirstWeapon() {
        return firstWeapon;
    }

    public void setFirstWeapon(SavannaItem firstWeapon) {
        this.firstWeapon = firstWeapon;
    }

    public SavannaItem getSecondWeapon() {
        return secondWeapon;
    }

    public void setSecondWeapon(SavannaItem secondWeapon) {
        this.secondWeapon = secondWeapon;
    }

    public SavannaItem getThirdWeapon() {
        return thirdWeapon;
    }

    public void setThirdWeapon(SavannaItem thirdWeapon) {
        this.thirdWeapon = thirdWeapon;
        apply();
    }

    public SavannaItem getSpecialWeapon() {
        return specialWeapon;
    }

    public void setSpecialWeapon(SavannaItem specialWeapon) {
        this.specialWeapon = specialWeapon;
        apply();
    }

    public Player getPlayer() {
        return player;
    }

    public void apply() {
        for (int i = 9; i < 36; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        if (firstWeapon != null) firstWeapon.set(player.getInventory(), 4);
        if (secondWeapon != null) secondWeapon.set(player.getInventory(), 5);
        if (thirdWeapon != null) thirdWeapon.set(player.getInventory(), 3);
        if (specialWeapon != null) specialWeapon.set(player.getInventory(), 7);
    }
}
