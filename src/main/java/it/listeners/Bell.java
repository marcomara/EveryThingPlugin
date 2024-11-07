package it.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class Bell implements Listener {
    @EventHandler
    public void RightClick(PlayerInteractEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.BELL&&e.getAction().isRightClick()&&e.getClickedBlock()==null){
            e.getPlayer().getWorld().playSound(e.getPlayer(), Sound.BLOCK_BELL_USE, 10,1);
        }
    }
}
