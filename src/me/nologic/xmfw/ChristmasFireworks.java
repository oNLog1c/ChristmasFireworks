package me.nologic.xmfw;

import me.nologic.xmfw.fragments.abstractfragment.AbstractPluginFragment;
import java.util.Random;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasFireworks extends JavaPlugin {
    private static ChristmasFireworks plugin;
    private Random random;
    
    static {
        ChristmasFireworks.plugin = null;
    }
    
    public ChristmasFireworks() {
        this.random = null;
    }
    
    public static ChristmasFireworks getPlugin() {
        return ChristmasFireworks.plugin;
    }
    
    public Random getRandom() {
        return this.random;
    }
    
    public void onEnable() {
        ChristmasFireworks.plugin = this;
        this.random = new Random();
        this.saveDefaultConfig();
        AbstractPluginFragment.Fragment.enableFragments();
    }

    // Test commit
    public void onDisable() {
        AbstractPluginFragment.Fragment.disableFragments();
    }

}