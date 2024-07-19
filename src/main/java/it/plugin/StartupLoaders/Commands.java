package it.plugin.StartupLoaders;

import it.commands.PlayersInteractions.*;
import it.commands.Utils.*;
import it.AdminUtility.*;
import it.commands.warp.*;
import it.commands.ResourcePacks.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    public static Map<String, Class<? extends CommandExecutor>> CommandsMap() {
        Map<String, Class<? extends CommandExecutor>> toreturn = new HashMap<>();
        toreturn.putAll(Map.of("warp", WarpCommand.class,
                "resourcepackset", Command.class,
                "setsize", SetSize.class,
                "anvil", Anvil.class));
        toreturn.putAll(Map.of(
                "ct", CraftingTable.class,
                "ec", EnderChest.class,
                "suicide", Suicide.class,
                "rel", Reload.class,
                "gm", GameModeCycle.class));
        toreturn.putAll(Map.of(
                "home", Home.class,
                "carry", Carry.class,
                "ldl", LastDeathLocation.class,
                "sendposition", SendCoordinates.class,
                "coords", CoordinatesCalculator.class));
        return toreturn;
    }
    public static Map<String, Class<? extends TabCompleter>> TabsMap() {
        Map<String, Class<? extends TabCompleter>> toreturn = new HashMap<>();
        toreturn.putAll(Map.of("warp", WarpCommand.class,
                "resourcepackset", Command.class,
                "setsize", SetSize.class,
                "anvil", Anvil.class));
        toreturn.putAll(Map.of(
                "ct", CraftingTable.class,
                "ec", EnderChest.class,
                "suicide", Suicide.class,
                "rel", Reload.class,
                "gm", GameModeCycle.class));
        toreturn.putAll(Map.of(
                "home", Home.class,
                "carry", Carry.class,
                "ldl", LastDeathLocation.class,
                "sendposition", SendCoordinates.class,
                "coords", CoordinatesCalculator.class));
        return toreturn;
    }
    public static Map<String, String> ConfigMap(){
        Map<String,String> toreturn = new HashMap<>();
        toreturn.putAll(Map.of("warp", "Commands.isWarpEnabled",
                "resourcepackset", "ResourcePacks.isEnabled",
                "setsize", "Misc.isSizeEnabled",
                "anvil", "Commands.isAnvilEnabled"));
        toreturn.putAll(Map.of("ct", "Commands.isCraftingTableEnabled",
                "ec", "Commands.isEnderChestEnabled",
                "suicide", "Commands.isSuicideEnabled",
                "rel", "Commands.isRELEnabled",
                "gm", "Commands.isGMEnabled"));
        toreturn.putAll(Map.of("home", "Commands.isHomeEnabled",
                "carry", "Commands.isCarryEnabled",
                "ldl", "Commands.isLDLEnabled",
                "sendposition", "Commands.isSendCordsEnabled",
                "coords", "Commands.isCoordsEnabled"));
        return toreturn;
    }
}
