package it.commands.PlayersInteractions;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import static it.commands.PlayersInteractions.Sit.SlimeSit;

public class FastSit implements Listener {
    @EventHandler
    public void FastSitListener(PlayerInteractEvent e){
        if(e.getAction().isRightClick()){
            if(e.getClickedBlock()!=null) {
                BlockData bd = e.getClickedBlock().getBlockData();
                if(bd.getMaterial() == Material.STONECUTTER){
                    e.setCancelled(true);
                    if (e.getPlayer().isSneaking()) {
                        return;
                    }
                    SlimeSit(e.getPlayer(), e.getClickedBlock());
                }
                if((bd instanceof Slab&&((Slab)bd).getType() == Slab.Type.BOTTOM)||bd  instanceof Stairs) {
                    if (e.getPlayer().isSneaking()) {
                        return;
                    }
                    SlimeSit(e.getPlayer(), e.getClickedBlock());
                }
            }
        }

    }

    @EventHandler
    public void ExitEvent(PlayerToggleSneakEvent e){
        if (e.getPlayer().getVehicle()!=null) {
            Entity en = e.getPlayer().getVehicle();
            en.getPassengers().remove(e.getPlayer());
            if (en.getType() == EntityType.SLIME && en.isInvisible()) {
                en.remove();
            }
        }
    }
}
