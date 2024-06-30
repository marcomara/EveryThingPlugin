package it.commands.skin;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
/*import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;**/
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import it.plugin.Plugin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CommandHandler {
    public static void Reset(Player player, Plugin plugin){
        player.setPlayerProfile((PlayerProfile) plugin.pfyml.get(player.getUniqueId()+".PP"));
    }
    public static void setURL(Player player,String url, Plugin plugin){
        PlayerProfile pp = player.getPlayerProfile();
        byte[] nsb = url.getBytes(StandardCharsets.UTF_8);
        ProfileProperty np = new ProfileProperty("textures",Base64.getEncoder().encodeToString(nsb));
        pp.setProperty(np);
        player.setPlayerProfile(pp);
        plugin.pfyml.set(player.getUniqueId()+".CCP", pp);
    }
    public static void info(Player player){
        /*CraftPlayer p = (CraftPlayer) player;
        GameProfile gp = p.getProfile();
        PropertyMap pm = gp.getProperties();
        Property pr = pm.get("textures").iterator().next();
        String ps = pr.value();
        byte[] c = Base64.getDecoder().decode(ps);
        String s = new String(c, StandardCharsets.UTF_8);
        player.sendMessage(Component.text(s));*/
        /*try{
            MessageDigest dig = MessageDigest.getInstance("SHA-1");
            byte[] bit = dig.digest(((CraftPlayer) player).getProfile().getProperties().get("textures").iterator().next().value().getBytes());
            String sig = Base64.getEncoder().encodeToString(bit);
            Plugin.ccs.sendMessage(Component.text(sig));
        }catch (NoSuchAlgorithmException ignore){}*/
    }
}
