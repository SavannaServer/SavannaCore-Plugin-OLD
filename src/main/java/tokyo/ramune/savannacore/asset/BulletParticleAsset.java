package tokyo.ramune.savannacore.asset;

import org.bukkit.Particle;

public enum BulletParticleAsset {
    SPELL_MOB_AMBIENT(Particle.SPELL_MOB_AMBIENT, 0),
    VILLAGER_ANGRY(Particle.VILLAGER_ANGRY, 0),
    ASH(Particle.ASH, 0),
    BUBBLE_POP(Particle.BUBBLE_POP, 0),
    CLOUD(Particle.CLOUD, 0),
    COMPOSTER(Particle.COMPOSTER, 0),
    CRIT(Particle.CRIT, 0),
    DAMAGE_INDICATOR(Particle.DAMAGE_INDICATOR, 0),
    ENCHANTMENT_TABLE(Particle.ENCHANTMENT_TABLE, 0),
    CRIT_MAGIC(Particle.CRIT_MAGIC, 0),
    END_ROD(Particle.END_ROD, 0),
    FALLING_NECTAR(Particle.FALLING_NECTAR, 0),
    FALLING_OBSIDIAN_TEAR(Particle.FALLING_OBSIDIAN_TEAR, 0),
    FALLING_HONEY(Particle.FALLING_HONEY, 0),
    FALLING_LAVA(Particle.FALLING_LAVA, 0),
    FALLING_DRIPSTONE_LAVA(Particle.FALLING_DRIPSTONE_LAVA, 0),
    FALLING_DRIPSTONE_WATER(Particle.FALLING_DRIPSTONE_WATER, 0),
    FALLING_SPORE_BLOSSOM(Particle.FALLING_SPORE_BLOSSOM, 0),
    FALLING_WATER(Particle.FALLING_WATER, 0),
    SPELL_WITCH(Particle.SPELL_WITCH, 0),
    SQUID_INK(Particle.SQUID_INK, 0),
    GLOW_SQUID_INK(Particle.GLOW_SQUID_INK, 0),
    WATER_SPLASH(Particle.WATER_SPLASH, 0),
    SPIT(Particle.SPIT, 0),
    SPELL_INSTANT(Particle.SPELL_INSTANT, 0),
    SOUL_FIRE_FLAME(Particle.SOUL_FIRE_FLAME, 0),
    SNEEZE(Particle.SNEEZE, 0),
    SMOKE_NORMAL(Particle.SMOKE_NORMAL, 0),
    WATER_WAKE(Particle.WATER_WAKE, 1),
    NAUTILUS(Particle.NAUTILUS, 0),
    LAVA(Particle.LAVA, 0),
    SNOWBALL(Particle.SNOWBALL, 0),
    SNOWFLAKE(Particle.SNOWFLAKE, 0),
    SLIME(Particle.SLIME, 0),
    HEART(Particle.HEART, 0),
    FLAME(Particle.FLAME, 0),
    SOUL(Particle.SOUL, 0),
    WAX_ON(Particle.WAX_ON, 0),
    WAX_OFF(Particle.WAX_OFF, 0),
    SCULK_CHARGE_POP(Particle.SCULK_CHARGE_POP, 0);

    private final Particle particle;
    private final int extra;

    BulletParticleAsset(Particle particle, int extra) {
         this.particle = particle;
         this.extra = extra;
     }

    public Particle getParticle() {
        return particle;
    }

    public int getExtra() {
        return extra;
    }
}
