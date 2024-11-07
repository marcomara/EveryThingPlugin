package it.AdminUtility.ClearLag;

import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;

import java.util.Random;

import static it.plugin.Plugin.lgg;


public class ChestReplacerHandler implements Listener {
    @EventHandler
    public void ChestReplacer(ChunkLoadEvent e){
        if (e.getChunk().isGenerated()) return;
        for (BlockState b : e.getChunk().getTileEntities()){
            if (b.getBlock().getType()== Material.CHEST){
                Block bl = b.getBlock();
                Chest c = (Chest) bl.getState();
                if(c.hasLootTable()){
                    LootContext lc = new LootContext.Builder(bl.getLocation()).lootedEntity(null).luck(new Random().nextFloat() *2).build();
                    for (ItemStack is : c.getLootTable().populateLoot(new Random(), lc)){
                        c.getInventory().addItem(is);
                    }
                }
                c.update();
                Inventory ci = c.getInventory();
                ItemStack[] isc = ci.getContents();
                bl.setType(Material.BARREL);
                bl.getState().update();
                Barrel bar = (Barrel) bl.getState();
                bar.getSnapshotInventory().setContents(isc);
                bar.update();
                lgg.warning("Updated chest at: " + b.getBlock().getX() + " " + b.getBlock().getY() + " " + b.getBlock().getZ() + " -> transformed in a barrel");
            }
        }

    }
}
