package net.vexmc.vexmc.listeners.ServerFixes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class ExplodableLava implements Listener {

    private boolean creeperExplosion;
    private boolean waterExplosion;
    private boolean turnSourceintoFlowing;
    private boolean explodeFlowing;
    private boolean whitelist;
    private List<String> worlds;

    public ExplodableLava(){
        this.creeperExplosion=false;
        this.waterExplosion=true;
        this.turnSourceintoFlowing=false;
        this.explodeFlowing=true;
        this.whitelist=false;
        this.worlds=new ArrayList<String>();
    }


    @EventHandler
    public void onExplode(final EntityExplodeEvent event) {
        final Location location = event.getLocation();
        if (this.waterExplosion) {
            final Material mat = location.getBlock().getType();
            if (mat.equals((Object)Material.WATER) || mat.equals((Object)Material.STATIONARY_WATER)) {
                return;
            }
            if (this.whitelist) {
                final boolean contains = this.worlds.contains(location.getWorld().getName());
                if (contains) {
                    this.explodeLava(event.getEntityType(), location);
                }
            }
            else {
                this.explodeLava(event.getEntityType(), location);
            }
        }
        else if (this.whitelist) {
            final boolean contains2 = this.worlds.contains(location.getWorld().getName());
            if (contains2) {
                this.explodeLava(event.getEntityType(), location);
            }
        }
        else {
            this.explodeLava(event.getEntityType(), location);
        }
    }

    private void explodeLava(final EntityType et, final Location location) {
        if (et == EntityType.PRIMED_TNT) {
            if (this.turnSourceintoFlowing) {
                this.explodeSources(location);
            }
            else {
                this.explode(location);
            }
        }
        else if (et == EntityType.CREEPER && this.creeperExplosion) {
            if (this.turnSourceintoFlowing) {
                this.explodeSources(location);
            }
            else {
                this.explode(location);
            }
        }
    }

    private void explode(final Location location) {
        for (int r = 2, x = r * -1; x <= r; ++x) {
            for (int y = r * -1; y <= r; ++y) {
                for (int z = r * -1; z <= r; ++z) {
                    final Block b = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    if (b.getType().equals((Object) Material.LAVA) || b.getType().equals((Object)Material.STATIONARY_LAVA)) {
                        b.setType(Material.AIR);
                    }
                }
            }
        }
    }

    private void explodeSources(final Location location) {
        for (int r = 2, x = r * -1; x <= r; ++x) {
            for (int y = r * -1; y <= r; ++y) {
                for (int z = r * -1; z <= r; ++z) {
                    final Block b = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    if (b.getType().equals((Object)Material.STATIONARY_LAVA)) {
                        b.setType(Material.LAVA);
                    }
                    else if (b.getType().equals((Object)Material.LAVA) && this.explodeFlowing) {
                        b.setType(Material.AIR);
                    }
                }
            }
        }
    }

}
