package it.events.Join;

import it.commands.ResourcePacks.Starter;
import org.bukkit.entity.Player;

import static it.AdminUtility.ClearLag.RemoveChestCrafting.GenReplacer;
import static it.commands.ResourcePacks.Command.sendResourcePack;
import static it.plugin.Plugin.booleanMap;

public class MiscActions {

    public static void MiscActions(Player p){
        /*if(booleanMap.get("ResourcePacks.forcePack")){
            if(Starter.config.get("ForcedPack") != ""){
                sendResourcePack(p, Starter.config.getString("ForcedPack"));
            }
        }*/
        GenReplacer(p);
    }
}
