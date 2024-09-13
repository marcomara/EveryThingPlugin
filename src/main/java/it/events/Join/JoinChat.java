package it.events.Join;

import it.utils.ChatUtils;
import it.utils.Motd;
import org.bukkit.event.player.PlayerJoinEvent;

import static it.plugin.Plugin.plugin;
import static it.plugin.Plugin.updateTell;

public class JoinChat {
    public static void JoinChat(PlayerJoinEvent e) throws Exception {
        e.joinMessage(ChatUtils.join(e.getPlayer()));
        new Motd(e.getPlayer(),plugin);
        if(updateTell && e.getPlayer().hasPermission("broadcast.op")){
            e.getPlayer().sendMessage(plugin.getName() + " can be upgraded to a newer version!");
        }
    }
}
