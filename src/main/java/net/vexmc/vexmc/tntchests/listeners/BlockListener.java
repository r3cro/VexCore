package net.vexmc.vexmc.tntchests.listeners;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import net.vexmc.vexmc.VexCore;
import net.vexmc.vexmc.tntchests.util.ChestItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockListener implements Listener
{
    public VexCore plugin;

    public BlockListener(final VexCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e) {
        if (e.getItemInHand() == null || (e.getItemInHand().getType() != Material.CHEST && e.getItemInHand().getType() != Material.TRAPPED_CHEST)) {
            return;
        }
        if (!e.getItemInHand().hasItemMeta() || !e.getItemInHand().getItemMeta().hasDisplayName()) {
            return;
        }
        final ItemStack inHand = e.getItemInHand();
        final ItemMeta inHandMeta = inHand.getItemMeta();
        if (inHandMeta.getDisplayName().equals(ChatColor.RED + "TNT Chest")) {
            final Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(e.getBlock()));
            if (!factionAt.isNormal()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Wilderness")));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.CHEST || e.getBlock().getType() == Material.TRAPPED_CHEST) {
            final Chest chest = (Chest)e.getBlock().getState();
            if (chest.getInventory().getTitle().equals(ChatColor.RED + "TNT Chest")) {
                e.getBlock().getDrops().clear();
                e.getBlock().getDrops().add(ChestItem.tntChest());
            }
        }
    }
}
