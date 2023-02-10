package tokyo.ramune.savannacore.gun;

import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gun.listener.DeathAnimationListener;
import tokyo.ramune.savannacore.gun.listener.ToggleShootEventListener;
import tokyo.ramune.savannacore.utility.EventUtil;

public final class GunHandler {
    public GunHandler() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new ToggleShootEventListener(),
                new DeathAnimationListener()
        );
    }
}
