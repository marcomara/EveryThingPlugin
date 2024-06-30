package it.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SendCoordinates implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if(args.length==1) {
            Player t = Bukkit.getPlayer(args[0]);
            t.sendMessage("X:"+p.getLocation().getBlockX() +" Y:"+ p.getLocation().getBlockY() +" Z:"+ p.getLocation().getBlockZ());
        }else{
            p.sendMessage("You need to specify a player to send the coordinates");
        }
        return true;
    }
}
