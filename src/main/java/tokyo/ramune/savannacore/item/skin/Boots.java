package tokyo.ramune.savannacore.item.skin;

import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.Item;

import javax.annotation.Nonnull;

public interface Boots extends Item {
    @Nonnull
    SoundAsset getStepSound();
}
