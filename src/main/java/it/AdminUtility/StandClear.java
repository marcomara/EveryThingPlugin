package it.AdminUtility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.intMap;

public class StandClear implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(!commandSender.isOp()){
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
                    if (e.getType()== EntityType.ARMOR_STAND) rm.add(e);
                }
                if(intMap.get("AdminUtils.Values.MaxStandChunk")==-1){
                    wrm.addAll(rm);
                    continue;
                }
                while(rm.size()>intMap.get("AdminUtils.Values.MaxStandChunk")){
                    Bukkit.getEntity(rm.get(rm.size()-1).getUniqueId()).remove();
                    i++;
                    rm.remove(rm.size()-1);
                }
                wrm.addAll(rm);
            }
            if(intMap.get("AdminUtils.Values.MaxStandWorld")==-1){
                srm.addAll(wrm);
                continue;
            }
            while(wrm.size()>intMap.get("AdminUtils.Values.MaxStandWorld")){
                Bukkit.getEntity(wrm.get(wrm.size()-1).getUniqueId()).remove();
                i++;
                wrm.remove(wrm.size()-1);
            }
            srm.addAll(wrm);
        }
        if(intMap.get("AdminUtils.Values.MaxStandServer")==-1){
            commandSender.sendMessage(Component.text("Removed " + i + " armor stand/s").color(NamedTextColor.WHITE));
            return true;
        }
        while(srm.size()>intMap.get("AdminUtils.Values.MaxStandServer")){
            Bukkit.getEntity(srm.get(srm.size()-1).getUniqueId()).remove();
            i++;
            srm.remove(srm.size()-1);
        }
        commandSender.sendMessage(Component.text("Removed " + i + " armor stand/s").color(NamedTextColor.WHITE));
        return true;
    }
}
