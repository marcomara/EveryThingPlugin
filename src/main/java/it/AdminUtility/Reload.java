package it.AdminUtility;
import it.plugin.Plugin;
import it.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.lgg;

public class Reload implements CommandExecutor {
    private final Plugin plugin;
    public Reload(Plugin plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player && sender.isOp() ||sender instanceof ConsoleCommandSender){
        if(args[0].equals("config")){
            plugin.reloadConfig();
            if(sender instanceof Player){
            lgg.warning("Config Reloaded by " + sender.getName());
            sender.sendMessage(Colors.GOLD + "Config Reloaded");}
            if(sender instanceof ConsoleCommandSender){lgg.warning("Config Reloaded");}
            return true;
        }
        if(args[0].equals("server")){
            plugin.getServer().reload();
            if(sender instanceof Player){
                lgg.warning("Server Reloaded by " + sender.getName());
                sender.sendMessage(Colors.GOLD + "Server Reloaded");}
            if(sender instanceof ConsoleCommandSender){lgg.warning("Server Reloaded");}
            return true;
        }
        if(args[0].equals("all")){
            plugin.reloadConfig();
            plugin.getServer().reload();
            if(sender instanceof Player){
                lgg.warning("Everything Reloaded by " + sender.getName());
                sender.sendMessage(Colors.GOLD + "Everything Reloaded");}
            if(sender instanceof ConsoleCommandSender){
                lgg.warning("Everything Reloaded");}
            return true;
        }
        else{
            if(sender instanceof Player){
                sender.sendMessage(Colors.RED + "Wrong use of args");}
            if(sender instanceof ConsoleCommandSender){lgg.warning("Wrong use of args");}
            return true;
        }
        }
        else{
            sender.sendMessage(Colors.RED + "Only OPs can use this command");
            return true;
        }
    }
}