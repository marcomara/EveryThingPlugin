package it.commands.Invsee;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import it.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class InvListener implements Listener {
    Plugin plugin;
    Inventory dinv;
    Player t, p;

    public InvListener(Plugin plugin, Inventory dinv, Player t, Player p) {
        this.plugin = plugin;
        this.dinv = dinv;
        this.t = t;
        this.p = p;
    }

    @EventHandler
    public void onInvMod1(PlayerInventorySlotChangeEvent ev) {
        if (ev.getPlayer() == t) {
            dinv.setItem(ev.getSlot(), ev.getNewItemStack());
        }
    }
}
