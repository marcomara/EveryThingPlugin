package it.events.Join;

import org.bukkit.entity.Player;

import static it.plugin.Plugin.pfyml;
import static it.plugin.Plugin.pf;
import static it.utils.SaveUtility.save;

public class SaveData {
    public static void SaveData(Player p){
        pfyml.set(p.getUniqueId() + ".originalName", p.getName());
        save(pf,pfyml);
    }
}
