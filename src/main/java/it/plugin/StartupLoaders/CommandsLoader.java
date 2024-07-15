package it.plugin.StartupLoaders;

import it.AdminUtility.Reload;
import it.commands.ChunkLoader.ChunkLoaderCommand;
import it.commands.PlayersInteractions.Carry;
import it.commands.ResourcePacks.Starter;
import it.commands.Utils.*;
import it.commands.warp.Home;
import it.commands.invsee.invsee;
import it.commands.tpa.Command;
import it.commands.warp.WarpCommand;
import it.commands.ChunkLoader.ChunkLoaderHandler;
import it.plugin.Plugin;

import java.io.File;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.*;

public class CommandsLoader {
    static public void CommandsLoader(Plugin plugin) {
        /*if (plugin.booleanMap.get("Commands.isNickEnabled")) {
            plugin.getCommand("nick").setExecutor(new Nick(plugin));
            plugin.getCommand("skin").setExecutor(new Command(plugin));
            plugin.commands.add("nick");
        } else {plugin.getCommand("nick").setExecutor(executor);plugin.getCommand("skin").setExecutor(executor);}*/
        if(booleanMap.get("ResourcePacks.isEnabled")){
            plugin.getCommand("resourcepackset").setExecutor(new it.commands.ResourcePacks.Command());
            plugin.getCommand("resourcepackset").setTabCompleter(new it.commands.ResourcePacks.Command());
            new Starter(new File(dataFolder , "ResourcePacks"),plugin);
        }else plugin.getCommand("resourcepackset").setExecutor(executor);
        if(booleanMap.get("Misc.isSizeEnabled")){
            plugin.getCommand("setsize").setExecutor(new SetSize());
            commands.add("setsize");
        }else plugin.getCommand("setsize").setExecutor(executor);
        if(booleanMap.get("Commands.isAnvilEnabled")){
            plugin.getCommand("anvil").setExecutor(new Anvil());
        }else plugin.getCommand("anvil").setExecutor(executor);
        if(booleanMap.get("Commands.isInvseeEnabled")){
            plugin.getCommand("invsee").setExecutor(new invsee(plugin));
            commands.add("invsee");
        }else plugin.getCommand("invsee").setExecutor(executor);
        if(booleanMap.get("Commands.isEnderChestEnabled")){
            plugin.getCommand("ec").setExecutor(new EnderChest());
            commands.add("ec");
        }else plugin.getCommand("ec").setExecutor(executor);
        if(booleanMap.get("Commands.isCraftingTableEnabled")){
            plugin.getCommand("ct").setExecutor(new CraftingTable());
        }else plugin.getCommand("ct").setExecutor(executor);
        if (booleanMap.get("Commands.isWarpEnabled")) {
            plugin.getCommand("warp").setExecutor(new WarpCommand());
            commands.add("warp");
        } else plugin.getCommand("warp").setExecutor(executor);
        if (booleanMap.get("Commands.isTPAEnabled") ) {
            tpatimer = plugin.getConfig().getInt("misc.TPATimer");
            plugin.getCommand("tpa").setExecutor(new Command());
            commands.add("tpa");
        } else plugin.getCommand("tpa").setExecutor(executor);
        if (booleanMap.get("Commands.isCarryEnabled") ) {
            plugin.getCommand("carry").setExecutor(new Carry());
            commands.add("carry");
        } else plugin.getCommand("carry").setExecutor(executor);
        if (booleanMap.get("Commands.isCoordsEnabled") ) {
            plugin.getCommand("coords").setExecutor(new CoordinatesCalculator());
            commands.add("coords");
        } else plugin.getCommand("coords").setExecutor(executor);
        if (booleanMap.get("Commands.isSuicideEnabled") ) {
            plugin.getCommand("suicide").setExecutor(new Suicide());
            commands.add("suicide");
        } else plugin.getCommand("suicide").setExecutor(executor);
        if (booleanMap.get("Commands.isRELEnabled") ) {
            plugin.getCommand("rel").setExecutor(new Reload(plugin));
            commands.add("rel");
        } else plugin.getCommand("rel").setExecutor(executor);
        if (booleanMap.get("Commands.isGMEnabled") ) {
            plugin.getCommand("gm").setExecutor(new GameModeCycle());
            commands.add("gm");
        } else plugin.getCommand("gm").setExecutor(executor);
        if (booleanMap.get("Commands.isHomeEnabled") ) {
            plugin.getCommand("home").setExecutor(new Home());
            commands.add("home");
        } else plugin.getCommand("home").setExecutor(executor);
        if (booleanMap.get("Commands.isLDLEnabled") ) {
            plugin.getCommand("ldl").setExecutor(new LastDeathLocation());
            commands.add("ldl");
        } else plugin.getCommand("ldl").setExecutor(executor);
        if (booleanMap.get("Commands.isSendCordsEnabled") ) {
            plugin.getCommand("sendposition").setExecutor(new SendCoordinates());
            commands.add("sendposition");
        } else plugin.getCommand("sendposition").setExecutor(executor);
        if (booleanMap.get("Commands.isChunkLoaderEnabled") ) {
            plugin.getCommand("chunk").setExecutor(new ChunkLoaderCommand());
            CFile = new File(plugin.getDataFolder(), "LoadedChunks.chunks");
            create(CFile);
            CFC = createyml(CFile);
            LoadedChunks = CFC.getStringList("LoadedChunks");
            ChunkLoaderHandler.LoadChunksFromList();
            commands.add("chunk");
        } else plugin.getCommand("chunk").setExecutor(executor);
    }
}