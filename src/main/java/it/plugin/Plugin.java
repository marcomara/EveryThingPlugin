package it.plugin;

import it.commands.DisabledCommandMessage;
import it.commands.ResourcePacks.Instance;
import it.commands.TPA.Data;
import it.commands.Leash.CollisionTeam;
import it.plugin.StartupLoaders.*;
import it.utils.UpdateChecker;
import it.web.Host;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import it.utils.Metrics;

import static it.plugin.ConfigLoader.ValueLoader.*;
import static it.utils.SaveUtility.*;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public static Instance instance;
    public static List<Entity> sitlist = new ArrayList<>();
    public boolean cancontinue = true;
    public static CollisionTeam team;
    public static File pf;
    public static FileConfiguration pfyml;
    public static String ecotype;
    public static File CFile;
    public static FileConfiguration CFC;
    public static List<String> LoadedChunks = new ArrayList<>();
    public static List<String> commands = new ArrayList<>();
    public static int tpatimer;
    public static HashMap<String, List<Data>> tpatable = new HashMap<>();
    public boolean FullDisable = true;
    public static Map<String, Boolean> booleanMap = new HashMap<>();
    public static Map<String, String> strMap = new HashMap<>();
    public static Map<String, Integer> intMap = new HashMap<>();
    public static Logger lgg;
    public static String ver;
    public static File dataFolder;
    private Metrics metrics;
    public static boolean updateTell = false;
    public static DisabledCommandMessage executor = new DisabledCommandMessage();
    public static YamlConfiguration worlds;
    public static File bkfolder;
    public static it.plugin.Plugin plugin;

    @Override
    public void onLoad() {
        if (getServer().getMinecraftVersion().startsWith("1.1") || (getServer().getMinecraftVersion().startsWith("1.20") && !getServer().getMinecraftVersion().equals("1.20.6"))){
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().warning("***MINECRAFT VERSION NOT SUPPORTED***");
            getLogger().warning("Check the supported versions here:");
            getLogger().warning("https://www.spigotmc.org/resources/everything-plugin.107875");
            cancontinue = false;
            return;
        }
        plugin = this;
        dataFolder = getDataFolder();
        ver = getPluginMeta().getVersion();
        lgg = getLogger();
        commands = new ArrayList<>();
        pf = new File(getDataFolder(), "players.yml");
        create(pf);
        pfyml = createyml(pf);
        ValueLoaderMain();
        metrics = new Metrics(this, 22468);
        UpdateChecker.init(this, 107875).requestUpdateCheck().whenComplete((result, e) -> {
            if (result.getReason() == UpdateChecker.UpdateReason.NEW_UPDATE) {
                lgg.warning("!!!You are running an outdated version of " + this.getName() + "!!!");
                lgg.warning(ver + " < " + result.getNewestVersion());
                lgg.warning("Get version " + result.getNewestVersion() + " here: https://www.spigotmc.org/resources/everything-plugin.107875/history");
                if (booleanMap.get("AdminUtils.UpdateTellToOps")) {
                    updateTell = true;
                }
            }
        });
    }

    @Override
    public void onEnable() {
        if (cancontinue) {
            lgg.log(Level.INFO, "Enabled");
            MiscLoader.Loader();
            MiscLoader.EventLoader();
            AdminUtilsLoader.CommandRegister();
            CommandsLoader.CommandsLoader();
            /*Bukkit.getScheduler().runTaskAsynchronously(this, ()->{
                try {
                    Host.main(8080);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });*/
        }
    }

    @Override
    public void onDisable() {
        if (cancontinue) {
            MiscLoader.Stop();
            lgg.log(Level.INFO, "Disabled");
            metrics.shutdown();
        }
        /*try{
            Host.stop();
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}