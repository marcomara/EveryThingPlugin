package it.plugin.ConfigLoader;

import it.utils.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static it.plugin.Plugin.*;

public class ValueLoader {
    private static final String ver = "ConfigVersion";
    public static void ValueLoaderMain(){
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
                        lgg.warning(e.fillInStackTrace().toString());
                        lgg.warning("Update went wrong, try to copy the files from ServerDirectory/plugins/Plugin to ServerDirectory/plugins/EveryThingPlugin");
                        plugin.FullDisable = false;
                        plugin.cancontinue = false;
                        Bukkit.getPluginManager().disablePlugin(plugin);
                        return;
                    }
                }
            }
        }
        if(!new File(dataFolder, "config.yml").exists()){
            plugin.saveDefaultConfig();
            return;
        }
        if(plugin.getConfig().get(ver) instanceof Double){
            try {
                FileUtil.updateConfigPurge(new File(dataFolder, "config.yml"), plugin.getResource("config.yml"));
            }catch (Exception e){
                e.printStackTrace();
                plugin.FullDisable = false;
                plugin.cancontinue = false;
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
            return;
        }
        int av = plugin.getConfig().getInt(ver), bv = plugin.getConfig().getDefaults().getInt(ver);
        if(av==bv){
            lgg.info("Configuration checked");
            return;
        }
        if(av<bv){
            try {
                FileUtil.updateConfigPurge(new File(dataFolder, "config.yml"), plugin.getResource("config.yml"));
            }catch (Exception e){
                e.printStackTrace();
                plugin.FullDisable = false;
                plugin.cancontinue = false;
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
            return;
        }
        plugin.cancontinue = false;
        lgg.warning("You are trying to run an older version of this plugin, disabling it");
        plugin.FullDisable = false;
        plugin.cancontinue = false;
        Bukkit.getPluginManager().disablePlugin(plugin);
    }
    public static void ValueRegister(){
        for(String str : plugin.getConfig().getDefaults().getValues(true).keySet()){
            Object obj = plugin.getConfig().get(str);
            if(obj instanceof Boolean){
                boolean bool =(boolean) obj;
                booleanMap.put(str,bool);
            }
            if(obj instanceof String){
                String s = (String) obj;
                strMap.put(str,s);
            }
            if(obj instanceof Integer){
                int i = (int) obj;
                intMap.put(str,i);
            }
        }
        lgg.info("Configuration Loaded");
    }
}
