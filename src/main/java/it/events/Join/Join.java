package it.events.Join;

import it.commands.Nick.NickHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static it.events.Join.SaveData.SaveData;
import static it.events.Join.JoinChat.JoinChat;
import static it.events.Join.MiscActions.MiscActions;

public class Join implements Listener{
    @EventHandler
    public void onjoin(PlayerJoinEvent e) throws Exception{
        SaveData(e.getPlayer());
        JoinChat(e);
        MiscActions(e.getPlayer());
        NickHandler.onJoin(e.getPlayer());
    }
}
