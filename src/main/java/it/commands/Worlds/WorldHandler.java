package it.commands.Worlds;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldHandler {
    /*private static Plugin plugin;
    private static Map<UUID, Map<String, CompoundBinaryTag>> playerDataStorage = new HashMap<>();

    public WorldHandler(Plugin plugin) {
        this.plugin = plugin;
    }
    public static void onWorldChange(Player p, World from, World to) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        World fromWorld = event.getFrom();
        World toWorld = player.getWorld();

        // Save current player data
        savePlayerData(player, fromWorld);

        // Load new player data
        loadPlayerData(player, toWorld);

        // Teleport player to the spawn point of the new world
        Location spawnLocation = toWorld.getSpawnLocation();
        Bukkit.getScheduler().runTask(plugin, () -> player.teleport(spawnLocation));
    }

    private static void savePlayerData(Player player, World world) {
        UUID playerId = player.getUniqueId();
        File playerDataFile = getPlayerDataFile(player, world);

        try {
            CompoundBinaryTag playerData = CompoundBinaryTag.create();

            // Serialize player data to NBT
            playerData.put("Inventory", NbtTagReader.readItemStacks(player.getInventory().getContents()));
            playerData.put("Health", player.getHealth());
            playerData.put("FoodLevel", player.getFoodLevel());
            playerData.put("Experience", player.getExp());
            playerData.put("Level", player.getLevel());

            // Store player data in memory
            playerDataStorage.putIfAbsent(playerId, new HashMap<>());
            playerDataStorage.get(playerId).put(world.getName(), playerData);

            // Save NBT data to file
            try (FileOutputStream fos = new FileOutputStream(playerDataFile)) {
                NbtBinaryTagWriter.write(playerData, fos);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player data for " + player.getName() + " in world " + world.getName());
            e.printStackTrace();
        }
    }

    private static void loadPlayerData(Player player, World world) {
        UUID playerId = player.getUniqueId();
        File playerDataFile = getPlayerDataFile(player, world);

        try {
            CompoundTag playerData;

            if (playerDataStorage.containsKey(playerId) && playerDataStorage.get(playerId).containsKey(world.getName())) {
                playerData = playerDataStorage.get(playerId).get(world.getName());
            } else {
                try (FileInputStream fis = new FileInputStream(playerDataFile)) {
                    playerData = NbtTagReader.read(fis);
                }
            }

            // Deserialize player data from NBT
            player.getInventory().setContents(NbtTagReader.readItemStacks(playerData.getList("Inventory")));
            player.setHealth(playerData.getDouble("Health"));
            player.setFoodLevel(playerData.getInt("FoodLevel"));
            player.setExp(playerData.getFloat("Experience"));
            player.setLevel(playerData.getInt("Level"));
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load player data for " + player.getName() + " in world " + world.getName());
            e.printStackTrace();
        }
    }

    private File getPlayerDataFile(Player player, World world) {
        return new File(world.getWorldFolder(), "playerdata/" + player.getUniqueId() + ".dat");
    }*/

}