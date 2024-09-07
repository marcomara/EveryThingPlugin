package it.commands.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static it.plugin.Plugin.lgg;

public class WarpPoint implements ConfigurationSerializable{
    private String name;
    private Location location;
    public WarpPoint(Location location , String name){
        this.name = name;
        this.location = location;
    }
    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("name",name);
        data.put("location",location);
        return data;
    }
    public static WarpPoint deserialize(@NotNull Map<String,Object> data){
        try {
            return new WarpPoint((Location) data.get("location"), (String) data.get("name"));
        }catch (IllegalArgumentException e){
            lgg.warning("Some of your warp point to other worlds and will not work, worlds are not supported yet");
            return null;
        }
    }
}
