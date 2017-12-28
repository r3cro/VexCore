package net.vexmc.vexmc.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class WildCommand implements CommandExecutor {

    public HashMap<String, Long> cooldowns;
    Random random;
    Location tp;
    int x;
    int y;
    int z;
    int zMax;
    int zMin;
    int xMax;
    int xMin;
    int i;

    public WildCommand(){
        this.cooldowns=new HashMap<String, Long>();
        this.random=new Random();
        this.i=0;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("wild")) {
            if(args.length >= 2 && !(sender instanceof Player)){
                final Player tped= Bukkit.getPlayer(args[0]);
                if(tped==null){
                    return false;
                }
                if(!tped.isOnline()){
                    return false;
                }
                final World world = Bukkit.getServer().getWorld(args[1]);
                if(world==null){
                    return false;
                }

            }
        }
        return true;
    }

    public void genCoord(final World world, final Player player){
        ++this.i;
        if(this.i>300){
            player.sendMessage("No available coords found.");
            return;
        }
        this.x=xMax+1-xMin+xMin;
        this.z = zMax+1-zMin+zMin;
        this.y= world.getHighestBlockYAt(this.x,this.z);
        this.tp = new Location(player.getWorld(), this.x + 0.5, (double)this.y, this.z + 0.5);
        if(world.getName().equalsIgnoreCase("flatnether")) return;
        if(world.getName().equalsIgnoreCase("flatend")) return;
        this.tp=new Location(player.getWorld(), this.x+0.5,(double)this.y,this.z+0.5);
        final Location tpy = new Location(this.tp.getWorld(),this.tp.getX(),this.tp.getY()-1,this.tp.getZ());
        if (this.tp.getWorld().getBlockAt(this.tp).getType().equals((Object) Material.LAVA) || this.tp.getWorld().getBlockAt(this.tp).getType().equals((Object)Material.STATIONARY_LAVA) || TPy.getWorld().getBlockAt(TPy).getType().equals((Object)Material.STATIONARY_LAVA)) {
            this.genCoord(player.getWorld(), player);
            return;
        }

        this.i = 0;
    }
}
