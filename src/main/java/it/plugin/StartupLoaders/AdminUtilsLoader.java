package it.plugin.StartupLoaders;

import it.AdminUtility.*;
import it.commands.PluginInfo;
import it.plugin.Plugin;

import static it.plugin.Plugin.*;

public class AdminUtilsLoader {
    static public void CommandRegister(){
        String s = "AdminUtils.Commands.";
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
