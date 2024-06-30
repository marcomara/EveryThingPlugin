package it.commands.skin;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static it.commands.skin.CommandHandler.*;

public class Command implements CommandExecutor {
    private final Plugin plugin;
    public Command(Plugin plugin){
        this.plugin=plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) sender;
        if(args[0].equals("info")){
            info(p);
            return true;
        }
        if(args[0].equals("reset")){
            Reset(p, plugin);
            return true;
        }
        if(args.length==2&&args[0].equals("url")){
            setURL(p,args[1], plugin);
            return true;
        }
        if(args.length==3&&args[0].equals("set")&&sender.isOp()&&Bukkit.getPlayer(args[1]).isOnline()){
            setURL(Bukkit.getPlayer(args[1]),args[2], plugin);
            return true;
        }
        return true;
    }
}
