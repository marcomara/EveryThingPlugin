package it.commands.XpFarmChunks;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static it.commands.XpFarmChunks.XpFarmChunk.chunks;

public class Tasks extends BukkitRunnable implements Listener {

    @Override
    public void run() {
        for (Chunk c : chunks){
            for (Entity e : c.getEntities()){
                if (e.getType() == EntityType.ITEM){
                    e.remove();
                }
            }
        }
    }


    @EventHandler
    public void NoItemDrop(EntityDeathEvent e){
        if (chunks.contains(e.getEntity().getChunk())&&!(e.getEntity() instanceof Player)) {
            e.setShouldPlayDeathSound(false);
            e.getDrops().clear();
        }
    }
}
