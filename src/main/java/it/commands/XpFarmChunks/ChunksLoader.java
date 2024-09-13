package it.commands.XpFarmChunks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.commands.XpFarmChunks.XpFarmChunk.chunks;
import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.save;

public class ChunksLoader {
    public static File f = new File(dataFolder, "LoadedChunks.chunks");
    public static YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);

    public static void ReadXpChunks(){
        List<String> ss = fc.getStringList("XpFarms");
        for (String cs : ss) {
            try {
                String[] csa = cs.split("#");
                Chunk chunk = Bukkit.getWorld(csa[2]).getChunkAt(Integer.parseInt(csa[0]), Integer.parseInt(csa[1]));
                chunks.add(chunk);
            }catch (Exception e){
                lgg.warning("A problem occurred loading a XpFarm chunk");
            }
        }
    }

    public static void SaveXpChunks(List<Chunk> l){
        List<String> temp = new ArrayList<>();
        for (Chunk c : l){
            temp.add(c.getX() + "#" + c.getZ() + "#" + c.getWorld().getName());
        }
        List<String> t2 = fc.getStringList("XpFarms");
        for (String s : temp){
            if (!t2.contains(s)){
                t2.add(s);
            }
        }
        fc.set("XpFarms", t2);
        save(f,fc);
    }

    public static void SaveXpChunks(){
        List<String> temp = new ArrayList<>();
        for(Chunk c : chunks){
            temp.add(c.getX() + "#" + c.getZ() + "#" + c.getWorld().getName());
        }
        fc.set("XpFarms", temp);
        save(f,fc);
    }
    
    public static void UnregisterXpChunks(Chunk c){
        UnregisterXpChunks(c, 1, 0);
    }

    public static void UnregisterXpChunks(Chunk chunk, int radius, int control){
        int x = chunk.getX(), z= chunk.getZ();
        World w = chunk.getWorld();
        List<Chunk> temp = GetNear(w,x,z,radius);
        for (Chunk ch : temp){
            if (chunks.contains(ch)){
                chunks.remove(ch);
                control++;
            }
        }
        if (!(control >= 9)){
            UnregisterXpChunks(chunk, 2, control);
        }
        SaveXpChunks();
    }

    public static List<Chunk> GetNear(World ChunkWorld, int ChunkX, int ChunkZ, int radius){
        List<Chunk> temp = new ArrayList<>();
        temp.add(ChunkWorld.getChunkAt(ChunkX+radius,ChunkZ-radius));
        temp.add(ChunkWorld.getChunkAt(ChunkX+radius,ChunkZ));
        temp.add(ChunkWorld.getChunkAt(ChunkX+radius,ChunkZ+radius));
        temp.add(ChunkWorld.getChunkAt(ChunkX,ChunkZ-radius));
        temp.add(ChunkWorld.getChunkAt(ChunkX,ChunkZ));
        temp.add(ChunkWorld.getChunkAt(ChunkX,ChunkZ+radius));
        temp.add(ChunkWorld.getChunkAt(ChunkX-radius,ChunkZ-radius));
        temp.add(ChunkWorld.getChunkAt(ChunkX-radius,ChunkZ));
        temp.add(ChunkWorld.getChunkAt(ChunkX-radius,ChunkZ+radius));
        return temp;
    }
}
