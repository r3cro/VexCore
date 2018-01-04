package net.vexmc.vexmc.tntchests.listeners;

import com.massivecraft.massivecore.ps.*;
import net.vexmc.vexmc.VexCore;
import org.bukkit.scheduler.*;
import com.massivecraft.factions.entity.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;

public class InventoryListener implements Listener
{
    private VexCore plugin;

    public InventoryListener(final VexCore pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final Inventory topInventory = e.getView().getTopInventory();
        final Location chestLoc = this.getLocation(topInventory);
        if (topInventory.getTitle().equals(ChatColor.RED + "TNT Chest")) {
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == null) {
                return;
            }
            if (e.getCurrentItem().getType() == Material.TNT) {
                final Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(chestLoc));
                new BukkitRunnable() {
                    public void run() {
                        final int amount = InventoryListener.this.calculateAmount(topInventory, factionAt);
                        InventoryListener.this.plugin.getFactionHandler().addTNT(factionAt, amount);
                    }
                }.runTaskLater((Plugin)this.plugin, 1L);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void hopperMoveItem(final InventoryMoveItemEvent e) {
        if (e.getInitiator() == null || e.getSource() == null || e.getItem() == null) {
            return;
        }
        if (e.getInitiator().getType() != InventoryType.HOPPER) {
            if (e.getInitiator().getType() == InventoryType.CHEST && e.getInitiator().getTitle().equals(ChatColor.RED + "TNT Chest")) {
                e.setCancelled(true);
            }
            return;
        }
        if (e.getDestination().getTitle().equals(ChatColor.RED + "TNT Chest")) {
            final Location loc = this.getLocation(e.getDestination());
            final Faction fac = BoardColl.get().getFactionAt(PS.valueOf(loc));
            new BukkitRunnable() {
                public void run() {
                    final int amount = InventoryListener.this.calculateAmount(e.getDestination(), fac);
                    InventoryListener.this.plugin.getFactionHandler().addTNT(fac, amount);
                }
            }.runTaskLater((Plugin)this.plugin, 1L);
        }
    }

    public int calculateAmount(final Inventory inv, final Faction faction) {
        final int size = inv.getSize();
        int amount = 0;
        for (int i = 0; i < size; ++i) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() == Material.TNT) {
                amount += inv.getItem(i).getAmount();
            }
        }
        final ItemStack[] all = { new ItemStack(Material.TNT, amount) };
        final int tnt = amount / 5;
        final int left = amount % 5;
        if (!this.plugin.getFactionHandler().isFull(faction, amount)) {
            final ItemStack[] keep = { new ItemStack(Material.TNT, left) };
            inv.removeItem(all);
            if (left != 0) {
                inv.addItem(keep);
            }
            return tnt;
        }
        return 0;
    }

    private Location getLocation(final Inventory inventory) {
        final InventoryHolder holder = inventory.getHolder();
        if (holder != null) {
            if (holder instanceof Chest) {
                return ((Chest)holder).getLocation();
            }
            if (holder instanceof DoubleChest) {
                return ((DoubleChest)holder).getLocation();
            }
            if (holder instanceof BlockState) {
                return ((BlockState)holder).getLocation();
            }
        }
        return null;
    }
}