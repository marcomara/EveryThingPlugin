package it.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoordinatesCalculator implements CommandExecutor {

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
}
