package it.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static it.plugin.Plugin.lgg;
import static it.plugin.Plugin.roles;

public class ChatUtils {
    public static Component msg(Player p, String omsg){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text(p.getName()).color(NamedTextColor.GREEN))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(omsg).color(NamedTextColor.WHITE));
    }
    public static Component rmsg(Player p, String omsg){
        try {
            return roles.getPlayerTeam(p).prefix().append(Component.text(" ")).append(msg(p, omsg));
        }catch (Exception e){
            lgg.warning("No team found for the messaging player");
            return msg(p,omsg);
        }
    }
    public static Component join(Player p){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text("+").color(NamedTextColor.GREEN))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(p.getName()));
    }

    public static Component rjoin(Player p){
        try {
            return roles.getPlayerTeam(p).prefix().append(Component.text(" ")).append(join(p));
        }catch (NullPointerException e){
            lgg.warning("No team found for the joining player");
            return join(p);
        }
    }
    public static Component quit(Player p){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text("-").color(NamedTextColor.RED))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(p.getName()));
    }

    public static Component rquit(Player p){
        try {
            return roles.getPlayerTeam(p).prefix().append(Component.text(" ")).append(quit(p));
        }catch (NullPointerException e){
            lgg.warning("No team found for the quitting player");
            return quit(p);
        }
    }
    public static void BroadcastToOPsSwitch (String msg, int importance){
        switch (importance){
            case 0 : BroadcastToOPs(msg, NamedTextColor.GREEN);
            case 1 : BroadcastToOPs(msg, NamedTextColor.GOLD);
            case 2 : BroadcastToOPs(msg, NamedTextColor.RED);
            case 3 : BroadcastToOPs(msg, NamedTextColor.DARK_RED);
        }
    }

    public static void BroadcastToOPs(String msg, NamedTextColor color){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.hasPermission("broadcast.op")) p.sendMessage(Component.text(msg).color(color));
        }
    }
}
