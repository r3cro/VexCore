package net.vexmc.vexmc.listeners.ServerFixes;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RaidableDefenses implements Listener {

    private static final Set<Material> destroyBlocks = new HashSet<>(Arrays.asList(
            Material.ANVIL,
            Material.BREWING_STAND,
            Material.CACTUS,
            Material.CHEST,
            Material.COBBLE_WALL,
            Material.COCOA,
            Material.ENCHANTMENT_TABLE,
            Material.FENCE,
            Material.FENCE_GATE,
            Material.IRON_FENCE,
            Material.IRON_DOOR_BLOCK,
            Material.NETHER_FENCE,
            Material.STAINED_GLASS_PANE,
            Material.THIN_GLASS,
            Material.TRAPPED_CHEST,
            Material.WOODEN_DOOR,
            Material.BED,
            Material.BED_BLOCK
    ));

    static {
        try {
            Material.valueOf("ACACIA_DOOR");

            destroyBlocks.add(Material.ACACIA_DOOR);
            destroyBlocks.add(Material.BIRCH_DOOR);
            destroyBlocks.add(Material.DARK_OAK_DOOR);
            destroyBlocks.add(Material.JUNGLE_DOOR);
            destroyBlocks.add(Material.SPRUCE_DOOR);

            destroyBlocks.add(Material.ACACIA_FENCE_GATE);
            destroyBlocks.add(Material.BIRCH_FENCE_GATE);
            destroyBlocks.add(Material.DARK_OAK_FENCE_GATE);
            destroyBlocks.add(Material.JUNGLE_FENCE_GATE);
            destroyBlocks.add(Material.SPRUCE_FENCE_GATE);

            destroyBlocks.add(Material.ACACIA_FENCE);
            destroyBlocks.add(Material.BIRCH_FENCE);
            destroyBlocks.add(Material.DARK_OAK_FENCE);
            destroyBlocks.add(Material.JUNGLE_FENCE);
            destroyBlocks.add(Material.SPRUCE_FENCE);
        } catch (IllegalArgumentException | NoSuchFieldError ignored) {

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void destroyBlocks(ExplosionPrimeEvent event) {
        int radius = NumberConversions.floor(event.getRadius());
        Location loc = event.getEntity().getLocation();
        World world = loc.getWorld();

        for (int x = -radius; x < radius; ++x) {
            for (int z = -radius; z < radius; ++z) {
                for (int y = -radius; y < radius; ++y) {
                    Location l = loc.clone();
                    Block block = l.add((double) x, (double) y, (double) z).getBlock();

                    if (!destroyBlocks.contains(block.getType())) continue;

                    world.playEffect(l, Effect.MOBSPAWNER_FLAMES, 0, 64);
                    block.breakNaturally();
                }
            }
        }
    }

}
