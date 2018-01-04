package net.vexmc.vexmc.tntchests.handlers;

import com.massivecraft.factions.entity.Faction;
import net.vexmc.vexmc.VexCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionHandler {

    private HashMap<String, Integer> tntAmounts;
    private VexCore plugin;
    private static FactionHandler instance;

    public FactionHandler(final VexCore pl){
        this.tntAmounts = new HashMap<String, Integer>();
        this.plugin=pl;
        FactionHandler.instance=this;
    }

    public static FactionHandler getInstance(){
        return FactionHandler.instance;
    }

    public boolean factionHasBank(final Faction faction) {
        return faction != null && faction.getId() != null && this.tntAmounts.containsKey(faction.getId());
    }

    public int getTNT(final Faction faction) {
        if (this.factionHasBank(faction)) {
            return this.tntAmounts.get(faction.getId());
        }
        return 0;
    }

    public void addTNT(final Faction faction, final int amount) {
        if (faction != null && faction.getId() != null) {
            if (this.factionHasBank(faction)) {
                if (amount > 0) {
                    this.tntAmounts.put(faction.getId(), this.tntAmounts.get(faction.getId()) + amount);
                }
            }
            else {
                this.tntAmounts.put(faction.getId(), amount);
            }
        }
    }

    public void removeTNT(final Faction faction, final int amount) {
        if (faction != null && faction.getId() != null && amount > 0) {
            this.tntAmounts.put(faction.getId(), this.getTNT(faction) - amount);
        }
    }

    public boolean isFull(final Faction faction, final int amount) {
        return this.getTNT(faction) + amount > 2147483647;
    }

    public void loadAmounts() {
        for (final String key : this.plugin.getConfig().getStringList("TNT")) {
            final String[] parts = key.split(" ");
            this.tntAmounts.put(parts[0], Integer.valueOf(parts[1]));
        }
    }


    public void saveAmounts() {
        final List<String> listAmounts = new ArrayList<String>();
        for (final String key : this.tntAmounts.keySet()) {
            listAmounts.add(String.valueOf(key) + " " + String.valueOf(this.tntAmounts.get(key)));
        }
        this.plugin.getConfig().set("TNT", (Object)listAmounts);
        this.plugin.saveConfig();
    }

}
