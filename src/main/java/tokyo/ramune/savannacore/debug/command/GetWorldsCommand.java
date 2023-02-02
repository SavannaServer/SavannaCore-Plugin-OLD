package tokyo.ramune.savannacore.debug.command;

import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.permission.SavannaPermission;


public class GetWorldsCommand extends Command {
    public GetWorldsCommand() {
        super(
                "get-worlds",
                args -> {
                    for (String name : SavannaCore.getInstance().getWorldHandler().getWorldNames()) {
                        args.getSender().sendMessage(name);
                    }
                    return true;
                },
                SavannaPermission.DEBUG_COMMAND.toPermission()
        );
    }
}
