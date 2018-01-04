package net.vexmc.vexmc.tntchests.util;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import net.vexmc.vexmc.VexCore;
import net.vexmc.vexmc.tntchests.util.TNTUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CmdFactionsTnt implements Listener
{
    private VexCore plugin;

    public CmdFactionsTnt(final VexCore pl) {
        this.plugin = pl;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void infoCommand(final PlayerCommandPreprocessEvent e) {
        final String[] args = e.getMessage().split(" ");
        if (args.length < 2) {
            return;
        }
        final String first = String.valueOf(args[0]) + " " + args[1];
        if (!first.equalsIgnoreCase("/f tnt")) {
            return;
        }
        e.setCancelled(true);
        final Player p = e.getPlayer();
        if (args.length == 2) {
            for (final String s : this.plugin.getConfig().getStringList("InfoMessage")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
        }
        if (args.length == 3) {
            if (args[2].equalsIgnoreCase("balance")) {
                final int tnt = this.plugin.getFactionHandler().getTNT(MPlayer.get((Object)p).getFaction());
                String message = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("TNTBalance"));
                message = message.replaceAll("%tnt%", String.valueOf(tnt));
                p.sendMessage(message);
            }
            if (args[2].equalsIgnoreCase("withdraw")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("SpecifyAmount")));
            }
        }
        if (args.length == 4) {
            if (args[2].equalsIgnoreCase("balance")) {
                final Faction faction = FactionColl.get().getByName(args[3]);
                if (faction == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("InvalidFaction")));
                    return;
                }
                final int tnt2 = this.plugin.getFactionHandler().getTNT(faction);
                String message2 = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("TNTBalance"));
                message2 = message2.replaceAll("%tnt%", String.valueOf(tnt2));
                p.sendMessage(message2);
            }
            if (args[2].equalsIgnoreCase("withdraw")) {
                if (MPlayer.get((Object)p).getRole().isAtLeast(Rel.OFFICER)) {
                    int amount;
                    try {
                        amount = Integer.valueOf(args[3]);
                    }
                    catch (NumberFormatException ex) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("InvalidFormat")));
                        return;
                    }
                    if (this.plugin.getFactionHandler().getTNT(MPlayer.get((Object)p).getFaction()) < amount) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("NotEnoughTNT")));
                    }
                    else {
                        TNTUtil.withdraw(p, amount);
                    }
                }
                else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("OfficerOrHigher")));
                }
            }
        }
    }
}