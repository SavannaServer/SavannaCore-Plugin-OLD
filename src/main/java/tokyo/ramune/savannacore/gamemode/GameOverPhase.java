package tokyo.ramune.savannacore.gamemode;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.gamemode.vote.VoteHandler;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;

import java.util.HashSet;
import java.util.Set;

public final class GameOverPhase extends GameMode {
    private final VoteHandler voteHandler;
    private final Set<Player> nonGravityPlayers = new HashSet<>();

    public GameOverPhase() {
        super("Game Over", 15);
        voteHandler = new VoteHandler();

    }

    @Override
    public void onLoad() {
        super.onLoad();
        EventUtil.register(
                SavannaCore.getInstance(),
                new SideBarListener()
        );

        final SideBarHandler sideBarHandler = SavannaCore.getInstance().getSideBarHandler();

        for (Player player : Bukkit.getOnlinePlayers()) {
            final SideBar sideBar = new SideBar(player);
            sideBarHandler.setSideBar(sideBar);
            sideBar.show();
        }

        Util.teleport(SavannaCore.getInstance().getWorldHandler().get("sa.vote").getWorld().getSpawnLocation());
        Bukkit.getOnlinePlayers().forEach(SoundAsset.GAME_OVER::play);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        EventUtil.unregister(
                new SideBarListener()
        );

        for (Player player : nonGravityPlayers) {
            player.setGravity(true);
        }
        final String nextWorldName = Util.getRandom(SavannaCore.getInstance().getWorldHandler().getWorldNames());

        SavannaCore.getInstance()
                .getGameServer()
                .getGameModeHandler()
                .loadGameMode(Util.getRandom(SavannaCore.getInstance().getGameServer().getGameModeHandler().getNormalGameModes()), nextWorldName);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(Component.text(getCurrentTime()));
        }
    }

    private final class SideBarListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();
            final SideBarHandler sideBarHandler = SavannaCore.getInstance().getSideBarHandler();

            final SideBar sideBar = new SideBar(player);
            sideBarHandler.setSideBar(sideBar);
            sideBar.show();
        }
    }

    private final class SideBar extends tokyo.ramune.savannacore.sidebar.SideBar {
        public SideBar(Player player) {
            super(player, "Savanna");

            addBlankLine();
            addLine(() -> "");
            addBlankLine();
            addLine(() -> "Time left:");
            addLine(() -> Util.formatElapsedTime(getCurrentTime()));
        }
    }
}
