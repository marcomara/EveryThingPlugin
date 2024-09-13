package it.events;

import it.utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener{
    @EventHandler
    public void onquit(PlayerQuitEvent e) {
        e.quitMessage(ChatUtils.quit(e.getPlayer()));
    }
}