package it.commands.warp;

import it.utils.Colors;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.*;


public class Home implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==0){
                HomePoint home;
                try {
                    home = HomePoint.deserialize((Map<String,Object>) pfyml.get(p.getUniqueId()+".home"));
                }catch (NullPointerException e){
                    e.fillInStackTrace();
                    p.sendMessage(Colors.RED + "No home found");
                    return true;
                }
                p.teleport(new Location(home.w,home.x,home.y,home.z));
                p.sendMessage(Colors.GREEN + "You've been teleported to your home");
                return true;
            }
            if(args.length==1){
                if(args[0].equals("set")){
                    HomePoint h = new HomePoint(p.getX(),p.getY(),p.getZ(),p.getWorld().getUID());
                    pfyml.set(p.getUniqueId() + ".home", h.serialize());
                    p.sendMessage(Colors.GREEN + "Your home has been set");
                    save(pf, pfyml);
                    return true;
                }
                if(args[0].equals("remove")){
                    pfyml.set(p.getUniqueId() + ".home", null);
                    p.sendMessage(Colors.RED + "Your home has been removed");
                    save(pf, pfyml);
                    return true;
                }
            }
        }
        if(sender instanceof ConsoleCommandSender){
            lgg.warning("Only usable by players");
            return true;
        }
        return false;
    }
}
