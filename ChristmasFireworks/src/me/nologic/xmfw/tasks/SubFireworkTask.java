package me.nologic.xmfw.tasks;

import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.inventory.meta.FireworkMeta;
import java.util.Random;
import me.nologic.xmfw.factory.FireworkFactory;
import org.bukkit.entity.Firework;
import org.bukkit.scheduler.BukkitRunnable;

public class SubFireworkTask extends BukkitRunnable {
    private Firework entity;
    private FireworkFactory factory;
    private Random rnd;
    
    public SubFireworkTask(final Firework entity, final FireworkFactory factory, final Random rnd) {
        this.factory = factory;
        this.entity = entity;
        this.rnd = rnd;
    }
    
    public void run() {
        final Firework firework = (Firework)this.entity.getWorld().spawn(this.entity.getLocation(), (Class)Firework.class);
        final FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(this.factory.getConfiguredFireworkEffect());
        try {
            firework.setVelocity(this.entity.getLocation().toVector().subtract(this.getVector(this.entity.getLocation())).normalize());
        }
        catch (Exception ex) {
            this.cancel();
        }
        meta.setPower(this.rnd.nextInt(1));
        firework.setFireworkMeta(meta);
    }
    
    private Vector getVector(final Location loc) {
        final double x = loc.getX() - this.rnd.nextInt(10) + this.rnd.nextInt(10);
        final double y = loc.getY() - this.rnd.nextInt(5) + this.rnd.nextInt(5);
        final double z = loc.getZ() - this.rnd.nextInt(10) + this.rnd.nextInt(10);
        return new Vector(x, y, z);
    }
}
