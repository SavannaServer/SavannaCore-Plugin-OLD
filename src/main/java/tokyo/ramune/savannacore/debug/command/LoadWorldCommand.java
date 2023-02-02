package tokyo.ramune.savannacore.debug.command;

import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.command.CommandArgs;
import tokyo.ramune.savannacore.permission.SavannaPermission;
import tokyo.ramune.savannacore.utility.ChatUtil;

import java.util.List;
import java.util.function.Function;

public class LoadWorldCommand extends Command {
    public LoadWorldCommand() {
        super(
                "load-world",
                args -> {
                    if (args.getArgs().length > 1 && args.getArgs()[0].isBlank() && !args.getArgs()[0].startsWith("sa.")) {
                        ChatUtil.sendMessage(args.getSender(), "§cArgs are wrong. example-> /load-world sa.<world-name>", true);
                        return false;
                    }
                    SavannaCore.getInstance().getWorldHandler().load(args.getArgs()[0]);
                    return true;
                },
                args -> SavannaCore.getInstance().getWorldHandler().getWorldNames(),
                SavannaPermission.DEBUG_COMMAND.toPermission()
        );
    }
}
