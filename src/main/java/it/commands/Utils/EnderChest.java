package it.commands.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderChest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if(args.length==0){
            p.openInventory(p.getEnderChest());
            return true;
        }
        if(args.length==1&&commandSender.isOp()){
            if(Bukkit.getPlayer(args[0]).isOnline()) {
                p.openInventory(Bukkit.getPlayer(args[0]).getEnderChest()).setTitle(args[0] + "'s enderchest");
            }else if(Bukkit.getOfflinePlayerIfCached(args[0]).hasPlayedBefore()){
                p.openInventory(Bukkit.getOfflinePlayer(args[0]).getPlayer().getEnderChest());
            }else p.sendMessage("Player not found");
            return true;
        }else {p.sendMessage("Only op can do that");return true;}
    }
}