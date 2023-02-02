package tokyo.ramune.savannacore.permission;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;

public enum SavannaPermission {
    DEBUG_COMMAND(PermissionDefault.OP);

    private final PermissionDefault def;

    SavannaPermission(PermissionDefault def) {
        this.def = def;
    }

    public static void registerAll() {
        for (SavannaPermission permission : values()) {
            try {
                Bukkit.getPluginManager().addPermission(permission.toPermission());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public static void unregisterAll() {
        for (SavannaPermission permission : values()) {
            Bukkit.getPluginManager().removePermission(permission.toPermission());
        }
    }

    public org.bukkit.permissions.Permission toPermission() {
        return new org.bukkit.permissions.Permission(name().toLowerCase(), def);
    }
}
