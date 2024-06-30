package it.commands.warp;

import it.utils.Colors;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static it.plugin.Plugin.ccs;

import static it.plugin.Plugin.dataFolder;
import static it.utils.SaveUtility.*;

import java.io.File;
import java.util.*;

public class WarpCommand implements CommandExecutor {
    public @NotNull List<Map<?, ?>> list;
    private static final HashMap<String, Location> map = new HashMap<>();
    private final File file;
    private final FileConfiguration fc;

    public WarpCommand() {
        file = new File(dataFolder, "warps.yml");
        create(file);
        fc = creatyml(file);
        try {
            list = fc.getMapList("warps");
            if (list.isEmpty()) return;
        } catch (NullPointerException e) {
            list = new ArrayList<>();
            fc.set("warps", list);
            return;
        }
        ccs.sendMessage(Colors.GREEN + "[WarpConstructor]" + Colors.WHITE + "Recording " + list.size() + " warp location(s)");
        for (Map data : list) {
            WarpPoint point = WarpPoint.deserialize(data);
            map.put(point.getName(), point.getLocation());
        }
        ccs.sendMessage(Colors.GREEN + "[WarpContructor]" + Colors.WHITE + "Done");
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
                    if (!sender.isOp()) {
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
                    if (!sender.isOp()) {
                        sender.sendMessage(Colors.RED + "Only OPs can do that!");
                        return true;
                    }
                    WarpPoint point = new WarpPoint(map.get(args[1]), args[1]);
                    if (map.get(args[1]) != null && list.contains(point)) {
                        list.remove(point);
                        map.remove(args[1]);
                        fc.set("warp", list);
                        save(file, fc);
                        return true;
                    }
                }
            } else {
                sender.sendMessage(Colors.RED + "Wrong use of the command");
                return true;
            }
        }
            sender.sendMessage("Only players can perform this command");
            return true;
    }

    public static Set<String> TabComplete() {
        return map.keySet();
    }
}
