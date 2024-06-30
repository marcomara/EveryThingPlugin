package it.commands;

import it.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.ver;

public class PluginInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(Colors.YELLOW + "Version " + ver +" - Created by il_maranna - twitch.tv/il_maranna");
        return true;
    }
}