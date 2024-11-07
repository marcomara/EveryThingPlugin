package it.commands.Skin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.Gson;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static it.plugin.Plugin.*;

public class Command implements CommandExecutor {
    private File folder = new File(dataFolder, "skins");
    public Command(){
        folder.mkdirs();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        if (args.length==1){
            Player p = (Player) sender;
            String nu = args[0];
            try {
                PlayerProfile pp = Bukkit.createProfile(p.getUniqueId(), p.getName());
                pp.setProperty(new ProfileProperty("textures", calculateSkinValue(nu, p.getUniqueId().toString(), p.getName()),null));
                p.setPlayerProfile(pp);
                lgg.info(p.getPlayerProfile().getTextures().getSkin().toString());
                WrappedGameProfile wgp = new WrappedGameProfile(p.getUniqueId(),p.getName());
                wgp.getProperties().put("textures", new WrappedSignedProperty("SKIN", calculateSkinValue(nu, p.getUniqueId().toString(), p.getName()), null));
                PacketContainer pc = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
                pc.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
                pc.getPlayerInfoDataLists().write(0, Collections.singletonList(
                        new PlayerInfoData(wgp, 0, EnumWrappers.NativeGameMode.NOT_SET, WrappedChatComponent.fromText(p.getName()))
                ));
                for (Player po : Bukkit.getOnlinePlayers()) {
                    manager.sendServerPacket(po, pc);
                    po.hidePlayer(plugin, p);
                    po.showPlayer(plugin, p);
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else sender.sendMessage("Wrong use");
        return true;
    }

    public void downloadTexture(URL url,String playerUUID) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int rcode = conn.getResponseCode();
        if (rcode==HttpURLConnection.HTTP_OK){
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(new File(folder, playerUUID+".png"));
            byte[] buffer = new byte[4096];
            int read =-1;
            while((read=is.read(buffer))!=-1){
                fos.write(buffer, 0, read);
            }
            fos.close();
            is.close();
        }
        conn.disconnect();
    }
    public String calculateSkinValue(String url, String pUUID, String name) throws IOException {
        downloadTexture(new URL(url),pUUID);
        File f = new File(folder, pUUID + ".png");
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[(int)f.length() ];
        fis.read(buffer);
        fis.close();
        String value = Base64.getEncoder().encodeToString(buffer);
        Map<String, Object> payload = new HashMap<>();
        payload.put("timestamp", System.currentTimeMillis());
        payload.put("profileId", pUUID);
        payload.put("profileName", name);
        Map<String, Object> textures = new HashMap<>();
        Map<String,String> skin = new HashMap<>();
        skin.put("url", "http://textures.minecraft.net/texture/" + value);
        textures.put("SKIN",skin);
        payload.put("textures", textures);
        Gson gson = new Gson();
        lgg.info(gson.toJson(payload));
        return Base64.getEncoder().encodeToString(gson.toJson(payload).getBytes());
    }
}
