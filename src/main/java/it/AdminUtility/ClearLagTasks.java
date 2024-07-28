package it.AdminUtility;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

import static it.plugin.Plugin.*;
import static it.utils.ChatUtils.BroadcastToOPsSwitch;
public class ClearLagTasks {
    public static class ItemRemoveMsg extends BukkitRunnable{
        @Override
        public void run() {
            Bukkit.broadcast(Component.text("All items will be removed in 1 minute!!"));
            lgg.warning("All items will be removed in 1 minute!!");
        }
    }
    public static class ItemsRemove extends BukkitRunnable{
        @Override
        public void run() {
            for(World w : Bukkit.getWorlds()){
                for(Entity e : w.getEntities()){
                    if(e.getType() == EntityType.ITEM) e.remove();
                }
            }
        }
    }
    public static class ChunkCheck extends BukkitRunnable {
        @Override
        public void run() {
            for (World w : Bukkit.getWorlds()){
                for (Chunk c : w.getLoadedChunks()){
                    if (c.getEntities().length>=intMap.get("ClearLag.MaxAmountOfEntitiesInChunk")){
                        if (booleanMap.get("ClearLag.OPsVerbose")) BroadcastToOPsSwitch(c.getX() + ", " + c.getZ() + " in " + w.getName() +" is an abused chunk!!", 2);
                        lgg.log(Level.WARNING, c.getX() + ", " + c.getZ() + " in " + w.getName() +" is an abused chunk!!");
                    }
                }
            }
        }
    }
}
