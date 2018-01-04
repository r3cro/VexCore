package net.vexmc.vexmc.tntchests.util;

import com.massivecraft.factions.entity.MPlayer;
import net.vexmc.vexmc.VexCore;
import net.vexmc.vexmc.tntchests.handlers.FactionHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TNTUtil {

    public static void withdraw(final Player p, final int amount) {
        final int price = amount * VexCore.instance.getConfig().getInt("WithdrawCost");
        FactionHandler.getInstance().removeTNT(MPlayer.get((Object)p).getFaction(), amount);
        if (VexCore.instance.econ.getBalance((OfflinePlayer)p) >= price) {
            final ItemStack[] tnt = { new ItemStack(Material.TNT, amount) };
            p.getInventory().addItem(tnt);
            VexCore.instance.econ.withdrawPlayer((OfflinePlayer)p, (double)price);
            String message = ChatColor.translateAlternateColorCodes('&', VexCore.instance.getConfig().getString("Withdraw"));
            message = message.replaceAll("%tnt%", String.valueOf(amount));
            message = message.replaceAll("%price%", String.valueOf(price));
            p.sendMessage(message);
        }
        else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', VexCore.instance.getConfig().getString("InsufficientFunds")));
        }
    }

}
