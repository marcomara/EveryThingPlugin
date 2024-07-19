package it.plugin.StartupLoaders;

import it.commands.ChunkLoader.ChunkLoaderCommand;
import it.commands.ChunkLoader.ChunkLoaderHandler;
import it.commands.PlayersInteractions.FastSit;
import it.commands.PlayersInteractions.Runner;
import it.commands.PlayersInteractions.Sit;
import it.commands.ResourcePacks.Server.Server;
import it.commands.ResourcePacks.Starter;
import it.commands.Utils.CommandList;
import it.commands.DisabledCommandMessage;
import it.commands.Suggestions;
import it.commands.Worlds.PlayerWorldData;
import it.commands.economy.Balance;
import it.commands.invsee.invsee;
import it.commands.nick.Nick;
import it.commands.warp.WarpCommand;
import it.events.Join;
import it.commands.leash.LeashEvent;
import it.events.Quit;
import it.listeners.Bell;
import it.listeners.FastSleep;
import it.listeners.Misc;
import it.plugin.Plugin;
import it.commands.leash.CollisionTeam;
import it.utils.SaveUtility;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.logging.Level;

import static it.plugin.Plugin.*;

public class MiscLoader {
    public static void Loader(Plugin plugin) {
        if(booleanMap.get("ResourcePacks.isEnabled")){
            new Starter(new File(dataFolder, "ResourcePacks"), plugin);
        }
        if (booleanMap.get("Worlds.isEnable")){
            plugin.getCommand("world").setExecutor(new it.commands.Worlds.Command());
        }
        if(booleanMap.get("Commands.isNickEnabled")){
            plugin.getCommand("nick").setExecutor(new Nick(plugin));
            plugin.getCommand("nick").setTabCompleter(new Nick(plugin));
            commands.add("nick");
        }
        if (booleanMap.get("Commands.isTPAEnabled")) {
            tpatimer = plugin.getConfig().getInt("misc.TPATimer");
            plugin.getCommand("tpa").setExecutor(new it.commands.tpa.Command());
            commands.add("tpa");
        } else plugin.getCommand("tpa").setExecutor(Plugin.executor);
        if (booleanMap.get("Commands.isChunkLoaderEnabled")) {
            plugin.getCommand("chunk").setExecutor(new ChunkLoaderCommand());
            CFile = new File(plugin.getDataFolder(), "LoadedChunks.chunks");
            SaveUtility.create(CFile);
            CFC = SaveUtility.createyml(CFile);
            LoadedChunks = CFC.getStringList("LoadedChunks");
            ChunkLoaderHandler.LoadChunksFromList();
            commands.add("chunk");
        } else plugin.getCommand("chunk").setExecutor(Plugin.executor);
        if(booleanMap.get("Commands.isInvseeEnabled")){
            plugin.getCommand("invsee").setExecutor(new invsee(plugin));
            plugin.getCommand("invsee").setTabCompleter(new invsee(plugin));
            commands.add("invsee");
        }else plugin.getCommand("invsee").setExecutor(executor);
        if (booleanMap.get("Misc.FastSleep")) {
            plugin.getServer().getPluginManager().registerEvents(new FastSleep(plugin), plugin);
        }
        if (booleanMap.get("Misc.CommandsList")) {
            plugin.getCommand("commands").setExecutor(new CommandList(plugin));
            commands.add("commands");
        }
        if (booleanMap.get("Misc.isLeashEnabled")) {
            plugin.getServer().getPluginManager().registerEvents(new LeashEvent(plugin), plugin);
            team = new CollisionTeam();
        }
        if (booleanMap.get("Economy.isEnabled")) {
            plugin.getCommand("balance").setExecutor(new Balance());
            ecotype = plugin.getConfig().getString("economy.symbol");
            commands.add("balance");
        } else {
            plugin.getCommand("balance").setExecutor(new DisabledCommandMessage());
        }
        if (booleanMap.get("Misc.areSuggestionsEnabled")) {
            plugin.getCommand("suggestion").setExecutor(new Suggestions());
            commands.add("suggestion");
        } else {
            plugin.getCommand("suggestion").setExecutor(new DisabledCommandMessage());
        }
        if(booleanMap.get("Misc.isSitEnabled")){
            plugin.getCommand("sit").setExecutor(new Sit());
            BukkitRunnable b = new Runner();
            plugin.getServer().getPluginManager().registerEvents(new FastSit(),plugin);
            b.runTaskTimer(plugin,200L,20L);
        }
    }

    public static void EventLoader(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new Join(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Quit(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Misc(), plugin);
        if(booleanMap.get("Misc.isBellEnabled")){
            plugin.getServer().getPluginManager().registerEvents(new Bell(), plugin);
        }
    }
    public static void Stop(Plugin plugin){
        if(booleanMap.get("ResourcePacks.isEnabled")){
            Server.terminate();
        }
    }
}
