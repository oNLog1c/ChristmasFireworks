package me.nologic.xmfw.factory;

import org.bukkit.Color;
import me.nologic.xmfw.factory.enums.Palette;
import me.nologic.xmfw.configuration.Configuration;
import java.util.Random;
import org.bukkit.FireworkEffect;

public class FireworkFactory {
    private FireworkEffect.Builder builder;
    private static Random rnd;
    
    public FireworkFactory() {
        FireworkFactory.rnd = new Random();
    }
    
    public FireworkEffect getConfiguredFireworkEffect() {
        this.builder = FireworkEffect.builder();
        this.useTrail();
        this.useFlicker();
        this.pickDetonatePalette(Palette.valueOf(Configuration.DETONATE_PALETTE));
        this.pickFadePalette(Palette.valueOf(Configuration.FADE_PALETTE));
        this.pickEffectType();
        return this.builder.build();
    }
    
    private void useFlicker() {
        if (Configuration.ALWAYS_TRAILING) {
            this.builder.flicker(true);
        }
        else {
            this.builder.flicker(FireworkFactory.rnd.nextBoolean());
        }
    }
    
    private void useTrail() {
        if (Configuration.ALWAYS_TRAILING) {
            this.builder.trail(true);
        }
        else {
            this.builder.trail(FireworkFactory.rnd.nextBoolean());
        }
    }
    
    private void pickDetonatePalette(final Palette detonatePalette) {
        switch (detonatePalette) {
            case RANDOM: {
                this.builder.withColor(Color.fromRGB(FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255)));
                if (Configuration.ADDITIONAL_DETONATE_COLORS == 0) {
                    return;
                }
                for (int x = 0; x < FireworkFactory.rnd.nextInt(Configuration.ADDITIONAL_DETONATE_COLORS) + 1; ++x) {
                    this.builder.withColor(Color.fromRGB(FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255)));
                }
            }
            case CHRISTMAS: {
                this.builder.withColor(Palette.CHRISTMAS.pickRandomColor());
                if (Configuration.ADDITIONAL_DETONATE_COLORS == 0) {
                    return;
                }
                for (int x = 0; x < FireworkFactory.rnd.nextInt(Configuration.ADDITIONAL_DETONATE_COLORS) + 1; ++x) {
                    this.builder.withColor(Palette.CHRISTMAS.pickRandomColor());
                }
                break;
            }
        }
    }
    
    private void pickFadePalette(final Palette fadePalette) {
        switch (fadePalette) {
            case RANDOM: {
                this.builder.withFade(Color.fromRGB(FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255)));
                if (Configuration.ADDITIONAL_FADE_COLORS == 0) {
                    return;
                }
                for (int x = 0; x < FireworkFactory.rnd.nextInt(Configuration.ADDITIONAL_FADE_COLORS) + 1; ++x) {
                    this.builder.withFade(Color.fromRGB(FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255), FireworkFactory.rnd.nextInt(255)));
                }
            }
            case CHRISTMAS: {
                this.builder.withFade(Palette.CHRISTMAS.pickRandomColor());
                if (Configuration.ADDITIONAL_FADE_COLORS == 0) {
                    return;
                }
                for (int x = 0; x < FireworkFactory.rnd.nextInt(Configuration.ADDITIONAL_FADE_COLORS) + 1; ++x) {
                    this.builder.withFade(Palette.CHRISTMAS.pickRandomColor());
                }
                break;
            }
        }
    }
    
    private void pickEffectType() {
        this.builder.with(FireworkEffect.Type.valueOf((String)Configuration.EXPLOSION_EFFECTS.get(FireworkFactory.rnd.nextInt(Configuration.EXPLOSION_EFFECTS.size()))));
    }
}
