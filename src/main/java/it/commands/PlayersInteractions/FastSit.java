package it.commands.PlayersInteractions;

import org.bukkit.block.data.type.Slab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static it.commands.PlayersInteractions.Sit.SlimeSit;

public class FastSit implements Listener {

    @EventHandler
    public void FastSitListener(PlayerInteractEvent e){
        if(e.getAction().isRightClick()){
            if(e.getClickedBlock()!=null&&e.getClickedBlock().getBlockData()instanceof Slab) {
                if(e.getPlayer().isSneaking()){
                    return;
                }
                SlimeSit(e.getPlayer(), e.getClickedBlock(), true);
            }
        }

    }
}
