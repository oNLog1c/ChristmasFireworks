package me.nologic.xmfw.tasks;

import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import me.nologic.xmfw.ChristmasFireworks;
import me.nologic.xmfw.configuration.Configuration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import java.util.Random;
import me.nologic.xmfw.factory.FireworkFactory;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkTask extends BukkitRunnable {
    private EntityShootBowEvent event;
    private FireworkFactory factory;
    private Random rnd;
    
    public FireworkTask(final EntityShootBowEvent event, final FireworkFactory factory, final Random rnd) {
        this.factory = factory;
        this.event = event;
        this.rnd = rnd;
    }
    
    public void run() {
        try {
            final Player player = (Player)this.event.getEntity();
            final Firework firework = (Firework)player.getWorld().spawn(player.getLocation().add(0.0, 1.3, 0.0), (Class)Firework.class);
            final FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffect(this.factory.getConfiguredFireworkEffect());
            firework.setVelocity(this.event.getProjectile().getVelocity().normalize());
            meta.setPower(this.rnd.nextInt(1));
            firework.setFireworkMeta(meta);
            for (int i = 40; i < Configuration.TASK_DURATION + 40; i += Configuration.FIREWORK_SPAWN_FREQUENCY) {
                new SubFireworkTask(firework, this.factory, this.rnd).runTaskLater((Plugin)ChristmasFireworks.getPlugin(), (long)i);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
