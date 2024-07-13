package it.plugin.StartupLoaders;

import it.AdminUtility.*;
import it.commands.PluginInfo;
import it.plugin.Plugin;

import static it.plugin.Plugin.*;

public class AdminUtilsLoader {
    static public void CommandRegister(Plugin p){
        String s = "AdminUtils.Commands.";
        if(booleanMap.get(s+"EntityList")) {
            p.getCommand("el").setExecutor(new EntityList());
            commands.add("el");
        }else p.getCommand("el").setExecutor(executor);
        if(booleanMap.get(s+"RemoveMinecart")){
            p.getCommand("minecart-cleaner").setExecutor(new MinecartClear());
            commands.add("minecart-cleaner");
        }else p.getCommand("minecart-cleaner").setExecutor(executor);
        if(booleanMap.get(s+"RemoveProjectiles")){
            p.getCommand("projectile-cleaner").setExecutor(new ProjectileClear());
            commands.add("projectile-cleaner");
        }else p.getCommand("projectile-cleaner").setExecutor( executor);
        p.getCommand("info").setExecutor(new PluginInfo());
        if(booleanMap.get(s+"RemoveStands")){
            p.getCommand("stand-cleaner").setExecutor(new StandClear());
            commands.add("stand-cleaner");
        }else p.getCommand("stand-cleaner").setExecutor( executor);

    }
}
