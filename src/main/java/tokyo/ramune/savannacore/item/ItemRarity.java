package tokyo.ramune.savannacore.item;

import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON("Common", ChatColor.WHITE),
    UNCOMMON("Unommon", ChatColor.GREEN),
    RARE("Rare", ChatColor.BLUE),
    EPIC("Epic", ChatColor.LIGHT_PURPLE),
    LEGENDARY("Legendary", ChatColor.GOLD),
    STREAMER("Streamer", ChatColor.DARK_PURPLE),
    RAMUNE("Ramune", ChatColor.AQUA);

    private final String name;
    private final ChatColor color;
    ItemRarity(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
