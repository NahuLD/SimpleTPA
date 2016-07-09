package me.nahuld.simpletpa.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

public class Effect {

    /**
     * Can be used to colorize certain particles. As of 1.8, those
     * include SPELL_MOB_AMBIENT, SPELL_MOB and REDSTONE.
     */
    public Color color = null;

    /**
     * This can be used to give particles a set speed when spawned.
     * This will not work with colored particles.
     */
    public float speed = 0;

    /**
     * Delay to wait for delayed effects.
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int delay = 0;

    /**
     * Interval to wait for repeating effects.
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int period = 1;

    /**
     * Amount of repetitions to do.
     * Set this to -1 for an infinite effect
     *
     * @see {@link de.slikey.effectlib.EffectType}
     */
    public int iterations = 0;

    /**
     * Total duration of this effect in milliseconds.
     *
     * If set, this will adjust iterations to match
     * the defined delay such that the effect lasts
     * a specific duration.
     */
    public Integer duration = null;

    /**
     * Callback to run, after effect is done.
     *
     * @see {@link java.lang.Runnable}
     */
    public Runnable callback = null;

    /**
     * Display particles to players within this radius.
     */
    public float visibleRange = 32;

    /**
     * If true, and a "target" Location or Entity is set, the two Locations
     * will orient to face one another.
     */
    public boolean autoOrient = false;
    /**
     * These are used to modify the direction of the origin Location.
     */
    public float yawOffset = 0;
    public float pitchOffset = 0;

    /**
     * If set to false, Entity-bound locations will not update during the Effect
     */
    public boolean updateLocations = true;

    /**
     * If set to false, Entity-bound directions will not update during the Effect
     */
    public boolean updateDirections = true;

    /**
     * The Material and data to use for block and item break particles
     */
    public Material material;
    public Byte materialData;

    /**
     * These can be used to spawn multiple particles per packet.
     * It will not work with colored particles, however.
     */
    public int particleCount = 1;

    /**
     * These can be used to apply an offset to spawned particles, particularly
     * useful when spawning multiples.
     */
    public int particleOffsetX = 0;
    public int particleOffsetY = 0;
    public int particleOffsetZ = 0;

    public void display(ParticleEffect effect, Location location) {
        display(effect, location, this.color);
    }

    public void display(ParticleEffect particle, Location location, Color color) {
        display(particle, location, color, speed, particleCount);
    }

    public void display(ParticleEffect particle, Location location, float speed, int amount) {
        display(particle, location, this.color, speed, amount);
    }

    public void display(ParticleEffect particle, Location location, Color color, float speed, int amount) {
        particle.display(particle.getData(material, materialData), location, color, visibleRange, particleOffsetX, particleOffsetY, particleOffsetZ, speed, amount);
    }
}
