package it.events;

import it.plugin.Plugin;
import it.utils.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static it.commands.nick.NickHandler.onJoin;
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
        onJoin(e.getPlayer());
        e.joinMessage(ChatPlayerName.join(e.getPlayer()));
        new Motd(e.getPlayer(),plugin);
        save(pf,pfyml);
        if(updateTell && e.getPlayer().isOp()){
            e.getPlayer().sendMessage(plugin.getName() + " can be upgraded to a newer version!");
        }
    }
}
