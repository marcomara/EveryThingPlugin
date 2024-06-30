package it.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class ChatPlayerName {
    public static Component msg(Player p, String omsg){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text(p.getName()).color(NamedTextColor.DARK_PURPLE))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(omsg).color(NamedTextColor.WHITE));
    }
    public static Component join(Player p){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text("+").color(NamedTextColor.GREEN))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(p.getName()));
    }
    public static Component quit(Player p){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text("-").color(NamedTextColor.RED))
                .append(Component.text("] ").color(NamedTextColor.GRAY))
                .append(Component.text(p.getName()));
    }
}
