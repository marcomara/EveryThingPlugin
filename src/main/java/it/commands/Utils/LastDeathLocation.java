package it.commands.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LastDeathLocation implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        p.sendMessage("X: " + p.getLastDeathLocation().getBlockX() + " Y: " + p.getLastDeathLocation().getBlockY() + " Z:" + p.getLastDeathLocation().getBlockZ());
        return true;
    }
}
