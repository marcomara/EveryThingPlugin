package it.commands.Skin;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static it.plugin.Plugin.plugin;


public class SkinUpdate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String bho, @NotNull String[] strings) {
        if (commandSender instanceof Player p){
            UUID uuid = p.getUniqueId();
            try {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString() + "?unsigned=false");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                String response = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
                String[] ra = response.split("\n");
                String val="",sig="";
                for (String s : ra){
                    s=s.replaceAll(" ", "");
                    if (s.startsWith("\"value\"")){
                        val=s.substring(9,s.length()-2);
                    }
                    if (s.startsWith("\"signature\"")){
                        sig=s.substring(13,s.length()-1);
                    }
                }
                PlayerProfile pp = Bukkit.createProfile(p.getUniqueId(),p.getName());
                pp.setProperty(new ProfileProperty("textures", val,sig));
                p.setPlayerProfile(pp);
                for (Player op : Bukkit.getOnlinePlayers()){
                    op.hidePlayer(plugin, p);
                    op.showPlayer(plugin, p);
                }
            } catch (Exception e) {
                e.printStackTrace();
                p.sendMessage(Component.text("An error occurred while updating your skin").color(NamedTextColor.RED));
            }
            return true;
        }
        return false;
    }
}
