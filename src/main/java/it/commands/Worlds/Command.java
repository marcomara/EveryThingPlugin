package it.commands.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if (args.length==0) return false;
        if(args.length==1){
            PlayerWorldData.savePlayerWorldData(p, p.getWorld().getUID());
        }
        return true;
        /*World w = Bukkit.getWorld(args[0]);
        if(w==null){
            WorldCreator creator = new WorldCreator(args[0]);
            Bukkit.createWorld(creator);
        }
        Location l = w.getSpawnLocation();
        p.teleport(l);
        return false;*/
    }
}
