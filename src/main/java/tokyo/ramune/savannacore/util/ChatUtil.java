package tokyo.ramune.savannacore.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {
    private static final RateLimiter<CommandSender> rateLimiter = new RateLimiter<>(1);
    private static final String prefix = ChatColor.GOLD + "Savanna";

    public static void sendMessage(Player player, String message, boolean addPrefix) {
        player.sendMessage(addPrefix ? prefix : "" + message);
    }

    public static void sendMessage(CommandSender sender, String message, boolean addPrefix) {
        sender.sendMessage(addPrefix ? prefix : "" + message);
    }

    public static void sendMessage(Player player, String message, boolean addPrefix, boolean rateLimited) {
        if (rateLimited && !rateLimiter.tryAcquire(player))
            return;

        sendMessage(player, message, addPrefix);
    }

    public static void sendMessage(CommandSender sender, String message, boolean addPrefix, boolean rateLimited) {
        if (rateLimited && !rateLimiter.tryAcquire(sender))
            return;

        sendMessage(sender, message, addPrefix);
    }
}
