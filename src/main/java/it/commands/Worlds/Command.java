package it.commands.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import static it.plugin.Plugin.dataFolder;
import static it.plugin.Plugin.worlds;
import static it.utils.SaveUtility.save;

public class Command /*implements CommandExecutor*/ {
    /*@Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if (args.length==0) return false;
        if(args.length==1){
            World w = Bukkit.getWorld(args[0]);
            if(w==null){
                WorldCreator creator = new WorldCreator(args[0]);
                w = Bukkit.createWorld(creator);
                List<String> list = worlds.getStringList("worlds");
                list.add(args[0]);
                worlds.set("worlds",list);
                save(new File(dataFolder, "worlds.yml"), worlds);
            }
            WorldHandler.onWorldChange(p, p.getWorld(), w);
            Location l = w.getSpawnLocation();
            p.teleport(l);
        }
        return true;

    }*/
}
