package tokyo.ramune.savannacore.item.skin;

import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.item.SavannaItem;

import javax.annotation.Nonnull;

public interface Boots extends SavannaItem {
    @Nonnull SoundAsset getStepSound();
}
