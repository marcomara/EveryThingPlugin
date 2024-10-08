package it.commands.Economy;

import it.utils.Colors;
import it.utils.TabCompleteUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import it.utils.SaveUtility;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.plugin.Plugin.*;

public class Balance implements CommandExecutor, TabCompleter {
    public static final String[] arguments1 = {"send", "add", "sub", "set"};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String et = ecotype;
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
            if(sender.hasPermission("admin.mmod")){
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
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> toreturn = new ArrayList<>();
        if(args.length==1) {
            if (commandSender.hasPermission("admin.mmod")) {
                toreturn.addAll(Arrays.asList(arguments1));
                return toreturn;
            }
            toreturn.add(arguments1[0]);
            return toreturn;
        }
        if(args.length==2) return TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender);
        return toreturn;
    }
}
