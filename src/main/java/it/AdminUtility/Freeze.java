package it.AdminUtility;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Freeze implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender.hasPermission("admin.freeze")){
            Bukkit.getServerTickManager().setFrozen(!Bukkit.getServerTickManager().isFrozen());
        }
        return true;
    }
}
