package me.nologic.xmfw.configuration;

import me.nologic.xmfw.ChristmasFireworks;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {
    private static final FileConfiguration config;
    public static final String DETONATE_PALETTE;
    public static final int ADDITIONAL_DETONATE_COLORS;
    public static final String FADE_PALETTE;
    public static final int ADDITIONAL_FADE_COLORS;
    public static final boolean ALWAYS_TRAILING;
    public static final boolean ALWAYS_FLICKERING;
    public static final List<String> EXPLOSION_EFFECTS;
    public static final int TASK_DURATION;
    public static final int FIREWORK_SPAWN_FREQUENCY;
    public static final String CHARGED_PROJECTILE_NAME;
    public static final String CROSSBOW_CHARGER;
    
    static {
        config = ChristmasFireworks.getPlugin().getConfig();
        DETONATE_PALETTE = Configuration.config.getString("Firework.Detonate.ColorPalette");
        ADDITIONAL_DETONATE_COLORS = Configuration.config.getInt("Firework.Detonate.AdditionalColors");
        FADE_PALETTE = Configuration.config.getString("Firework.Fade.ColorPalette");
        ADDITIONAL_FADE_COLORS = Configuration.config.getInt("Firework.Fade.AdditionalColors");
        ALWAYS_TRAILING = Configuration.config.getBoolean("Firework.AlwaysTrailing");
        ALWAYS_FLICKERING = Configuration.config.getBoolean("Firework.AlwaysFlickering");
        EXPLOSION_EFFECTS = Configuration.config.getStringList("Firework.AllowedExplosionEffectTypes");
        TASK_DURATION = Configuration.config.getInt("Task.Duration");
        FIREWORK_SPAWN_FREQUENCY = Configuration.config.getInt("Task.Frequency");
        CHARGED_PROJECTILE_NAME = Configuration.config.getString("Crossbow.ChargedProjectileName");
        CROSSBOW_CHARGER = Configuration.config.getString("Crossbow.Charger");
    }
}