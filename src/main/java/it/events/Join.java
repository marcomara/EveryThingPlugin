package it.events;

import it.commands.ResourcePacks.Starter;
import it.plugin.Plugin;
import it.utils.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.save;

public class Join implements Listener{
    private final Plugin plugin;
    public Join(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onjoin(PlayerJoinEvent e) throws Exception{
        pfyml.set(e.getPlayer().getUniqueId() + ".originalName", e.getPlayer().getName());
        //onJoin(e.getPlayer());
        e.joinMessage(ChatUtils.join(e.getPlayer()));
        new Motd(e.getPlayer(),plugin);
        save(pf,pfyml);
        if(updateTell && e.getPlayer().hasPermission("broadcast.op")){
            e.getPlayer().sendMessage(plugin.getName() + " can be upgraded to a newer version!");
        }
        if(booleanMap.get("ResourcePacks.forcePack")){
            if(Starter.config.get("ForcedPack") != null){
                it.commands.ResourcePacks.Command.sendResourcePack(e.getPlayer(), Starter.config.getString("ForcedPack"));
            }
        }
    }
}
