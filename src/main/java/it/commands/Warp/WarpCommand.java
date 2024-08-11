package it.commands.Warp;

import it.utils.Colors;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static it.plugin.Plugin.dataFolder;
import static it.utils.SaveUtility.*;

import java.io.File;
import java.util.*;

public class WarpCommand implements CommandExecutor, TabCompleter {

    public @NotNull List<Map<?, ?>> list;
    private static final HashMap<String, Location> map = new HashMap<>();
    private File file;
    private FileConfiguration fc;

    public WarpCommand() {
        file = new File(dataFolder, "warps.yml");
        create(file);
        fc = createyml(file);
        try {
            list = fc.getMapList("warps");
            if (list.isEmpty()) return;
        } catch (NullPointerException e) {
            list = new ArrayList<>();
            fc.set("warps", list);
            return;
        }
        for (Map data : list) {
            WarpPoint point = WarpPoint.deserialize(data);
            map.put(point.getName(), point.getLocation());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                if (!map.containsKey(args[0])) {
                    sender.sendMessage("The warp point \"" + args[0] + "\" does not exists");
                    return true;
                }
                ((Player) sender).teleport(map.get(args[0]));
                return true;
            }
            if (args.length == 2) {
                if (args[0].equals("add")) {
                    if (!sender.hasPermission("admin.wset")) {
                        sender.sendMessage(Colors.RED + "Only OPs can do that!");
                        return true;
                    }
                    if (map.get(args[1]) != null) {
                        sender.sendMessage("Warp already exists");
                        return true;
                    }
                    map.put(args[1], ((Player) sender).getLocation());
                    list.add(new WarpPoint(map.get(args[1]), args[1]).serialize());
                    saveList(list, fc, "warps", file);
                    return true;
                }
                if (args[0].equals("remove")) {
                    if (!sender.hasPermission("admin.wset")) {
                        sender.sendMessage(Colors.RED + "Only OPs can do that!");
                        return true;
                    }
                    WarpPoint point = new WarpPoint(map.get(args[1]), args[1]);
                    list.remove(point.serialize());
                    map.remove(args[1]);
                    saveList(list, fc, "warps", file);
                    return true;
                }
            } else {
                sender.sendMessage(Colors.RED + "Wrong use of the command");
                return true;
            }
        }else {
            sender.sendMessage("Only players can perform this command");
            return true;
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length==1) return arguments1();
        if(args.length == 2 && args[0].equals("remove")) return new ArrayList<>(map.keySet());
        return new ArrayList<>();
    }
    public static List<String> arguments1() {
        List<String> keyset = new ArrayList<>();
        keyset.add("add");
        keyset.add("remove");
        keyset.addAll(map.keySet());
        return keyset;
    }
}
