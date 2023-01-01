package me.nologic.xmfw.fragments;

import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.NamespacedKey;
import me.nologic.xmfw.tasks.FireworkTask;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemFlag;
import java.util.List;
import me.nologic.xmfw.configuration.Configuration;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import java.util.Random;
import org.bukkit.inventory.Recipe;
import me.nologic.xmfw.factory.FireworkFactory;
import me.nologic.xmfw.ChristmasFireworks;
import org.bukkit.event.Listener;
import me.nologic.xmfw.fragments.abstractfragment.AbstractPluginFragment;

public class ShootingFragment extends AbstractPluginFragment implements Listener {
    private ChristmasFireworks INSTANCE;
    private FireworkFactory fireworkFactory;
    private Recipe chargedCrossbow;
    private Random rnd;
    
    public ShootingFragment(final Fragment fragment) {
        super(fragment);
    }
    
    @Override
    public void enable() {
        this.INSTANCE = ChristmasFireworks.getPlugin();
        this.rnd = this.INSTANCE.getRandom();
        this.fireworkFactory = new FireworkFactory();
        this.chargedCrossbow = this.addCustomCrossbowRecipe();
        this.INSTANCE.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.INSTANCE);
    }
    
    @Override
    public void disable() {
        if (this.chargedCrossbow != null) {
            final Iterator<Recipe> it = (Iterator<Recipe>)this.INSTANCE.getServer().recipeIterator();
            while (it.hasNext()) {
                final Recipe recipe = it.next();
                if (recipe.getResult().isSimilar(this.chargedCrossbow.getResult())) {
                    it.remove();
                }
            }
        }
    }
    
    @EventHandler
    private void craft(final PrepareItemCraftEvent event) {
        try {
            if (event.getRecipe().getResult().isSimilar(this.chargedCrossbow.getResult())) {
                final List<ItemStack> projectiles = new ArrayList<ItemStack>();
                final ItemStack[] matrix = event.getInventory().getContents();
                ItemStack result = null;
                CrossbowMeta crossmeta = null;
                ItemStack[] array;
                for (int length = (array = matrix).length, i = 0; i < length; ++i) {
                    final ItemStack item = array[i];
                    if (item.getType() == Material.CROSSBOW) {
                        result = item.clone();
                        crossmeta = (CrossbowMeta)item.getItemMeta();
                    }
                }
                final ItemStack firework = new ItemStack(Material.FIREWORK_ROCKET);
                final ItemMeta firemeta = firework.getItemMeta();
                firemeta.setDisplayName(Configuration.CHARGED_PROJECTILE_NAME);
                firework.setItemMeta(firemeta);
                projectiles.add(firework);
                crossmeta.setChargedProjectiles((List)projectiles);
                crossmeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
                result.setItemMeta((ItemMeta)crossmeta);
                event.getInventory().setResult(result);
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @EventHandler
    private void onCrossbowShoot(final EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getBow().getType() == Material.CROSSBOW) {
            final CrossbowMeta meta = (CrossbowMeta)event.getBow().getItemMeta();
            if (!meta.getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)) {
                return;
            }
            meta.setChargedProjectiles((List)null);
            event.getBow().setItemMeta((ItemMeta)meta);
            meta.removeItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
            event.getBow().setItemMeta((ItemMeta)meta);
            event.setCancelled(true);
            new FireworkTask(event, this.fireworkFactory, this.rnd).runTask((Plugin)this.INSTANCE);
        }
    }
    
    private Recipe addCustomCrossbowRecipe() {
        final ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        final NamespacedKey key = new NamespacedKey((Plugin)this.INSTANCE, "charged_crossbow");
        final ShapelessRecipe chargedCrossbowRecipe = new ShapelessRecipe(key, crossbow);
        chargedCrossbowRecipe.addIngredient(1, Material.CROSSBOW);
        chargedCrossbowRecipe.addIngredient(1, Material.valueOf(Configuration.CROSSBOW_CHARGER));
        this.INSTANCE.getServer().addRecipe((Recipe)chargedCrossbowRecipe);
        return (Recipe)chargedCrossbowRecipe;
    }
}
