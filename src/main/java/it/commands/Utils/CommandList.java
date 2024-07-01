package it.commands.Utils;

import it.plugin.Plugin;
import it.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandList implements CommandExecutor {
    private final Plugin plugin;
    public CommandList(Plugin plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        sender.sendMessage("The commands are the following:");
        for(String name : plugin.commands){
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
}
