package tokyo.ramune.savannacore.item;

import net.kyori.adventure.text.format.TextColor;

public enum ItemRarity {
    COMMON("Common", TextColor.color(0xFFFFFF)),
    UNCOMMON("Uncommon", TextColor.color(0x11FF00)),
    RARE("Rare", TextColor.color(0x5F4)),
    EPIC("Epic", TextColor.color(0xBF00F4)),
    LEGENDARY("Legendary", TextColor.color(0xF4A100)),
    STREAMER("Streamer", TextColor.color(0xFFAAF1)),
    RAMUNE("Ramune", TextColor.color(0xE6F4));

    private final String name;
    private final TextColor color;

    ItemRarity(String name, TextColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public TextColor getTextColor() {
        return color;
    }
}
