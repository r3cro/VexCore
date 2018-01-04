package net.vexmc.vexmc.tntchests;

import net.vexmc.vexmc.tntchests.util.ChestItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TNTChestAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        if(command.getName().equalsIgnoreCase("tntchest") && player.hasPermission("tntchest.admin") && args.length==0){
            player.getInventory().addItem(new ItemStack[] { ChestItem.tntChest() });
            player.sendMessage("givign ghist");
        }
        return true;
    }
}
