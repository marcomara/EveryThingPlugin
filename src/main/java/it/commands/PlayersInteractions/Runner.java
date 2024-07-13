package it.commands.PlayersInteractions;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import static it.plugin.Plugin.sitlist;

public class Runner extends BukkitRunnable {
    @Override
    public void run() {
        int i = 0;
        int m = sitlist.size();
        while(i<m){
            Entity e = sitlist.get(i);
            if(e.getPassengers().isEmpty()){
                e.remove();
                sitlist.remove(i);
                //lgg.warning("removed slime with uuid: " + e.getUniqueId());
                m--;
                continue;
            }
            i++;
        }
    }
}
