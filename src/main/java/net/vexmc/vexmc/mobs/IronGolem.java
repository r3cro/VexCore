package net.vexmc.vexmc.mobs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by logan on 12/29/17.
 */
public class IronGolem implements Listener{

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof org.bukkit.entity.IronGolem) {
            event.getDrops().clear();
            event.getDrops().add(new ItemStack(Material.IRON_INGOT, 2));
        }
    }

}
