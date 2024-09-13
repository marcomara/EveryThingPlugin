package it.commands.Utils;

import it.plugin.Plugin;
import it.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.commands;
import static it.plugin.Plugin.plugin;

public class CommandList implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        sender.sendMessage("The commands are the following:");
        for(String name : commands){
            if(sender.hasPermission(plugin.getCommand(name).getPermission())){
                String description;
                try{
                    description = plugin.getCommand(name).getDescription();
                }catch (NullPointerException e){
                    sender.sendMessage( Colors.GREEN + "/" + name);
                    continue;
                }
                sender.sendMessage(Colors.GREEN + "/" + name + Colors.WHITE + ": "+description);
            }
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}
