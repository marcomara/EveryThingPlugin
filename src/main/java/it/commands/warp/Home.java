package it.commands.warp;

import it.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.*;


public class Home implements CommandExecutor, TabCompleter{
    public static final String[] arguments1 = {"set", "remove"};
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length==0){
                HomePoint home;
                if(pfyml.get(p.getUniqueId() + ".home") instanceof Location){
                    Location l = (Location) pfyml.get(p.getUniqueId() + ".home");
                    pfyml.set(p.getUniqueId() + ".home", new HomePoint(l.x(), l.y(), l.z(), l.getWorld().getUID().toString(),l.getPitch(),l.getYaw())) ;
                }
                try {
                    home = HomePoint.deserialize(pfyml.getStringList(p.getUniqueId() + ".home"));
                }catch (NullPointerException e){
                    e.fillInStackTrace();
                    p.sendMessage(Colors.RED + "No home found");
                    return true;
                }
                p.teleport(new Location(Bukkit.getWorld(UUID.fromString(home.w)),home.x,home.y,home.z,home.yaw,home.p));
                p.sendMessage(Colors.GREEN + "You've been teleported to your home");
                return true;
            }
            if(args.length==1){
                if(args[0].equals("set")){
                    HomePoint h = new HomePoint(p.getX(),p.getY(),p.getZ(),p.getWorld().getUID().toString(),p.getPitch(),p.getYaw());
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) return new ArrayList<>(Arrays.asList(arguments1));
        return new ArrayList<>();
    }
}
