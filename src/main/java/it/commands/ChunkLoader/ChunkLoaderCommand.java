package it.commands.ChunkLoader;

import it.utils.SaveUtility;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import it.utils.Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.plugin.Plugin.*;

public class ChunkLoaderCommand implements CommandExecutor {
    public static final String[] arguments1 = {"load", "unload", "list"};
    public static final String[] arguments2 = {"world", "nether", "end"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(Colors.RED + "You don't have the permission to do that");
            return true;
        }
        String method = args[0];
        if (method.equals("load")) {
            if (args.length == 1) {
                Player p = (Player) sender;
                loadChunk(null, null, p.getLocation().getChunk(), sender);
                return true;
            }
            if (args.length == 4) {
                World w = Bukkit.getWorld("world");
                if (args[1].equals("world")) {
                    w = Bukkit.getWorld("world");
                }
                if (args[1].equals("nether")) {
                    w = Bukkit.getWorld("world_nether");
                }
                if (args[1].equals("end")) {
                    w = Bukkit.getWorld("world_the_end");
                }
                loadChunk(w, args, null, sender);
                return true;
            }
        }
        if (method.equals("unload")) {
            if (args.length == 1) {
                Player p = (Player) sender;
                unloadChunk(null, null, p.getLocation().getChunk(), sender);
                return true;
            }
            if (args.length == 2 && args[1].equals("all")) {
                List<String> list = LoadedChunks;
                for (String schunk : list) {
                    String[] chunkS = schunk.split("#");
                    String x = chunkS[0];
                    String z = chunkS[1];
                    World world = Bukkit.getWorld(chunkS[2]);
                    ChunkLoaderHandler.UnloadChunk(world.getChunkAt(Integer.parseInt(x), Integer.parseInt(z)));
                }
                list.clear();
                SaveUtility.saveList(list, CFC, "LoadedChunks", CFile);
                sender.sendMessage("All chunks have been unloaded");
                return true;
            }
            if (args.length == 4) {
                World w = Bukkit.getWorld("world");
                if (args[1].equals("world")) {
                    w = Bukkit.getWorld("world");
                }
                if (args[1].equals("nether")) {
                    w = Bukkit.getWorld("world_nether");
                }
                if (args[1].equals("end")) {
                    w = Bukkit.getWorld("world_the_end");
                }
                unloadChunk(w, args, null, sender);
                return true;
            }
        }
        if (method.equals("list")) {
            List<String> list = LoadedChunks;
            if (list.isEmpty()) {
                sender.sendMessage("There are no chunks loaded");
                return true;
            }
            sender.sendMessage("Loaded chunks:");
            for (String schunk : list) {
                String[] chunkS = schunk.split("#");
                String x = chunkS[0];
                String z = chunkS[1];
                String world = chunkS[2];
                sender.sendMessage(x + " " + z + " in " + world);
            }
            return true;
        }
        return false;
    }

    public static class ChunkLoaderTab implements TabCompleter {

        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
            List<String> toreturn = new ArrayList<>();
            if (args.length == 1) toreturn.addAll(Arrays.stream(arguments1).toList());
            if (args.length == 2) toreturn.addAll(Arrays.asList(arguments2));
            return toreturn;
        }
    }
    private static void loadChunk(@Nullable World w, @Nullable String[] args, @Nullable Chunk chunk, CommandSender sender){
        if(w!=null && args!=null) {
            chunk = w.getChunkAt(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        String x = String.valueOf(chunk.getX());
        String z = String.valueOf(chunk.getZ());
        String wn = chunk.getWorld().getName();
        String chunkS = x + "#" + z + "#" + wn;
        if(LoadedChunks.contains(chunkS)){
            sender.sendMessage("Chunk " + x + " " + z + " in " + wn + " is already loaded");
            return;
        }
        LoadedChunks.add(chunkS);
        ChunkLoaderHandler.LoadChunk(chunk);
        SaveUtility.saveList(LoadedChunks, CFC, "LoadedChunks", CFile);
        sender.sendMessage("Chunk " + x + " " + z + " in " + wn + " is now loaded");
    }
    private static void unloadChunk(@Nullable World w, @Nullable String[] args, @Nullable Chunk chunk,CommandSender sender){
        if(w!=null && args!=null) {
            chunk = w.getChunkAt(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        String x = String.valueOf(chunk.getX());
        String z = String.valueOf(chunk.getZ());
        String wn = chunk.getWorld().getName();
        String chunkS = x + "#" + z + "#" + wn;
        if(!LoadedChunks.contains(chunkS)){
            sender.sendMessage("Chunk " + x + " " + z + " in " + wn + " isn't loaded");
            return;
        }
        LoadedChunks.remove(chunkS);
        ChunkLoaderHandler.UnloadChunk(chunk);
        SaveUtility.saveList(LoadedChunks, CFC, "LoadedChunks", CFile);
        sender.sendMessage("Chunk " + x + " " + z + " in " + wn + " is no more loaded");
    }
}
