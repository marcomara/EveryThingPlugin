package it.commands.tpa;

import it.commands.tpa.handlers.*;
import it.utils.TabCompleteUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.plugin.Plugin.*;


public class Command implements CommandExecutor,TabCompleter {
    public static final String[] arguments1 = {"getOtherOnlinePlayers", "accept", "deny"};
    public static final String[] getArguments2 = {"getTpaSpecificOnlinePlayers"};
    static HashMap<String, List<Data>> table = tpatable;
    int timer = tpatimer;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String string, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender){
            lgg.warning("Only players can perform this command");
            return true;
        }
        if(args.length<1){
            sender.sendMessage("You need to specify an online player");
            return true;
        }

        if(args.length==1){
            if(sender.getName().equals(args[0])){
                sender.sendMessage("You can't tpa to yourself");
                return true;
            }
            new request(table,sender,args[0]).request();
            return true;
        }
        if(args.length==2){
            if(args[0].equals("allow") || args[0].equals("deny")){
                boolean condition= args[0].equals("allow");
                new response(table,sender, args[1], condition , timer).response();
                return true;
            }
            sender.sendMessage("Wrong use of the command syntax");
            return false;
        }
        return false;
    }

    @Override

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> toreturn = new ArrayList<>();
        if(args.length==1) {
            toreturn.add("accept");
            toreturn.add("deny");
            toreturn.addAll(TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender));
        }
        if (args.length==2){
            return getTpaSpecificOnlinePlayers(commandSender);
        }
        return toreturn;
    }

    public static List<String> getTpaSpecificOnlinePlayers(CommandSender sender){
        List<String> applicants = new ArrayList<>();
        if(!table.isEmpty()) {
            for (Data data : table.get(sender.getName())) {
                applicants.add(data.sender);
            }
        }
        return applicants;
    }
}
