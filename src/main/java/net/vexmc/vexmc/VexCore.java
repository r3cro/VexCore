package net.vexmc.vexmc;

import net.vexmc.vexmc.listeners.ServerFixes.AntiPortalTrap;
import net.vexmc.vexmc.listeners.ServerFixes.ExplodableLava;
import net.vexmc.vexmc.listeners.ServerFixes.RaidableDefenses;
import net.vexmc.vexmc.mobs.Creeper;
import net.vexmc.vexmc.mobs.IronGolem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class VexCore extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AntiPortalTrap(), this);
        pm.registerEvents(new RaidableDefenses(), this);
        pm.registerEvents(new ExplodableLava(), this);
        pm.registerEvents(new IronGolem(), this);
        pm.registerEvents(new Creeper(), this);
    }

    private void registerCommands(){

    }

    /*(
    ALT LIMITER
    SWOOP RAIDALERTS
     */

}
