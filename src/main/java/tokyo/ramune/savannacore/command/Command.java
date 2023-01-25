package tokyo.ramune.savannacore.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import tokyo.ramune.savannacore.utility.ChatUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Command {
    private final String name;
    @Nullable
    private final Function<CommandArgs, Boolean> onCommand;
    @Nullable
    private final Function<CommandArgs, List<String>> onTabComplete;
    @Nullable
    private final Permission requirePermission;

    public Command(@Nonnull String name, @Nullable Function<CommandArgs, Boolean> onCommand, @Nullable Function<CommandArgs, List<String>> onTabComplete, @Nullable Permission requirePermission) {
        this.name = name;
        this.onCommand = onCommand;
        this.onTabComplete = onTabComplete;
        this.requirePermission = requirePermission;
    }

    public Command(@Nonnull String name, @Nullable Function<CommandArgs, Boolean> onCommand, @Nullable Permission requirePermission) {
        this.name = name;
        this.onCommand = onCommand;
        this.onTabComplete = null;
        this.requirePermission = requirePermission;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Function<CommandArgs, Boolean> getOnCommand() {
        return onCommand;
    }

    @Nullable
    public Function<CommandArgs, List<String>> getOnTabComplete() {
        return onTabComplete;
    }

    public void register() {
        if (isRegistered(this)) return;
        Bukkit.getServer().getCommandMap().register(getName(), new org.bukkit.command.Command(getName()) {
            @Override
            public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
                if (requirePermission != null && sender.hasPermission(requirePermission)) {
                    ChatUtil.requirePremission(sender, requirePermission);
                    return false;
                }
                return getOnCommand() != null && getOnCommand().apply(new CommandArgs(sender, args));
            }

            @Override
            public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) throws IllegalArgumentException {
                if (requirePermission != null && sender.hasPermission(requirePermission)) return new ArrayList<>();
                return getOnTabComplete() == null ? new ArrayList<>() : getOnTabComplete().apply(new CommandArgs(sender, args));
            }
        });
    }

    public void unregisterCommand() {
            org.bukkit.command.Command registeredCommand = Bukkit.getServer().getCommandMap().getCommand(getName());
            if (registeredCommand == null) return;
            registeredCommand.unregister(Bukkit.getServer().getCommandMap());
    }

    public boolean isRegistered(@Nonnull Command command) {
        return Bukkit.getCommandMap().getCommand(command.getName()) != null;
    }
}
