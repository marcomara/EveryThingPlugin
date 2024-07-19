package it.commands.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static it.plugin.Plugin.dataFolder;
import static it.plugin.Plugin.lgg;
import static it.utils.SaveUtility.*;

public class PlayerWorldData implements ConfigurationSerializable {
    Location location;
    Inventory inventory;
    Inventory enderchest;
    Location lastDeathLocation;
    Map<String, Integer> warden_spawn_tracker;
    Location spawn;
    float expP;
    int expL;
    static File folder = new File(dataFolder, "PlayersWorldsData");

    public PlayerWorldData(Player player) {
        this.location = player.getLocation();
        this.inventory = player.getInventory();
        this.enderchest = player.getEnderChest();
        this.lastDeathLocation = player.getLastDeathLocation();
        this.warden_spawn_tracker = Map.of("cooldown_ticks", player.getWardenWarningCooldown(),
                "ticks_since_last_warning", player.getWardenTimeSinceLastWarning(),
                "warning_level", player.getWardenWarningLevel());
        this.spawn = player.getRespawnLocation();
        this.expP = player.getExp();
        this.expL = player.getLevel();
    }

    public static PlayerWorldData getPlayerWorldData(Player player, UUID world) {
        File pwdf = new File(folder, player.getUniqueId().toString());
        if(!pwdf.exists()){
            try{
                pwdf.createNewFile();
            }catch (Exception e){
                lgg.log(Level.WARNING, "Couldn't load " + player.getName() + " WorldsFile");
                return null;
            }
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(pwdf);
        Object obj = yml.get(world.toString());
        if (obj instanceof PlayerWorldData) {
            return (PlayerWorldData) obj;
        } else return null;
    }

    public static void savePlayerWorldData(Player player, UUID world) {
        /*File pwdf = new File(folder, player.getUniqueId() + ".worlds");
        if(!pwdf.exists()){
            try{
                pwdf.createNewFile();
            }catch (Exception e){
                lgg.log(Level.WARNING, "Couldn't load " + player.getName() + " WorldsFile");
                return;
            }
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(pwdf);
        yml.set(world.toString(), new PlayerWorldData(player).serialize());
        save(pwdf, yml);
    }
    public static void checkForFolder() {
        if(!folder.exists()){
            folder.mkdir();
        }*/
       /* File worldFolder = Bukkit.getWorld("world").getWorldFolder();
        File playerdata = new File(worldFolder, "playerdata");
        File pdata = new File(playerdata, player.getUniqueId() + ".dat");
        Path path = Paths.get(pdata.getAbsolutePath());
        File target = new File(folder, player.getUniqueId() + "." + world + ".dat");
        Path tpath = Paths.get(target.getAbsolutePath());
        try{
            Files.copy(path, tpath, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }


    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("Location", location,
                "Inventory", inventory,
                "EnderChest", enderchest,
                "ldl", lastDeathLocation,
                "warden_stuff", warden_spawn_tracker,
                "spawn",spawn,
                "exp", expP,
                "Level", expL);
    }
}