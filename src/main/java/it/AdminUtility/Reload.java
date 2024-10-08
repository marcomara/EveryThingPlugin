package it.AdminUtility;

import it.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.plugin.Plugin.lgg;
import static it.plugin.Plugin.plugin;

public class Reload implements CommandExecutor, TabCompleter {
    public static final String[] arguments1 = {"config", "server"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player && sender.hasPermission(command.getPermission()) || sender instanceof ConsoleCommandSender) {
            if (args[0].equals("config")) {
                plugin.reloadConfig();
                if (sender instanceof Player) {
                    lgg.warning("Config Reloaded by " + sender.getName());
                    sender.sendMessage(Colors.GOLD + "Config Reloaded");
                }
                if (sender instanceof ConsoleCommandSender) {
                    lgg.warning("Config Reloaded");
                }
                return true;
            } if (args[0].equals("server")) {
                if (sender instanceof Player) {
                    lgg.warning("Server Reloading by " + sender.getName());
                    sender.sendMessage(Colors.GOLD + "Server Reloading");
                }
                if (sender instanceof ConsoleCommandSender) {
                    lgg.warning("Reloading Server");
                    lgg.warning("If you updated any plugin please consider to restart the server instead of reloading it!!");
                }
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    Bukkit.getServer().reload();
                }, 60L);
                return true;
            } else {
                if (sender instanceof Player) {
                    sender.sendMessage(Colors.RED + "Wrong use of args");
                }
                if (sender instanceof ConsoleCommandSender) {
                    lgg.warning("Wrong use of args");
                }
                return true;
            }
        } else {
            sender.sendMessage(Colors.RED + "Only OPs can use this command");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> toreturn = new ArrayList<>();
        toreturn.addAll(Arrays.stream(arguments1).toList());
        return toreturn;
    }
}