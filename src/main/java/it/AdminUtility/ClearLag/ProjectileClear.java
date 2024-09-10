package it.AdminUtility.ClearLag;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.intMap;

public class ProjectileClear implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(!commandSender.hasPermission(command.getPermission())){
            return false;
        }
        int i=0;
        List<World> lw = Bukkit.getWorlds();
        List<Entity> srm = new ArrayList<>();
        for(World w : lw){
            List<Entity> wrm = new ArrayList<>();
            Chunk[] ac = w.getLoadedChunks();
            for(Chunk c : ac){
                Entity[] ae = c.getEntities();
                List<Entity> rm= new ArrayList<>();
                for(Entity e : ae){
                    if (e.getType() == EntityType.SNOWBALL || e.getType() == EntityType.ARROW) rm.add(e);
                }
                if(intMap.get("AdminUtils.Values.MaxProjectileChunk")==-1){
                    wrm.addAll(rm);
                    continue;
                }
                while(rm.size()>intMap.get("AdminUtils.Values.MaxProjectileChunk")){
                    Bukkit.getEntity(rm.getLast().getUniqueId()).remove();
                    i++;
                    rm.removeLast();
                }
                wrm.addAll(rm);
            }
            if(intMap.get("AdminUtils.Values.MaxProjectileWorld")==-1){
                srm.addAll(wrm);
                continue;
            }
            while(wrm.size()>intMap.get("AdminUtils.Values.MaxProjectileWorld")){
                Bukkit.getEntity(wrm.getLast().getUniqueId()).remove();
                i++;
                wrm.removeLast();
            }
            srm.addAll(wrm);
        }
        if(intMap.get("AdminUtils.Values.MaxProjectileServer")==-1){
            commandSender.sendMessage(Component.text("Removed " + i + " projectile/s").color(NamedTextColor.WHITE));
            return true;
        }
        while(srm.size()>intMap.get("AdminUtils.Values.MaxProjectileServer")){
            Bukkit.getEntity(srm.getLast().getUniqueId()).remove();
            i++;
            srm.removeLast();
        }
        commandSender.sendMessage(Component.text("Removed " + i + " projectile/s").color(NamedTextColor.WHITE));
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}

