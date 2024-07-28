package it.commands.TPA.handlers;

import it.commands.TPA.Data;
import it.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class request {
    CommandSender sender;
    String target;
    HashMap<String, List<Data>> table;
    public request(HashMap<String, List<Data>> table, CommandSender sender, String target){
        this.sender=sender;
        this.target=target;
        this.table=table;
    }
    public void request() {
        if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(target))){
            sender.sendMessage("You need to specify an online player");
        }
        List<Data> list;
        if(!table.containsKey(target)){
            list = new ArrayList<>();
        }else{
            list = table.get(target);
        }
        if(list==null){
            sender.sendMessage("An error occurred, this will be auto-reported to the server admins");
            for(Player all : Bukkit.getOnlinePlayers()){
                if(all.isOp()){
                    all.sendMessage("An error in the tpa command execution occurred");
                }
            }
        }
        Bukkit.getPlayer(target).sendMessage(sender.getName() + " wants to teleport to your location, use"+ Colors.GOLD + " /tpa allow " + sender.getName() + Colors.WHITE + " to accept the request, otherwise use"+ Colors.GOLD +" /tpa deny " + sender.getName() + Colors.WHITE +" to reject the request");
        Data data = new Data((int) (System.currentTimeMillis()/1000), target, sender.getName());
        list.add(data);
        if(table.containsKey(target)){
            table.replace(target,list);
        }else{
            table.put(target,list);
        }
    }
}
