package tokyo.ramune.savannacore.debug.command;

import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.permission.SavannaPermission;
import tokyo.ramune.savannacore.utility.ChatUtil;
import tokyo.ramune.savannacore.utility.CommandUtil;

public class AddSpawnCommand extends Command {
    public AddSpawnCommand() {
        super(
                "add-spawn",
                args -> {
                    if (!(args.getSender() instanceof Player player)) {
                        CommandUtil.mismatchSender(args.getSender());
                        return false;
                    }
                    if (!player.getWorld().getName().startsWith("sa.")) {
                        ChatUtil.sendMessage(player, "§cYou need to run this command in savanna world.", true);
                        return false;
                    }
                    SavannaCore.getInstance().getWorldHandler().load(player.getWorld().getName()).addSpawnLocation(player.getLocation());
                    return true;
                },
                SavannaPermission.DEBUG_COMMAND.toPermission());
    }
}
