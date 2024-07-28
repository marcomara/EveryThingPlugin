package it.commands.Nick;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static it.plugin.Plugin.lgg;
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
                lgg.warning(e.fillInStackTrace().toString());
            }
        }
    }
    public static void onCommand(Player p, String var){
        PlayerProfile pp = Bukkit.createProfileExact(p.getUniqueId(), var);
        pp.setTextures(p.getPlayerProfile().getTextures());
        try {
            p.setPlayerProfile(pp);
        }catch (Exception e){
            p.sendMessage("Something went wrong creating your profile");
            lgg.warning(e.fillInStackTrace().toString());
        }
        pfyml.set(p.getUniqueId()+".CPP", pp);
    }
    public static void onCommandReset(Player p) {
        PlayerProfile pp = pfyml.getSerializable(p.getUniqueId() + ".PP", PlayerProfile.class);
        assert pp != null;
        p.setPlayerProfile(pp);
        pfyml.set(p.getUniqueId() + ".CPP", null);
    }
    public static void wrap(Player p, String name){
        PlayerProfile pp = Bukkit.createProfileExact(p.getUniqueId(),name);
        String[] spfn = getFromName(name);
        if(spfn == null){
            p.sendMessage(Component.text("Something went wrong, try again").color(NamedTextColor.RED));
            return;
        }
        ProfileProperty Pp = new ProfileProperty("textures", spfn[0], spfn[1]);
        pp.setProperty(Pp);
        try {
            p.setPlayerProfile(pp);
        }catch (Exception e){
            p.sendMessage("Something went wrong creating your profile");
            lgg.warning(e.fillInStackTrace().toString());
        }
        pfyml.set(p.getUniqueId()+".CPP", pp);
    }
    private static String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            return new String[] {texture, signature};
        } catch (IOException e) {
            lgg.warning("Could not get skin data from session servers!");
            lgg.warning(e.fillInStackTrace().toString());
            return null;
        }
    }
}
