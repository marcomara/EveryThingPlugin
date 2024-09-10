package it.plugin.StartupLoaders;

import it.AdminUtility.ClearLag.EntityList;
import it.AdminUtility.ClearLag.MinecartClear;
import it.AdminUtility.ClearLag.ProjectileClear;
import it.AdminUtility.ClearLag.StandClear;
import it.AdminUtility.OnTime;
import it.commands.PluginInfo;
import org.bukkit.scheduler.BukkitTask;

import static it.plugin.Plugin.*;

public class AdminUtilsLoader {
    static public void CommandRegister(){
        String s = "AdminUtils.Commands.";
        BukkitTask timer = timerc.runTaskTimer(plugin,0L,20L);
        plugin.getCommand("ontime").setExecutor(new OnTime());
        if(booleanMap.get(s+"EntityList")) {
            plugin.getCommand("el").setExecutor(new EntityList());
            plugin.getCommand("el").setTabCompleter(new EntityList());
            commands.add("el");
        }else plugin.getCommand("el").setExecutor(executor);
        if(booleanMap.get(s+"RemoveMinecart")){
            plugin.getCommand("minecart-cleaner").setExecutor(new MinecartClear());
            plugin.getCommand("minecart-cleaner").setTabCompleter(new MinecartClear());
            commands.add("minecart-cleaner");
        }else plugin.getCommand("minecart-cleaner").setExecutor(executor);
        if(booleanMap.get(s+"RemoveProjectiles")){
            plugin.getCommand("projectile-cleaner").setExecutor(new ProjectileClear());
            plugin.getCommand("projectile-cleaner").setExecutor(new ProjectileClear());
            commands.add("projectile-cleaner");
        }else plugin.getCommand("projectile-cleaner").setExecutor( executor);
        plugin.getCommand("info").setExecutor(new PluginInfo());
        plugin.getCommand("info").setTabCompleter(new PluginInfo());
        if(booleanMap.get(s+"RemoveStands")){
            plugin.getCommand("stand-cleaner").setExecutor(new StandClear());
            plugin.getCommand("stand-cleaner").setExecutor(new StandClear());
            commands.add("stand-cleaner");
        }else plugin.getCommand("stand-cleaner").setExecutor( executor);
    }
}
