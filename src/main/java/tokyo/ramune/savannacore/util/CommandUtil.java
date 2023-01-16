package tokyo.ramune.savannacore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public final class CommandUtil {
    public static void register(@Nonnull String fallbackPrefix, @Nonnull Command... commands) {
        for (Command command : commands) {
            Bukkit.getCommandMap().register(fallbackPrefix, command);
        }
    }

    public static void mismatchSender(@Nonnull CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "You can't execute this command from here.");
    }

    public static void success(@Nonnull CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Success!");
    }
}
