package tokyo.ramune.savannacore.debug.command;

import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.permission.SavannaPermission;
import tokyo.ramune.savannacore.utility.CommandUtil;

public class TpWorldCommand extends Command {
    public TpWorldCommand() {
        super(
                "tp-world",
                args -> {
                    if (!(args.getSender() instanceof Player player)) {
                        CommandUtil.mismatchSender(args.getSender());
                        return false;
                    }
                    try {
                        player.teleport(SavannaCore.getInstance().getWorldHandler().load(args.getArgs()[0]).getWorld().getSpawnLocation());
                    } catch (Exception ignored) {
                    }
                    return true;
                },
                args -> SavannaCore.getInstance().getWorldHandler().getWorldNames(),
                SavannaPermission.DEBUG_COMMAND.toPermission()
        );
    }
}
