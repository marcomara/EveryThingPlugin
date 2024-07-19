package it.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteUtils {
    public static List<String> getOtherOnlinePlayers(Player p){
        List<String> op = new ArrayList<>();
        for (Player po : Bukkit.getOnlinePlayers()){
            if(po == p) continue;
            op.add(po.getName());
        }
        return op;
    }

}
