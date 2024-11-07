package it.commands.Worlds;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static it.plugin.Plugin.*;

public class PlayerData {
    File folder = new File(new File(dataFolder.getParentFile().getParentFile(), "world"), "playerdata");
    File inuse;
    File wsfolder = new File(dataFolder, "WorldsPlayerData");
    Player p;
    Map<World, File> pfiles= new HashMap<>();
    private PlayerData(Player p){
        for (World w: Bukkit.getWorlds()){
            if (Arrays.asList(wsfolder.listFiles()).contains(w.getName())){
                File wfolder = new File(wsfolder, w.getName());
                if (Arrays.asList(wfolder.listFiles()).contains(new File(wfolder, p.getUniqueId()+".dat"))){
                    pfiles.put(w, new File(wfolder,p.getUniqueId()+".dat"));
                }else try {
                    new File(wfolder, p.getUniqueId() + ".dat").createNewFile();
                }catch (Exception e){
                    lgg.warning("Unable to create player file " + wfolder.getAbsolutePath() + "/"+p.getUniqueId()+".dat");
                    p.kick(Component.text("Unable to create new player data file, try again").color(NamedTextColor.RED));
                    e.fillInStackTrace();
                }
            }else try{
                File pf = new File(new File(wsfolder, w.getName()), p.getUniqueId()+"dat");
                pf.getParentFile().mkdirs();
                pf.createNewFile();
            }catch (Exception e){
                lgg.warning("Unable to create player file " + pf.getParentFile().getAbsolutePath() + "/"+p.getUniqueId()+".dat");
                lgg.warning("Unable to create world player file directory " + pf.getParentFile().getAbsolutePath());
                p.kick(Component.text("Unable to create new player data file, try again").color(NamedTextColor.RED));
                e.fillInStackTrace();
            }
        }
        inuse= new File(folder, p.getUniqueId()+".dat");
    }
    public PlayerData getPlayerData(Player p){
        return new PlayerData(p);
    }
    public boolean copyPlayerData(Player p, World w){
        File f = new File(folder, p.getUniqueId() + ".dat");
        File cop = new File(dataFolder, w.getName());
        File tcop = new File(cop, p.getUniqueId() + ".dat");
        try {
            Files.move(f.toPath(), tcop.toPath(), StandardCopyOption.ATOMIC_MOVE);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void setPlayerData(World w) throws IOException {
        Files.delete(new File(folder, p.getUniqueId()+".dat").toPath());
        Files.copy(pfiles.get(w).toPath(), inuse.toPath(), StandardCopyOption.ATOMIC_MOVE);
        p.loadData();
    }
}
