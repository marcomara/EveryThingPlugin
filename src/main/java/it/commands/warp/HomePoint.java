package it.commands.warp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomePoint implements ConfigurationSerializable {
    public double x,y,z;
    public World w;
    public HomePoint(double x, double y, double z, UUID w){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w= Bukkit.getWorld(w);
    }
    public void set(double x, double y, double z, World w){
        this.x= x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String , Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z",z);
        map.put("World", w.getUID().toString());
        return map;
    }

    public static HomePoint deserialize(Map<String,Object> map){
        return new HomePoint((Double) map.get("x"), (Double) map.get("y"), (Double) map.get("z"), UUID.fromString((String) map.get("World")));
    }
}
