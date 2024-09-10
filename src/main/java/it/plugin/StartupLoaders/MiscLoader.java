package it.plugin.StartupLoaders;

import it.AdminUtility.ClearLag.ClearLagCommand;
import it.BackupUtils.AllWorldsRun;
import it.BackupUtils.BKUPCommand;
import it.Misc.KnockDown.KnockListener;
import it.Misc.KnockDown.Reanimate;
import it.commands.ChunkLoader.ChunkLoaderCommand;
import it.commands.ChunkLoader.ChunkLoaderHandler;
import it.commands.PlayersInteractions.Carry;
import it.commands.PlayersInteractions.FastSit;
import it.commands.PlayersInteractions.Sit;
import it.commands.ResourcePacks.Server.Server;
import it.commands.ResourcePacks.Starter;
import it.commands.Roles.RolesLoader;
import it.commands.TPA.Command;
import it.commands.Utils.BedrockBuild;
import it.commands.Utils.CommandList;
import it.commands.DisabledCommandMessage;
import it.commands.Suggestions;
import it.commands.Economy.Balance;
import it.AdminUtility.Invsee.invsee;
import it.commands.Utils.MinecartSpawn;
import it.events.Join;
import it.commands.Leash.LeashEvent;
import it.events.Quit;
import it.listeners.Bell;
import it.listeners.FastSleep;
import it.listeners.Misc;
import it.commands.Leash.CollisionTeam;
import it.plugin.ProtocolLibOn;
import it.utils.SaveUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;

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
            Bukkit.getPluginManager().registerEvents(new KnockListener(plugin,i), plugin);
            Bukkit.getPluginManager().registerEvents(new Reanimate(plugin,i), plugin);
        }
        if (booleanMap.get("ClearLag.EnableItemsCheck")){
            plugin.getCommand("clearlag").setExecutor(new ClearLagCommand());
        }
        if(booleanMap.get("Backup.Enabled")){
            plugin.getCommand("backup").setExecutor(new BKUPCommand(plugin));
            plugin.getCommand("backup").setTabCompleter(new BKUPCommand.BKUPTab());
            bkfolder = new File(dataFolder.getParentFile().getParentFile(), "Backups");
            if(!bkfolder.exists()){
                bkfolder.mkdir();
            }
            if(booleanMap.get("Backup.AutomaticBackups")) {
                Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                    lgg.warning("Backup started");
                    new AllWorldsRun(bkfolder).runTaskAsynchronously(plugin);
                }, 20L * 60 * intMap.get("Backup.BKTimer"),
                        20L * 60 * intMap.get("Backup.BKTimer"));
            }
        }
        if (booleanMap.get("Misc.isTPAEnabled")) {
            plugin.getCommand("tpa").setExecutor(new Command(plugin.getConfig().getInt("Misc.TPATimer")));
            plugin.getCommand("tpa").setTabCompleter(new Command.TPACommandCompleter());
            commands.add("tpa");
        } else plugin.getCommand("tpa").setExecutor(executor);
        if (booleanMap.get("Misc.isChunkLoaderEnabled")) {
            plugin.getCommand("chunk").setExecutor(new ChunkLoaderCommand());
            plugin.getCommand("chunk").setTabCompleter(new ChunkLoaderCommand.ChunkLoaderTab());
            CFile = new File(plugin.getDataFolder(), "LoadedChunks.chunks");
            create(CFile);
            CFC = SaveUtility.createyml(CFile);
            LoadedChunks = CFC.getStringList("LoadedChunks");
            ChunkLoaderHandler.LoadChunksFromList();
            commands.add("chunk");
        } else plugin.getCommand("chunk").setExecutor(executor);
        if(booleanMap.get("AdminUtils.isInvseeEnabled")){
            plugin.getCommand("invsee").setExecutor(new invsee(plugin));
            plugin.getCommand("invsee").setTabCompleter(new invsee.InvSeeTab());
            commands.add("invsee");
        }else plugin.getCommand("invsee").setExecutor(executor);
        if (booleanMap.get("Misc.isFastSleepEnabled")) {
            for (World w : Bukkit.getWorlds() ){
                Bukkit.getPluginManager().registerEvents(new FastSleep(plugin), plugin);
                w.setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, intMap.get("Misc.FastSleepPercentage"));
            }
        }
        if (booleanMap.get("Misc.CommandsList")) {
            plugin.getCommand("commands").setExecutor(new CommandList(plugin));
            commands.add("commands");
        }
        if (booleanMap.get("Misc.isLeashEnabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new LeashEvent(plugin), plugin);
            team = new CollisionTeam();
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
            rolesl = new ArrayList<>();
            roles = Bukkit.getScoreboardManager().getNewScoreboard();
            roles.registerNewTeam("Owner")
                    .prefix(Component.text("[").color(NamedTextColor.GRAY)
                            .append(Component.text("OWNER").color(NamedTextColor.DARK_PURPLE))
                            .append(Component.text("] ").color(NamedTextColor.GRAY)));
            plugin.getCommand("role").setExecutor(new it.commands.Roles.Command());
            try {
                new RolesLoader();
            }catch (Exception e){
                e.printStackTrace();
            }
            Bukkit.getServer().getPluginManager().registerEvents(new it.commands.Roles.ListenerMod.Join(plugin), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new it.commands.Roles.ListenerMod.Quit(), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new it.commands.Roles.ListenerMod.Misc(), plugin);
        }else {
            Bukkit.getServer().getPluginManager().registerEvents(new Join(plugin), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new Quit(plugin), plugin);
            Bukkit.getServer().getPluginManager().registerEvents(new Misc(), plugin);
        }
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
    }
}
