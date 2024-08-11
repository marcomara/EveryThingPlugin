package it.commands.Nick;

import it.utils.TabCompleteUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.utils.SaveUtility;
import it.utils.Colors;
import org.jetbrains.annotations.Nullable;

import static it.plugin.Plugin.pf;
import static it.plugin.Plugin.pfyml;

public class Nick implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File nicks = pf;
        FileConfiguration nickyml = pfyml;
        if(sender instanceof Player && args.length==1){
            Player p = (Player) sender;
            if(args[0].equals("reset")){
                nickyml.set(p.getUniqueId()+".UsingNick", false);
                SaveUtility.save(nicks, nickyml);
                NickHandler.onCommandReset(p);
                p.sendMessage("Your name has been reset to "+p.getName());
                return true;
            }
            if(args[0].length()<=16) {
                nickyml.set(p.getUniqueId()+".UsingNick", true);
                NickHandler.onCommand(p,args[0]);
                return true;
            }
            p.sendMessage("Too many characters, max 16!!");
            return true;
        }
        if(args.length==2&&args[0].equals("getOriginalName")){
            Player p = Bukkit.getPlayer(args[1]);
            sender.sendMessage(p.getName()
             + " original name is "
            + pfyml.getString(p.getUniqueId()+".originalName"));
            return true;
        }
        if(args.length==2&&args[0].equals("wrap")){
            Player p = (Player) sender;
            NickHandler.wrap(p,args[1]);
            return true;
        }
        if(sender instanceof Player && sender.hasPermission("admin.nick") && args.length==2){
            Player p = Bukkit.getPlayer(args[0]);
            if(p==null){
                sender.sendMessage("No player found");
                return true;
            }
            nickyml.set(p.getUniqueId()+".UsingNick", true);
            String nick = Colors.AlternateColorCodes('&',args[1]);
            NickHandler.onCommand(p,nick);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> toreturn = new ArrayList<>();
        if(args.length==1 && commandSender.hasPermission("admin.nick")){
            toreturn.add("getOriginalName");
            toreturn.addAll(TabCompleteUtils.getOtherOnlinePlayers((Player)commandSender));

        }
        if(args.length==1){
            toreturn.add("wrap");
        }
        if(args.length==2&&args[0].equals("getOriginalName")){
            toreturn.addAll(TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender));
        }
        return toreturn;
    }
}
