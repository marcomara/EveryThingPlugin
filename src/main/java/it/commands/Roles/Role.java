package it.commands.Roles;

import net.kyori.adventure.text.Component;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Role implements ConfigurationSerializable {
    private String name;
    private Component prefix;
    private int position;

    public Role(String name, Component prefix, int position){
        this.name=name;
        this.prefix=prefix;
        this.position=position;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("Name" , name,
                "Prefix", prefix,
                "Position", position);
    }

    public String getName(){return name;}

    public Component getPrefix(){return prefix;}

    public int getPosition(){return position;}

    public Role deserialize(Map<String, Object> map){
        return new Role((String) map.get("Name"), (Component) map.get("Prefix"), (Integer) map.get("Position"));
    }
}
