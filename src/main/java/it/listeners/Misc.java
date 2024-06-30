package it.listeners;

import it.utils.ChatPlayerName;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Misc implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Bukkit.getServer().broadcast(ChatPlayerName.msg(event.getPlayer(), event.getMessage()));
    }
}
