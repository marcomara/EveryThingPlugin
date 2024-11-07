package it.commands.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.save;

public class WorldHandler {
    public static void changeWorld(Player p, World w){
        Object o = pfyml.get(p.getUniqueId()+".worldPositions."+w.getName());
        if(o instanceof Location l){
            savePlayerData(p,p.getWorld());
            CompletableFuture<Boolean>tp= p.teleportAsync(w.getSpawnLocation());
            Bukkit.getScheduler().runTaskTimer(plugin,(handle)->{
                if (tp.isDone()){
                    if (Arrays.asList(new File(w.getWorldFolder(), "playerdata").listFiles()).contains(new File(new File(w.getWorldFolder(),"playerdata"),p.getUniqueId()+".dat"))){
                        p.loadData();
                    }else {
                        p.getInventory().clear();
                        p.setExp(0);
                        p.setLevel(0);
                        p.saveData();
                        p.loadData();
                    }
                    handle.cancel();
                }
            },0L,5L);
        }else {
            CompletableFuture<Boolean>tp= p.teleportAsync(w.getSpawnLocation());
            Bukkit.getScheduler().runTaskTimer(plugin,(handle)->{
                if (tp.isDone()){
                    if (Arrays.asList(new File(w.getWorldFolder(), "playerdata").listFiles()).contains(new File(new File(w.getWorldFolder(),"playerdata"),p.getUniqueId()+".dat"))){
                        p.loadData();
                    }else {
                        p.getInventory().clear();
                        p.setExp(0);
                        p.setLevel(0);
                        p.saveData();
                        p.loadData();
                    }
                    handle.cancel();
                }
            },5L,0L);
        }
    }
    private static void savePlayerData(Player player, World world) {
        pfyml.set(player.getUniqueId()+".worldPositions."+ world.getName(), player.getLocation());
        save(pf,pfyml);
    }
}