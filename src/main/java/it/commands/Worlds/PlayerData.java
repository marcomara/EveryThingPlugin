package it.commands.Worlds;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerData {
    /*private ItemStack[] inventoryContents;
    private int health;
    private int foodLevel;
    private int experienceLevel;
    private float experienceProgress;

    public PlayerData() {
        // Default constructor
    }

    public PlayerData(Player player) {
        this.inventoryContents = player.getInventory().getContents();
        this.health = (int) player.getHealth();
        this.foodLevel = player.getFoodLevel();
        this.experienceLevel = player.getLevel();
        this.experienceProgress = player.getExp();
    }

    public void applyTo(Player player) {
        player.getInventory().setContents(inventoryContents);
        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setLevel(experienceLevel);
        player.setExp(experienceProgress);
    }

    @Override
    public String toString() {
        // Convert player data to a string representation
        // This is a simplified example, you might want to use JSON or another format
        return inventoryContents.toString() + ";" + health + ";" + foodLevel + ";" + experienceLevel + ";" + experienceProgress;
    }

    public static PlayerData fromString(String data) {
        // Convert string representation back to PlayerData
        // This is a simplified example, you might want to use JSON or another format
        String[] parts = data.split(";");
        PlayerData playerData = new PlayerData();
        // Parse inventory contents from parts[0]
        // Parse health from parts[1]
        // Parse foodLevel from parts[2]
        // Parse experienceLevel from parts[3]
        // Parse experienceProgress from parts[4]
        return playerData;
    }*/
}
