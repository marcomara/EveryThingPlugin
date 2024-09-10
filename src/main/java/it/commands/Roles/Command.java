package it.commands.Roles;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static it.plugin.Plugin.*;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0 && sender instanceof Player){
            Player p = (Player) sender;
            String ss = roles.getPlayerTeam(p).getName();
            for (Role r : rolesl){
                if (r.getName().equals(ss)){
                    sender.sendMessage("You are in the " + r.getPrefix() + " team");
                }
            }
            return true;
        }
        if(sender.hasPermission("admin.roles")){
            if (args[0].equals("add")){
                if (args.length != 4){
                    sender.sendMessage("Usage: /roles add Name Prefix PrefixColor(Hex)");
                    return true;
                }
                for(Role r : rolesl){
                    if (r.getName() == args[1]){
                        sender.sendMessage("Role already exists");
                        return true;
                    }
                }
                if (args[3].length() != 7 && !args[3].startsWith("#")){
                    sender.sendMessage("Please use hex code colors");
                    return true;
                }
                Role r = new Role(args[1], args[2], args[3]);
                rolesl.add(r);
                roles.registerNewTeam(r.getName()).prefix(r.getDisplayPrefix());
                return true;
            }
            if (args[0].equals("remove")){
                for (Team t : roles.getTeams()){
                    if (t.getName().equals(args[1])){
                        for (Player p : Bukkit.getOnlinePlayers()){
                            if(roles.getPlayerTeam(p) == t){
                                t.removePlayer(p);
                                roles.getTeam("default").addPlayer(p);
                            }
                        }
                        roles.getTeams().remove(t);
                        sender.sendMessage("Team " + args[1] + " removed");
                    }
                }
                return true;
            }
            
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
