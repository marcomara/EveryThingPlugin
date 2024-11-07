package it.plugin;

import it.AdminUtility.ClearLag.RemoveChestCrafting;
import it.AdminUtility.Freeze;
import it.WorldUtils.WorldSecurity.AreaClaim.AreaClaim;
import it.WorldUtils.Lootables.ChestRefiller;
import it.WorldUtils.Lootables.PersonalLoot;
import it.commands.DisabledCommandMessage;
import it.commands.Misc.Jumpscare;
import it.commands.Skin.SkinUpdate;
import it.plugin.StartupLoaders.*;
import it.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import it.utils.Metrics;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import static it.plugin.ConfigLoader.ValueLoader.*;
import static it.utils.SaveUtility.*;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    public static List<BukkitTask> plugintasks = new ArrayList<>();
    public boolean cancontinue = true;
    public static File pf;
    public static FileConfiguration pfyml;
    public static String ecotype;
    public static File CFile;
    public static FileConfiguration CFC;
    public static List<String> LoadedChunks = new ArrayList<>();
    public static List<String> commands = new ArrayList<>();
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
    public static Plugin plugin;

    @NotNull private static String APIV = "1.21";

    //public static DataBase db;
    public static File dbfile;

    @Override
    public void onLoad() {
        if (getPluginMeta().getAPIVersion() != null) {
            APIV = getPluginMeta().getAPIVersion();
        }
        if (!getServer().getMinecraftVersion().startsWith(APIV)){
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().warning("***MINECRAFT VERSION NOT SUPPORTED***");
            getLogger().warning("Check the supported versions here:");
            getLogger().warning("https://www.spigotmc.org/resources/everything-plugin.107875");
            Bukkit.getPluginManager().disablePlugin(this);
            cancontinue = false;
            return;
        }
        plugin = this;
        dataFolder = getDataFolder();
        ver = getPluginMeta().getVersion();
        lgg = getLogger();
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
        /*dbfile = new File(dataFolder, "database.db");
        if (!dbfile.exists()){
            try {
                dbfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            db = new DataBase(dbfile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onEnable() {
        if (cancontinue) {
            lgg.log(Level.INFO, "Enabled");
            MiscLoader.Loader();
            MiscLoader.EventLoader();
            AdminUtilsLoader.CommandRegister();
            CommandsLoader.CommandsLoader();
            getCommand("asjumpscare").setExecutor(new Jumpscare());
            if (booleanMap.get("Misc.enableSkinRefresher")) {
                getCommand("skinupdate").setExecutor(new SkinUpdate());
            }
            if (booleanMap.get("AdminUtils.enableAreas")){
                getCommand("claimarea").setExecutor(new AreaClaim());
                getCommand("claimarea").setTabCompleter(new AreaClaim.AreaClaimTC());
            }
            if (booleanMap.get("AdminUtils.canUseFreeze")){
                getCommand("freeze").setExecutor(new Freeze());
            }
            if (!booleanMap.get("AdminUtils.areChestsEnabled")){
                RemoveChestCrafting.a();
            }
            if (booleanMap.get("World.doChestRefill")){
                Bukkit.getPluginManager().registerEvents(new ChestRefiller(getConfig().getLong("World.chestRefillTimer")), this);
            }
            if (booleanMap.get("World.doPersonalChestLoot")){
                Bukkit.getPluginManager().registerEvents(new PersonalLoot(), this);
            }
        }
    }

    @Override
    public void onDisable() {
        if (cancontinue) {
            MiscLoader.Stop();
            lgg.log(Level.INFO, "Disabled");
            metrics.shutdown();
        }
    }
}