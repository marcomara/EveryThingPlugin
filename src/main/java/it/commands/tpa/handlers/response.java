package it.commands.tpa.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import it.commands.tpa.Data;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class response {
    CommandSender sender;
    String applicant;
    boolean condition;
    HashMap<String, List<Data>> table;
    int timer;
    public response(HashMap<String, List<Data>> table, CommandSender sender, String applicant, boolean condition, int timer){
        this.sender=sender;
        this.applicant=applicant;
        this.condition=condition;
        this.table=table;
        this.timer=timer;
    }
    public void response(){
        Player p = Bukkit.getPlayer(applicant);
        List<Data> list = table.get(sender.getName());
        if(list.isEmpty()){
            sender.sendMessage("You have no requests");
            return;
        }
        int position = 0;
        for(Data data : list){
            if(data.getSender().equals(applicant)){
                position=list.indexOf(data);
                if(condition){
                    int time = (int) (System.currentTimeMillis()/1000);
                    if(time>(data.getTime()+timer)){
                        sender.sendMessage("The request from " + applicant + " has expired");
                        p.sendMessage("Your request has expired and " + sender.getName() + " can't accept it");
                        break;
                    }
                    p.teleport(((Player) sender).getLocation());
                    p.sendMessage("You have been teleported to " + sender.getName() + "'s location");
                    sender.sendMessage(p.getName() + " has been teleported to your location");
                }else{
                    p.sendMessage(sender.getName() + " rejected your request");
                }
                break;
            }
        }
        list.remove(position);
        table.replace(sender.getName(), list);
    }
}
