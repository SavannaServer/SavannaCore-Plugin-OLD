package tokyo.ramune.savannacore.item.gun;

import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.Item;

import javax.annotation.Nonnull;

public interface Gun extends Item {
    @Nonnull
    SoundAsset getShootSound();

    @Nonnull
    SoundAsset getReloadSound();
}
