package it.commands.XpFarmChunks;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.commands.XpFarmChunks.ChunksLoader.*;

public class XpFarmChunk implements CommandExecutor {

    public static List<Chunk> chunks = new ArrayList<>();


    public XpFarmChunk(){
        ReadXpChunks();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!sender.hasPermission("commands.xpfarmchunk")){
            sender.sendMessage("You don't have the permission to do that!");
            return true;
        }
        if (args.length==0){
            Player p = (Player) sender;
            List<Chunk> temp = GetNear(p.getWorld(), p.getChunk().getX(),p.getChunk().getZ(),1);
            for (Chunk c : temp){
                if (!chunks.contains(c)){
                    chunks.add(c);
                }
            }
            ChunksLoader.SaveXpChunks(temp);
            sender.sendMessage("XpFarmChunks saved!");
            return true;
        }
        if (args.length==1&&args[0].equals("unregister")){
            UnregisterXpChunks(((Player)sender).getChunk());
            return true;
        }
        if (args.length==1&&args[0].equals("list")){
            sender.sendMessage("XpFarmChunks:");
            for (Chunk c : chunks){
                sender.sendMessage("Chunk " + c.getX() + ", " + c.getZ() + " in " + c.getWorld().getName());
            }
            return true;
        }
        sender.sendMessage("Wrong use of the command");
        return true;
    }

    public static class XpFarmChunkTabCompleter implements TabCompleter{
        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
            if (args.length==1) {
                return List.of("unregister" , "list");
            }
            return new ArrayList<>();
        }
    }
}
