package it.plugin.StartupLoaders;

import it.commands.PlayersInteractions.FastSit;
import it.commands.PlayersInteractions.Runner;
import it.commands.PlayersInteractions.Sit;
import it.commands.Utils.CommandList;
import it.commands.DisabledCommandMessage;
import it.commands.Suggestions;
import it.commands.economy.Balance;
import it.events.Join;
import it.commands.leash.LeashEvent;
import it.events.Quit;
import it.listeners.Bell;
import it.listeners.FastSleep;
import it.listeners.Misc;
import it.plugin.Plugin;
import it.commands.leash.CollisionTeam;
import org.bukkit.scheduler.BukkitRunnable;

import static it.plugin.Plugin.*;

public class MiscLoader {
    public static void Loader(Plugin plugin) {
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
}
