package me.nologic.xmfw.factory.enums;

import me.nologic.xmfw.ChristmasFireworks;
import java.util.ArrayList;
import org.bukkit.Color;
import java.util.List;

public enum Palette {
    RANDOM("RANDOM", 0, "RANDOM", 0, new Color[0]), 
    CHRISTMAS("CHRISTMAS", 1, "CHRISTMAS", 1, new Color[] { Color.WHITE, Color.RED, Color.GREEN, Color.LIME, Color.ORANGE, Color.YELLOW });
    
    private List<Color> colors;
    
    private Palette(final String name, final int ordinal, final String s, final int n, final Color... colors) {
        this.colors = new ArrayList<Color>();
        for (final Color color : colors) {
            this.colors.add(color);
        }
    }
    
    public Color pickRandomColor() {
        return this.colors.get(ChristmasFireworks.getPlugin().getRandom().nextInt(this.colors.size()));
    }
}
