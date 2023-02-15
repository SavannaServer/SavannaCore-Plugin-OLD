package tokyo.ramune.savannacore.ready.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import tokyo.ramune.savannacore.menu.Menu;

public class GameReadyMenu extends Menu {
    public GameReadyMenu() {
        super(
                Component.text("待機中...").color(TextColor.color(97, 37, 209)),
                45
        );
    }
}
