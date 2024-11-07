package it.WorldUtils.Lootables;

import com.destroystokyo.paper.loottable.LootableBlockInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootContext;

import java.util.Random;


public class PersonalLoot implements Listener {
    @EventHandler
    public void LootableByPlayer(InventoryOpenEvent e){
        if (e.getInventory().getType() == InventoryType.CHEST){
            if (e.getInventory().getHolder() instanceof LootableBlockInventory lbi && lbi.hasLootTable()){
                assert lbi.getLootTable() != null;
                if (!lbi.hasPlayerLooted(e.getPlayer().getUniqueId())){
                    Inventory i = Bukkit.createInventory(null, 27);
                    Random r = new Random();
                    lbi.getLootTable().fillInventory(i, r, new LootContext.Builder(e.getPlayer().getLocation()).lootedEntity(null).luck(1).build());
                }
            }
        }
    }
}