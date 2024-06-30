package it.plugin.StartupLoaders;

import it.AdminUtility.EntityList;
import it.AdminUtility.MinecartClear;
import it.AdminUtility.ProjectileClear;
import it.AdminUtility.StandClear;
import it.commands.PluginInfo;
import it.plugin.Plugin;

public class AdminUtilsLoader {
    static public void CommandRegister(Plugin p){

        p.getCommand("el").setExecutor(new EntityList());
        p.getCommand("minecart-cleaner").setExecutor(new MinecartClear());
        p.getCommand("info").setExecutor(new PluginInfo());
        p.getCommand("projectile-cleaner").setExecutor(new ProjectileClear());
        p.getCommand("stand-cleaner").setExecutor(new StandClear());
    }
}
