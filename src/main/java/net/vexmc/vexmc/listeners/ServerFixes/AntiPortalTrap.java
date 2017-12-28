package net.vexmc.vexmc.listeners.ServerFixes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AntiPortalTrap implements Listener {

    @EventHandler
    public void teleportToSpawn(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if(location.getBlock().getType()== Material.PORTAL){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn "+player.getName());
        }
    }
}
