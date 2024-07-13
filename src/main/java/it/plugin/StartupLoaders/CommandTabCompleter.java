package it.plugin.StartupLoaders;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    private final static List<String> ARGUMENTS = new ArrayList<>();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> collection = new ArrayList<>();
        try {
            String[] da = command.getUsage().split("\s");
            String[] fa = da[args.length].split("\\|");
            ARGUMENTS.addAll(List.of(fa));
        }catch (Exception e){
            return null;
        }
        checkForExeption(sender, command.getName(),args);
        StringUtil.copyPartialMatches(args[args.length - 1], ARGUMENTS, collection);
        ARGUMENTS.clear();
        try {
            return collection;
        }catch (Exception e){
            return null;
        }
    }
    private static void getPlayers(CommandSender sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getUniqueId().equals(((Player)sender).getUniqueId())) {
                ARGUMENTS.add(p.getName());
            }
        }
    }
    private static void checkForExeption(CommandSender sender, String command, String[] args) {
        if (command.equals("balance")) {
            if (!sender.isOp()) {
                ARGUMENTS.removeAll(List.of(new String[]{"set", "add", "sub"}));
            }
        }
        if (command.equals("warp")) {
            if (!sender.isOp()) {
                ARGUMENTS.removeAll(List.of(new String[]{"add", "remove"}));
            }
        }
        if (ARGUMENTS.contains("player")) {
            ARGUMENTS.remove("player");
            getPlayers(sender);
        }
        if (ARGUMENTS.contains("LocationName")) {
            ARGUMENTS.remove("LocationName");
            ARGUMENTS.addAll(it.commands.warp.WarpCommand.TabComplete());
        }
        if (ARGUMENTS.contains("ApplicantPlayer")) {
            ARGUMENTS.remove("ApplicantPlayer");
            ARGUMENTS.addAll(it.commands.tpa.Command.TabComplete(sender));
        }
        if(ARGUMENTS.contains("AllPlayers")){
            ARGUMENTS.remove("AllPlayers");
            for(Player p : Bukkit.getOnlinePlayers()){
                ARGUMENTS.add(p.getName());
            }
        }
        ARGUMENTS.remove("CustomName");
    }
}
