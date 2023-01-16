package tokyo.ramune.savannacore.physics;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;

public final class JumpPad {
    private final Block jumpPad;

    public JumpPad(@Nonnull Block jumpPad) {
        if (!jumpPad.getType().equals(Material.SCULK_SENSOR))
            throw new IllegalArgumentException("Block type must be SCULK_SENSOR");
        this.jumpPad = jumpPad;
    }

    public Block getJumpPad() {
        return jumpPad;
    }

    public double getVelocity() {
        return NBTEditor.getDouble(jumpPad, "SavannaCore:JumpPad.velocity");
    }

    public void setVelocity(double velocity) {
        NBTEditor.set(jumpPad, velocity, "SavannaCore:JumpPad.velocity");
    }
}
