package net.vexmc.vexmc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCmd implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if(command.getName().equalsIgnoreCase("discord"))
            player.sendMessage("§c§lVEXMC: §fhttps://discord.gg/fGbRFCp");
        return true;
    }
}
