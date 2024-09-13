package it.plugin.StartupLoaders;

import it.AdminUtility.ClearLag.ClearLagCommand;
import it.BackupUtils.AllWorldsRun;
import it.BackupUtils.BKUPCommand;
import it.Misc.KnockDown.KnockListener;
import it.Misc.KnockDown.Reanimate;
import it.Roles.RolesRegister;
import it.commands.ChunkLoader.ChunkLoaderCommand;
import it.commands.ChunkLoader.ChunkLoaderHandler;
import it.commands.PlayersInteractions.Carry;
import it.commands.PlayersInteractions.FastSit;
import it.commands.PlayersInteractions.Sit;
import it.commands.ResourcePacks.Server.Server;
import it.commands.ResourcePacks.Starter;
import it.commands.TPA.Command;
import it.commands.Utils.BedrockBuild;
import it.commands.Utils.CommandList;
import it.commands.DisabledCommandMessage;
import it.commands.Suggestions;
import it.commands.Economy.Balance;
import it.AdminUtility.Invsee.invsee;
import it.commands.Utils.MinecartSpawn;
import it.commands.XpFarmChunks.Tasks;
import it.commands.XpFarmChunks.XpFarmChunk;
import it.events.Join.Join;
import it.commands.Leash.LeashEvent;
import it.events.Quit;
import it.listeners.Bell;
import it.listeners.FastSleep;
import it.listeners.Misc;
import it.utils.SaveUtility;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.*;
import static it.plugin.Plugin.plugin;

public class MiscLoader {
    public static void Loader() {
        if(booleanMap.get("ResourcePacks.isEnabled")){
            new Starter(new File(dataFolder, "ResourcePacks"), plugin);
        }
        if (booleanMap.get("Misc.MinecartSpawn")){
            plugin.getServer().getPluginManager().registerEvents(new MinecartSpawn(), plugin);
        }
        /*if (booleanMap.get("Worlds.isEnable")){
            plugin.getCommand("world").setExecutor(new Command());
            File conff = new File(dataFolder, "worlds.yml");
            create(conff);
            worlds = YamlConfiguration.loadConfiguration(conff);
            if(worlds.contains("worlds")){
                for(String str : worlds.getStringList("worlds")){
                    Bukkit.createWorld(new WorldCreator(str));
                }
            }else worlds.set("worlds", new ArrayList<String>());
            save(conff, worlds);
        }*/
        if (booleanMap.get("KnockDown.isEnabled")){
            boolean i=false;
            Bukkit.getPluginManager().registerEvents(new KnockListener(i), plugin);
            Bukkit.getPluginManager().registerEvents(new Reanimate(i), plugin);
        }
        if (booleanMap.get("ClearLag.EnableItemsCheck")){
            plugin.getCommand("clearlag").setExecutor(new ClearLagCommand());
        }
        if(booleanMap.get("Backup.Enabled")){
            plugin.getCommand("backup").setExecutor(new BKUPCommand());
            plugin.getCommand("backup").setTabCompleter(new BKUPCommand.BKUPTab());
            bkfolder = new File(dataFolder.getParentFile().getParentFile(), "Backups");
            if(!bkfolder.exists()){
                bkfolder.mkdir();
            }
            if(booleanMap.get("Backup.AutomaticBackups")) {
                plugintasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                            lgg.warning("Backup started");
                            new AllWorldsRun(bkfolder).runTaskAsynchronously(plugin);
                        }, 20L * 60 * intMap.get("Backup.BKTimer"),
                        20L * 60 * intMap.get("Backup.BKTimer")));
            }
        }
        if (booleanMap.get("Misc.isTPAEnabled")) {
            plugin.getCommand("tpa").setExecutor(new Command(plugin.getConfig().getInt("Misc.TPATimer")));
            plugin.getCommand("tpa").setTabCompleter(new Command.TPACommandCompleter());
            commands.add("tpa");
        } else plugin.getCommand("tpa").setExecutor(executor);
        if (booleanMap.get("Chunks.ChunkLoader")) {
            plugin.getCommand("chunk").setExecutor(new ChunkLoaderCommand());
            plugin.getCommand("chunk").setTabCompleter(new ChunkLoaderCommand.ChunkLoaderTab());
            CFile = new File(plugin.getDataFolder(), "LoadedChunks.chunks");
            create(CFile);
            CFC = SaveUtility.createyml(CFile);
            LoadedChunks = CFC.getStringList("LoadedChunks");
            ChunkLoaderHandler.LoadChunksFromList();
            commands.add("chunk");
        } else plugin.getCommand("chunk").setExecutor(executor);
        if (booleanMap.get("Chunks.XpFarmChunks")){
            plugin.getCommand("xpfarm").setExecutor(new XpFarmChunk());
            plugin.getCommand("xpfarm").setTabCompleter(new XpFarmChunk.XpFarmChunkTabCompleter());
            plugin.getServer().getPluginManager().registerEvents(new Tasks(),plugin);
            plugintasks.add(new Tasks().runTaskTimerAsynchronously(plugin, 0L, 5L));
        }else plugin.getCommand("xpfarm").setExecutor(executor);
        if(booleanMap.get("AdminUtils.isInvseeEnabled")){
            plugin.getCommand("invsee").setExecutor(new invsee());
            plugin.getCommand("invsee").setTabCompleter(new invsee.InvSeeTab());
            commands.add("invsee");
        }else plugin.getCommand("invsee").setExecutor(executor);
        if (booleanMap.get("Misc.isFastSleepEnabled")) {
            for (World w : Bukkit.getWorlds() ){
                Bukkit.getPluginManager().registerEvents(new FastSleep(), plugin);
                w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, intMap.get("Misc.FastSleepPercentage"));
            }
        }
        if (booleanMap.get("Misc.CommandsList")) {
            plugin.getCommand("commands").setExecutor(new CommandList());
            commands.add("commands");
        }
        if (booleanMap.get("Misc.isLeashEnabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new LeashEvent(), plugin);
        }
        if (booleanMap.get("Economy.isEnabled")) {
            plugin.getCommand("balance").setExecutor(new Balance());
            plugin.getCommand("balance").setTabCompleter(new Balance());
            ecotype = plugin.getConfig().getString("economy.symbol");
            commands.add("balance");
        } else {
            plugin.getCommand("balance").setExecutor(new DisabledCommandMessage());
        }
        if (booleanMap.get("Misc.areSuggestionsEnabled")) {
            plugin.getCommand("suggestion").setExecutor(new Suggestions());
            plugin.getCommand("suggestion").setTabCompleter(new Suggestions());
            commands.add("suggestion");
        } else {
            plugin.getCommand("suggestion").setExecutor(new DisabledCommandMessage());
        }
        if(booleanMap.get("Misc.isSitEnabled")){
            plugin.getCommand("sit").setExecutor(new Sit());
            plugin.getCommand("sit").setTabCompleter(new Sit());
            Bukkit.getServer().getPluginManager().registerEvents(new FastSit(),plugin);
        }
        if(booleanMap.get("Commands.isCarryEnabled")){
            Bukkit.getServer().getPluginManager().registerEvents(new Carry(), plugin);
        }
    }

    public static void EventLoader() {
        if (booleanMap.get("Roles.Enable")){
            plugin.getCommand("role").setExecutor(new it.Roles.Command());
            RolesRegister.RolesRegister();
        }//else {
            Bukkit.getServer().getPluginManager().registerEvents(new Join(), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new Quit(), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new Misc(), plugin);
        //}
        if(booleanMap.get("Misc.isBellEnabled")){
            Bukkit.getServer().getPluginManager().registerEvents(new Bell(), plugin);
        }
        if(booleanMap.get("Misc.isBedrockBridgeBuildEnabled")){
            Bukkit.getServer().getPluginManager().registerEvents(new BedrockBuild(), plugin);
        }
    }
    public static void Stop(){
        if(booleanMap.get("ResourcePacks.isEnabled")&&Server.isActive){
            Server.terminate();
        }
        for (BukkitTask t : plugintasks){
            t.cancel();
        }
    }
}
