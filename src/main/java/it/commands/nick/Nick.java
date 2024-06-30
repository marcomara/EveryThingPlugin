package it.commands.nick;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import it.utils.SaveUtility;
import it.utils.Colors;
public class Nick implements CommandExecutor {
    private final Plugin plugin;
    public Nick(Plugin plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File nicks = plugin.pf;
        FileConfiguration nickyml = plugin.pfyml;
        if(sender instanceof Player && args.length==1){
            Player p = (Player) sender;
            if(args[0].equals("reset")){
                nickyml.set(p.getUniqueId()+".UsingNick", false);
                SaveUtility.save(nicks, nickyml);
                NickHandler.onCommandReset(p,plugin);
                p.sendMessage("Your name has been reset to "+p.getName());
                return true;
            }
            if(args[0].length()<=16) {
                nickyml.set(p.getUniqueId()+".UsingNick", true);
                String nick = Colors.AlternateColorCodes('&',args[0]);
                NickHandler.onCommand(p,nick,plugin);
                return true;
            }
            p.sendMessage("Too many characters, max 16!!");
            return true;
        }
        if(args.length==2&&args[0].equals("getOriginalName")){
            Player p = Bukkit.getPlayer(args[1]);
            sender.sendMessage(p.getName()
             + " original name is "
            + plugin.pfyml.getString(p.getUniqueId()+".originalName"));
            return true;
        }
        if(args.length==2&&args[0].equals("warp")){
            Player p = (Player) sender;
            NickHandler.warp(p,args[1],plugin);
            return true;
        }
        if(sender instanceof Player && sender.isOp() && args.length==2){
            Player p = Bukkit.getPlayer(args[0]);
            if(p==null){
                sender.sendMessage("No player found");
                return true;
            }
            nickyml.set(p.getUniqueId()+".UsingNick", true);
            String nick = Colors.AlternateColorCodes('&',args[1]).toString();
            NickHandler.onCommand(p,nick,plugin);
            return true;
        }
        return false;
    }

}
