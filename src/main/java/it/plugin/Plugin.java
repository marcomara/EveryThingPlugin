package it.plugin;

import it.commands.tpa.Data;
import it.leash.CollisionTeam;
import it.plugin.StartupLoaders.*;
import it.utils.Colors;
import it.utils.UpdateChecker;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import it.utils.Metrics;

import static it.plugin.ConfigLoader.ValueLoader.*;
import static it.utils.SaveUtility.*;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public boolean cancontinue = true;
    public CollisionTeam team;
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
    public static ConsoleCommandSender ccs;
    public static Logger lgg;
    public static PrintStream err;
    public static String ver;
    public static File dataFolder;
    private Metrics metrics;
    public static boolean updateTell=false;
    @Override
    public void onLoad(){
        err = System.err;
        dataFolder = getDataFolder();
        ver = getDescription().getVersion();
        lgg = getLogger();
        ccs = getServer().getConsoleSender();
        commands = new ArrayList<>();
        pf = new File(getDataFolder(), "players.yml");
        create(pf);
        pfyml = creatyml(pf);
        ValueLoaderMain(this);
        metrics = new Metrics(this, 22468);
        UpdateChecker.init(this, 107875).requestUpdateCheck().whenComplete((result, e )-> {
            if(result.getReason() == UpdateChecker.UpdateReason.NEW_UPDATE){
                lgg.warning("!!!You are running an outdated version of " + this.getName() + "!!!");
                lgg.warning(result.getNewestVersion() + " > " + this.getDescription().getVersion());
                lgg.warning("Get the newest version here: https://www.spigotmc.org/resources/everything-plugin.107875/history");
                if(booleanMap.get("AdminUtils.UpdateTellToOps")){
                    updateTell = true;
                    lgg.warning("Telling that to all ops");
                }
            }
        });
    }
    @Override
    public void onEnable() {
        if(cancontinue) {
            ccs.sendMessage(Colors.GREEN + "Plugin Enabled");
            CommandsLoader.CommandsLoader(this);
            AdminUtilsLoader.CommandRegister(this);
            CommandTabCompleterHandler.Handler(this);
            MiscLoader.Loader(this);
            MiscLoader.EventLoader(this);
        }
    }

    @Override
    public void onDisable() {
        if (FullDisable) {
            save(pf, pfyml);
            saveConfig();
        }
        ccs.sendMessage(Colors.DARKRED + "Plugin Disabled");
        metrics.shutdown();
    }
}