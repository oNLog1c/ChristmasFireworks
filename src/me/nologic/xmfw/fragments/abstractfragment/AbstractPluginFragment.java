package me.nologic.xmfw.fragments.abstractfragment;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import me.nologic.xmfw.fragments.ShootingFragment;

public abstract class AbstractPluginFragment {
    private Fragment fragment;
    
    public AbstractPluginFragment(final Fragment fragment) {
        this.fragment = fragment;
    }
    
    public Fragment getComponent() {
        return this.fragment;
    }
    
    public static ShootingFragment getShooting() {
        return (ShootingFragment)Fragment.SHOOTING.getInstance();
    }
    
    public abstract void enable();
    
    public abstract void disable();
    
    public enum Fragment
    {
        SHOOTING((Class<? extends AbstractPluginFragment>)ShootingFragment.class);
        
        private final AbstractPluginFragment INSTANCE;
        
        private Fragment(final Class<? extends AbstractPluginFragment> clazz) {
            AbstractPluginFragment instance = null;
            try {
                instance = (AbstractPluginFragment)clazz.getConstructor(Fragment.class).newInstance(this);
                Bukkit.getLogger().log(Level.INFO, "New instance created: " + clazz.getName());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.INSTANCE = instance;
        }
        
        public AbstractPluginFragment getInstance() {
            return this.INSTANCE;
        }
        
        public static void enableFragments() {
            final Fragment[] fragments = values();
            Fragment[] array;
            for (int length = (array = fragments).length, i = 0; i < length; ++i) {
                final Fragment fragment = array[i];
                fragment.getInstance().enable();
                Bukkit.getLogger().log(Level.INFO, "Fragment enabled: " + fragment.name());
            }
        }
        
        public static void disableFragments() {
            final Fragment[] fragments = values();
            Fragment[] array;
            for (int length = (array = fragments).length, i = 0; i < length; ++i) {
                final Fragment fragment = array[i];
                fragment.getInstance().disable();
                Bukkit.getLogger().log(Level.INFO, "Fragment disabled: " + fragment.name());
            }
        }
    }
}
