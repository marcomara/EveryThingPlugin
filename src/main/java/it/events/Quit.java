package it.events;

import it.plugin.Plugin;
import it.utils.ChatPlayerName;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener{
    private final Plugin plugin;
    public Quit(Plugin plugin){
        this.plugin=plugin;
    }
    @EventHandler
    public void onquit(PlayerQuitEvent e) {
        e.quitMessage(ChatPlayerName.quit(e.getPlayer()));
    }
}