package it.Roles;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command implements CommandExecutor {


    public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    public static List<Integer> poslist = new ArrayList<>();
    public static Map<String, Team> teams = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length==0){
            sender.sendMessage("You are a " + scoreboard.getPlayerTeam((Player) sender).prefix());
            return true;
        }
        if (args.length==3&&args[0].equals("set")){     //usage /role set player team
            if (!sender.hasPermission("roles.set")){
                sender.sendMessage("You don't have the permission to do that");
                return true;
            }
            if (!teams.containsKey(args[2])){
                sender.sendMessage(args[2] + " team does not exist");
                return true;
            }
            Team t = teams.get(args[2]);
            Player p = Bukkit.getPlayer(args[1]);
            t.addPlayer(p);
            p.sendMessage("You are now a " + t.prefix());
            sender.sendMessage(p.getName() + " role updated");
            return true;
        }
        if (args.length==3&&args[0].equals("remove")){
            if (!sender.hasPermission("roles.remove")){
                sender.sendMessage("You don't have the permission to do that");
                return true;
            }
            if (!teams.containsKey(args[1])){
                sender.sendMessage(args[1] + " team does not exist");
                return true;
            }
            if (!teams.containsKey(args[2])){
                sender.sendMessage("Replacement team \"" + args[2] +"\" does not exist");
                return true;
            }
            teams.get(args[1]).unregister();
            teams.remove(args[1]);
        }
        //usage /role create name displayname color position
        //usage /role modify rname displayname|color|position newvalue
        //usage /role remove name replacement
        return true;
    }






    /*@Override
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
    }*/
}
