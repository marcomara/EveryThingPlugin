package it.commands.Roles;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.Map;


public class Role {
    private String name;
    private String prefix;
    private String Hex;


    public Role(String name, String prefix, String hex){
        this.name = name;
        this.prefix = prefix;
        this.Hex = hex;
    }
    public Map<String, String> serialize() {
        return Map.of("Name" , name,
                "Prefix", prefix,
                "Color", Hex);
    }

    public String getName(){return name;}

    public Component getPrefix() {
        return Component.text(prefix).color(TextColor.fromHexString(Hex));
    }

    public Component getDisplayPrefix(){
        return Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text(prefix).color(TextColor.fromHexString(Hex)))
                .append(Component.text("] ").color(NamedTextColor.GRAY));
    }
}
