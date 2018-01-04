package net.vexmc.vexmc.tntchests.listeners;

import net.vexmc.vexmc.tntchests.util.ChestItem;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ChestListener  implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlow(final EntityExplodeEvent e) {
        for (final Block block : e.blockList()) {
            if (block.getState() instanceof Chest) {
                final Chest c = (Chest)block.getState();
                if (!c.getInventory().getTitle().equals(ChatColor.RED + "TNT Chest")) {
                    continue;
                }
                block.getDrops().clear();
                block.getDrops().add(ChestItem.tntChest());
            }
        }
    }

}
