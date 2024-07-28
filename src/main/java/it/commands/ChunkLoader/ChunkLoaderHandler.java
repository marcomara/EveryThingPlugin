package it.commands.ChunkLoader;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.List;
import java.util.logging.Level;

import static it.plugin.Plugin.LoadedChunks;
import static it.plugin.Plugin.lgg;

public class ChunkLoaderHandler{
    public static void LoadChunksFromList(){
        try{
            List<String> chunks = LoadedChunks;
            for (String cs : chunks) {
                String[] csa = cs.split("#");
                String wname = csa[2];
                Chunk chunk = Bukkit.getWorld(wname).getChunkAt(Integer.parseInt(csa[0]), Integer.parseInt(csa[1]));
                LoadChunk(chunk);
            }
        }catch (NullPointerException e){
            lgg.log(Level.INFO, "There are no chunks to load");
        }
    }
    public static void LoadChunk(Chunk chunk){
        World w = chunk.getWorld();
        w.loadChunk(chunk);
        w.setChunkForceLoaded(chunk.getX(), chunk.getZ(), true);
    }
    public static void UnloadChunk(Chunk chunk){
        World w = chunk.getWorld();
        w.unloadChunk(chunk);
        w.setChunkForceLoaded(chunk.getX(), chunk.getZ(), false);
    }
}
