package it.commands.warp;

import it.utils.Colors;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.*;

import java.io.File;

public class Home implements CommandExecutor{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File homes = pf;
        FileConfiguration homesyml = pfyml;
        save(homes, homesyml);
        if(sender instanceof Player){
            if(args.length==0){
                Location L = homesyml.getLocation(((Player) sender).getUniqueId()+ ".home");
                if (L==null){sender.sendMessage(Colors.RED + "No home found");}
                else if(homesyml.getString(((Player) sender).getUniqueId()+ ".home")!=null){
                    ((Player) sender).teleport(L);
                    sender.sendMessage(Colors.GREEN + "You've been teleported to your home");
                }
                return true;
            }
            if(args.length==1){
                if(args[0].equals("set")){
                    Location L=((Player) sender).getLocation();
                    if(homesyml.get(((Player) sender).getUniqueId() + ".home")!=null){
                        homesyml.set(((Player) sender).getUniqueId() + ".home",L);
                    }else{
                        homesyml.createSection(((Player) sender).getUniqueId() + ".home");
                        homesyml.set(((Player) sender).getUniqueId() + ".home",L);
                    }
                    sender.sendMessage(Colors.GREEN + "Your home has been set");
                }
                if(args[0].equals("remove")){
                    homesyml.set(((Player) sender).getUniqueId() + ".home", null);
                    sender.sendMessage(Colors.RED + "Your home has been removed");
                }
            }
            save(homes, homesyml);
            return true;
        }
        if(sender instanceof ConsoleCommandSender){
            err.println("Only usable by players");
            return true;
        }
        return false;
    }
}
