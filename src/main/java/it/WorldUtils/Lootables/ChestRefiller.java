package it.WorldUtils.Lootables;

import com.destroystokyo.paper.loottable.LootableBlockInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ChestRefiller implements Listener {
    long refillTimer = 1800000;
    public ChestRefiller(long timer){
        this.refillTimer = timer*1000;
    }
    @EventHandler
    public void set(InventoryOpenEvent e){
        if (e.getInventory().getHolder() instanceof LootableBlockInventory lbi && lbi.hasLootTable()){
            if (lbi.getLastFilled()<=System.currentTimeMillis()-refillTimer && !lbi.hasPendingRefill()){
                lbi.setNextRefill(System.currentTimeMillis()+1);
                lbi.getBlock().getState().update();
            }
        }

    }
}
