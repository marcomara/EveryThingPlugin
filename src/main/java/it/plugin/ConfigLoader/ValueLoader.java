package it.plugin.ConfigLoader;

import it.plugin.Plugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import static it.plugin.Plugin.*;

public class ValueLoader {
    static Plugin plugin ;
    private static final String ver = "ConfigVersion";
    public static void ValueLoaderMain(Plugin plugin){
        ValueLoader.plugin=plugin;
        ConfigurationCheck();
        if(plugin.cancontinue) {
            ValueRegister();
        }
    }
    public static void ConfigurationCheck(){
        if(!dataFolder.exists()) {
            File olddf = new File(dataFolder.getParentFile(), "Plugin");
            if (olddf.exists()) {
                YamlConfiguration oldcfg = YamlConfiguration.loadConfiguration(new File(olddf, "config.yml"));
                if (oldcfg.getInt("ConfigVersion") < 8) {
                    try {
                        Files.move(new File(dataFolder.getParentFile(), "Plugin").toPath(), dataFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
                        lgg.warning("Plugin folder updated");
                    } catch (Exception e) {
                        err.println(e.fillInStackTrace());
                        lgg.warning("Update went wrong, try to copy the files from ServerDirectory/plugins/Plugin to ServerDirectory/plugins/EveryThingPlugin");
                        plugin.FullDisable = false;
                        plugin.cancontinue = false;
                        Bukkit.getPluginManager().disablePlugin(plugin);
                        return;
                    }
                }
            }
        }
        if(plugin.getConfig().get(ver) instanceof Double){
            ConfigUpdater();
            return;
        }
        int av = plugin.getConfig().getInt(ver), bv = plugin.getConfig().getDefaults().getInt(ver);
        if(av==bv){
            ccs.sendMessage("Configuration checked correctly");
            return;
        }
        if(av<bv){
            ConfigUpdater();
            return;
        }
        plugin.cancontinue = false;
        err.println("You are trying to run an older version of this plugin, disabling it");
        plugin.FullDisable = false;
        plugin.cancontinue = false;
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
    public static void ValueRegister(){
        for(String str : plugin.getConfig().getDefaults().getValues(true).keySet()){
            Object obj = plugin.getConfig().get(str);
            if(obj instanceof Boolean bool){
                booleanMap.put(str,bool);
            }
            if(obj instanceof String s){
                strMap.put(str,s);
            }
            if(obj instanceof Integer i){
                intMap.put(str,i);
            }
        }
        ccs.sendMessage(Component.text("Values Registered").color(NamedTextColor.WHITE));
    }
    private static void ConfigUpdater(){
        Map<String, Object> oldcfg = plugin.getConfig().getValues(true);
        InputStream is = plugin.getResource("config.yml");
        try {
            FileWriter fw = new FileWriter(new File(plugin.getDataFolder(), "config.yml"));
            assert is != null;
            for(byte b : is.readAllBytes()){
                fw.write(b);
            }
            fw.close();
            is.close();
        }catch (Exception e){
            err.println(e.fillInStackTrace());
        }
        plugin.reloadConfig();
        for(String s : oldcfg.keySet()){
            plugin.getConfig().set(s, oldcfg.get(s));
        }
        plugin.getConfig().set(ver, plugin.getConfig().getDefaults().get(ver));
        plugin.saveConfig();
        lgg.warning("Configuration updated");
    }
}
