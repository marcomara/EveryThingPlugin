package it.commands.nick;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.plugin.Plugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static it.plugin.Plugin.pfyml;

public class NickHandler {
    public static void onJoin(Player p){
        PlayerProfile pp = p.getPlayerProfile();
        pfyml.set(p.getUniqueId() + ".PP", pp);
        if(pfyml.get(p.getUniqueId()+".CPP")!=null && pfyml.getBoolean(p.getUniqueId()+".UsingNick")){
            PlayerProfile pp2 = pfyml.getSerializable(p.getUniqueId() + ".CPP", PlayerProfile.class);
            try {
                assert pp2 != null;
                p.setPlayerProfile(pp2);
            }catch (Exception e){
                p.sendMessage("Something went wrong swapping your profile");
                Plugin.err.println(e.fillInStackTrace());
            }
        }
    }
    public static void onCommand(Player p, String var,Plugin plugin){
        PlayerProfile pp = plugin.getServer().createProfileExact(p.getUniqueId(),var);
        pp.setTextures(p.getPlayerProfile().getTextures());
        try {
            p.setPlayerProfile(pp);
        }catch (Exception e){
            p.sendMessage("Something went wrong creating your profile");
            Plugin.err.println(e.fillInStackTrace());
        }
        pfyml.set(p.getUniqueId()+".CPP", pp);
    }
    public static void onCommandReset(Player p) {
        PlayerProfile pp = pfyml.getSerializable(p.getUniqueId() + ".PP", PlayerProfile.class);
        assert pp != null;
        p.setPlayerProfile(pp);
        pfyml.set(p.getUniqueId() + ".CPP", null);
    }
    public static void warp(Player p, String name, Plugin plugin){
        PlayerProfile pp = plugin.getServer().createProfileExact(p.getUniqueId(),name);
        String[] spfn = getFromName(name);
        if(spfn == null){
            p.sendMessage(Component.text("Something went wrong, try again").color(NamedTextColor.RED));
            return;
        }
        ProfileProperty sp = new ProfileProperty("textures", spfn[0], spfn[1]);
        pp.setProperty(sp);
        try {
            p.setPlayerProfile(pp);
        }catch (Exception e){
            p.sendMessage("Something went wrong creating your profile");
            Plugin.err.println(e.fillInStackTrace());
        }
        pfyml.set(p.getUniqueId()+".CPP", pp);
    }
    private static String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            DocFlavor.URL url = new DocFlavor.URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[] {texture, signature};
        } catch (IOException e) {
            Plugin.err.println("Could not get skin data from session servers!");
            Plugin.err.println(e.fillInStackTrace());
            return null;
        }
    }
}
