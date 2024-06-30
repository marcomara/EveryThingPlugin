package it.economy;

import it.plugin.Plugin;
import it.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import it.utils.SaveUtility;

import java.io.File;

public class Balance implements CommandExecutor {
    private final Plugin plugin;
    public Balance(Plugin plugin){
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        File pf = plugin.pf;
        FileConfiguration pfyml = plugin.pfyml;
        String et = plugin.ecotype;
        if(sender instanceof Player){
            if(args.length==0){
                sender.sendMessage(Colors.GOLD + "Your balance is: "+ Colors.WHITE + pfyml.getLong(((Player)sender).getUniqueId()+".balance") + et);
            }
            if(args.length==3&&args[0].equals("send")){
                Player t = Bukkit.getPlayer(args[1]);
                long money = Long.parseLong(args[2]);
                long balance = pfyml.getLong(t.getUniqueId()+".balance");
                Player don = (Player) sender;
                long donbal = pfyml.getLong(don.getUniqueId()+".balance");
                if(donbal>money) {
                    pfyml.set(don.getUniqueId() + ".balance", donbal - money);
                    pfyml.set(t.getUniqueId() + ".balance", balance + money);
                    t.sendMessage(Colors.GREEN + don.getName() + Colors.GOLD + " sent you: " + Colors.WHITE + money + et);
                    don.sendMessage(Colors.GOLD + "You sent " + Colors.YELLOW + t.getName() + " " + Colors.WHITE + money + et);
                    t.sendMessage(Colors.GOLD + "Your balance is now: "+ Colors.WHITE +pfyml.getLong(t.getUniqueId()+".balance") + et);
                    don.sendMessage(Colors.GOLD + "Your balance is now: "+ Colors.WHITE +pfyml.getLong(don.getUniqueId()+".balance") + et);
                }
            }
            if(sender.isOp()){
                if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    long balance = pfyml.getLong(t.getUniqueId() + ".balance");
                    sender.sendMessage(args[0] + "'s balance is " + balance + et);
                }
                if(args.length==3){
                    Player t = Bukkit.getPlayer(args[1]);
                    long money = Long.parseLong(args[2]);
                    long balance = pfyml.getLong(t.getUniqueId()+".balance");
                    switch (args[0]){
                        case "add":
                            pfyml.set(t.getUniqueId()+".balance", balance+money);
                            t.sendMessage(Colors.GOLD + "Your balance is now: "+ Colors.WHITE +pfyml.getLong(t.getUniqueId()+".balance") + et);
                            t.sendMessage(Colors.GREEN + "An operator just gave you " + money + et);
                            break;
                        case "sub":
                            pfyml.set(t.getUniqueId()+".balance", balance-money);
                            t.sendMessage(Colors.GOLD + "Your balance is now: "+ Colors.WHITE +pfyml.getLong(t.getUniqueId()+".balance") + et);
                            t.sendMessage(Colors.RED + "An operator took " + money + et + " from you");
                            break;
                        case "set":
                            pfyml.set(t.getUniqueId()+".balance", money);
                            t.sendMessage(Colors.GOLD + "Your balance is now: "+ Colors.WHITE +pfyml.getLong(t.getUniqueId()+".balance") + et);
                            t.sendMessage(Colors.YELLOW + "An operator set your balance to " + money + et);
                            break;
                    }
                    SaveUtility.save(pf, pfyml);
                }

            }
        return true;
        }
        return false;
    }
}
