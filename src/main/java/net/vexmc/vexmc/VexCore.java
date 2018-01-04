package net.vexmc.vexmc;

import net.milkbowl.vault.economy.Economy;
import net.vexmc.vexmc.commands.DiscordCmd;
import net.vexmc.vexmc.listeners.ServerFixes.AntiPortalTrap;
import net.vexmc.vexmc.listeners.ServerFixes.ExplodableLava;
import net.vexmc.vexmc.listeners.ServerFixes.RaidableDefenses;
import net.vexmc.vexmc.mobs.Creeper;
import net.vexmc.vexmc.mobs.IronGolem;
import net.vexmc.vexmc.tntchests.TNTChestAdmin;
import net.vexmc.vexmc.tntchests.util.CmdFactionsTnt;
import net.vexmc.vexmc.tntchests.handlers.FactionHandler;
import net.vexmc.vexmc.tntchests.listeners.BlockListener;
import net.vexmc.vexmc.tntchests.listeners.ChestListener;
import net.vexmc.vexmc.tntchests.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class VexCore extends JavaPlugin {

    private FactionHandler factionHandler;
    public Economy econ;
    public static VexCore instance;

    public VexCore(){
        this.factionHandler=new FactionHandler(this);
        this.econ=null;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerListeners();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.setupEconomy();
        this.factionHandler.loadAmounts();
        VexCore.instance=this;
    }

    @Override
    public void onDisable() {
        this.factionHandler.saveAmounts();
    }

    private void registerListeners(){
        PluginManager pm = Bukkit.getPluginManager();
//        pm.registerEvents(new AntiPortalTrap(), this);
        pm.registerEvents(new RaidableDefenses(), this);
        pm.registerEvents(new ExplodableLava(), this);
        pm.registerEvents(new IronGolem(), this);
        pm.registerEvents(new Creeper(), this);

        pm.registerEvents(new BlockListener(this), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new ChestListener(), this);
        Bukkit.getPluginManager().registerEvents((Listener)new CmdFactionsTnt(this), (Plugin)this);
    }

    private void registerCommands(){
        getCommand("tntchest").setExecutor(new TNTChestAdmin());
        getCommand("discord").setExecutor(new DiscordCmd());
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = (Economy)rsp.getProvider();
        return this.econ != null;
    }

    public FactionHandler getFactionHandler() {
        return this.factionHandler;
    }

}
