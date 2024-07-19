package it.commands.ResourcePacks;

import it.commands.ResourcePacks.Server.Server;
import it.plugin.Plugin;
import it.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import static it.plugin.Plugin.*;

public class Starter {
    public static YamlConfiguration config;

    public Starter(File folder, Plugin plugin) {
        File configFile = new File(folder, "RPConfig.yml");
        try {
            if (!configFile.exists()) {
                InputStream is = plugin.getResource("RPConfig.yml");
                FileWriter fw = new FileWriter(configFile);
                assert is != null;
                for(byte b: is.readAllBytes()){
                    fw.write(b);
                }
                fw.close();
                is.close();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        double newVersion = 0.0;
        InputStream is = plugin.getResource("RPConfig.yml");
        Scanner s = new Scanner(is);
        while (s.hasNextLine()){
            String str = s.nextLine();
            if(str.startsWith("ConfigVersion")){
                str = str.substring("ConfigVersion: ".length());
                newVersion = Double.parseDouble(str);
            }
        }
        if(config.getInt("ConfigVersion")<newVersion||config.get("ConfigVersion")==null){
            FileUtil.updateConfigFromFile(configFile, plugin.getResource("RPConfig.yml"),true);
        }
        Map<String, File> map = new HashMap<>();
        if (config.contains("packs")) {
            for (String key : config.getConfigurationSection("packs").getKeys(false)) {
                String name = config.getString("packs." + key);
                File file = new File(folder, name);
                if (!file.exists()) {
                    lgg.log(Level.WARNING, "Resource pack '" + name + "' does not exist!");
                } else {
                    lgg.info("Discovered resource pack '" + name + "'");
                    map.put(key, file);
                }
            }
        }
        instance = new Instance(config.getInt("port"), Ip(config.getBoolean("localhost")), map);
        try{
            Server.main(instance.getPort(), map);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String Ip(boolean l) {
        if (l) {
            return Bukkit.getIp();
        } else {
            return PublicIP();
        }
    }

    private String PublicIP() {
        try {
            URL url = new URL("https://api.ipify.org");
            InputStream stream = url.openStream();
            Scanner s = new Scanner(stream, StandardCharsets.UTF_8).useDelimiter("\\A");
            String ip = s.next();
            s.close();
            stream.close();
            return ip;
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }
}
