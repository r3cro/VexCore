package net.vexmc.vexmc.tntchests.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestItem {

    public static ItemStack tntChest() {
        final ItemStack tntChest = new ItemStack(Material.CHEST);
        final ItemMeta tntMeta = tntChest.getItemMeta();
        tntMeta.setDisplayName(ChatColor.RED + "TNT Chest");
        tntChest.setItemMeta(tntMeta);
        return tntChest;
    }

}
