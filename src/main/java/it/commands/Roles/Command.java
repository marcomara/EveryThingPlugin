package it.commands.Roles;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.roles;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p;
        if ((p = Bukkit.getPlayer(args[1])).isOnline()&&args[0].equals("set")){
            p.setScoreboard(roles);
            roles.getTeam("Owner").addEntry(p.getName());
        }
        return true;
    }
}
