package it.commands.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoordinatesCalculator implements CommandExecutor, TabCompleter {
    public static final String[] arguments1 = {"toOverWorld","toNether"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 1){
            sender.sendMessage("Use /coords [toNether|toOverworld]");
        }
        if(args.length==1){
            float x = ((Player) sender).getLocation().getBlockX();
            float z = ((Player) sender).getLocation().getBlockZ();
            if(args[0].equals("toOverworld")){
                x=x*8;
                z=z*8;
                sender.sendMessage("X: " + x + " Z: " + z);
            }
            if(args[0].equals("toNether")){
                x=x/8;
                z=z/8;
                sender.sendMessage("X: " + x + " Z: " + z);
            }
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>(Arrays.asList(arguments1));
    }
}
