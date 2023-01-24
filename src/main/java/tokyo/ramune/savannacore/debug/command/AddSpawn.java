package tokyo.ramune.savannacore.debug.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.utility.CommandUtil;

import javax.annotation.Nonnull;

public final class AddSpawn extends Command {
    public AddSpawn() {
        super("add-spawn");
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String s, @Nonnull String[] strings) {
        if (!(sender instanceof Player player)) {
            CommandUtil.mismatchSender(sender);
            return true;
        }


        return true;
    }
}
