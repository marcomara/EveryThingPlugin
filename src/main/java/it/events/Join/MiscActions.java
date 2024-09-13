package it.events.Join;

import it.commands.ResourcePacks.Starter;
import org.bukkit.entity.Player;

import static it.commands.ResourcePacks.Command.sendResourcePack;
import static it.plugin.Plugin.booleanMap;

public class MiscActions {

    public static void MiscActions(Player p){
        if(booleanMap.get("ResourcePacks.forcePack")){
            if(Starter.config.get("ForcedPack") != null){
                sendResourcePack(p, Starter.config.getString("ForcedPack"));
            }
        }
    }
}
