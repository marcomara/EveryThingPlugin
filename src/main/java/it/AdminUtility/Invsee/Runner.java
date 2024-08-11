package it.AdminUtility.Invsee;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Runner extends BukkitRunnable {
    Player p, t;
    Inventory dinv;
    Listener l;
    public Runner(Player p, Player t, Inventory dinv, Listener l) {
        this.p = p;
        this.t = t;
        this.dinv = dinv;
        this.l=l;
    }

    @Override
    public void run() {
        int n = 0;
        for (ItemStack i : dinv){
            if(n<41){
                t.getInventory().setItem(n, i);
                n++;
            }
        }
        if (!dinv.getViewers().contains(p)) {
            this.cancel();
            HandlerList.unregisterAll(l);
        }
    }
}
