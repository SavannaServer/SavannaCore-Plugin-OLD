package tokyo.ramune.savannacore.asset;

import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public enum MessageAsset {
    PLAYER_JOIN_MESSAGE,
    PLAYER_QUIT_MESSAGE,
    WIN,
    LOSE;

    private final Map<Integer, String> replaces = new HashMap<>();

    MessageAsset() {
    }

    public MessageAsset replace(int slot, String text) {
        replaces.put(slot, text);
        return this;
    }

    public String getMessage(@Nonnull String language) {
        String message = SavannaCore.getInstance().getLanguageHandler().getLanguageText(language, name());
        for (Map.Entry<Integer, String> entry : replaces.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }
}