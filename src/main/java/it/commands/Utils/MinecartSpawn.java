package it.commands.Utils;

import org.bukkit.block.data.Rail;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

import java.util.ArrayList;
import java.util.List;

public class MinecartSpawn implements Listener {
    List<Integer> minecarts = new ArrayList<>();
    @EventHandler
    public void listener(PlayerInteractEvent e) {
        if (e.getClickedBlock()!=null &&  e.getPlayer().getInventory().getItemInMainHand().isEmpty() && e.getAction().isRightClick() && e.getClickedBlock().getBlockData() instanceof Rail) {
            for (Entity en : e.getClickedBlock().getChunk().getEntities()) {
                if (en.getType() == EntityType.MINECART) {
                    if (e.getClickedBlock().getLocation().distanceSquared(en.getLocation()) < 3) return;
                }
            }
            Minecart m = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getLocation(), Minecart.class);
            m.setPersistent(false);
            m.addPassenger(e.getPlayer());
            m.setMaxSpeed(1000);
            m.setSilent(true);
            minecarts.add(m.getEntityId());
        }
    }
    @EventHandler
    public void minecartkill(VehicleDestroyEvent e) {
        if (e.getVehicle() instanceof Minecart m) {
            if (minecarts.contains(m.getEntityId())) {
                e.setCancelled(true);
                m.remove();
                minecarts.remove(minecarts.indexOf(m.getEntityId()));
            }
        }
    }
}
