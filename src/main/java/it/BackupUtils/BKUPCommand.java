package it.BackupUtils;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.bkfolder;

public class BKUPCommand implements CommandExecutor {
    Plugin plugin;

    public BKUPCommand(Plugin plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender.hasPermission(command.getPermission())) {
            if (args[0].equals("all")) {
                new AllWorldsRun(bkfolder).runTaskAsynchronously(plugin);
            } else {
                if(getWorldsNames().contains(args[0])){
                    new SingleWorldRun(args[0], bkfolder).runTaskAsynchronously(plugin);
                }else commandSender.sendMessage(args[0] + " is not a world");
            }
            return true;
        }
        return false;
    }

    public static class BKUPTab implements TabCompleter {
        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
            if (args.length == 1) {
                return getWorldsNames();
            }
            return new ArrayList<>();
        }
    }

    private static List<String> getWorldsNames() {
        List<String> str = new ArrayList<>();
        for (World w : Bukkit.getWorlds()) {
            str.add(w.getName());
        }
        str.add("all");
        return str;
    }
}
