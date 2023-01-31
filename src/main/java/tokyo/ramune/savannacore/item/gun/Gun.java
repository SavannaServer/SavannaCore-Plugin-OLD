package tokyo.ramune.savannacore.item.gun;

import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.SavannaItem;

import javax.annotation.Nonnull;

public interface Gun extends SavannaItem {
    @Nonnull
    SoundAsset getShootSound();

    @Nonnull
    SoundAsset getReloadSound();
}
