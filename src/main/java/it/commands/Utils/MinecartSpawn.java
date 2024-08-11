package it.commands.Utils;

import org.bukkit.block.data.Rail;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MinecartSpawn implements Listener {
    @EventHandler
    public void listener(PlayerInteractEvent e) {
        if (e.getClickedBlock()!=null &&  e.getPlayer().getInventory().getItemInMainHand().isEmpty() && e.getAction().isRightClick() && e.getClickedBlock().getBlockData() instanceof Rail) {
            for (Entity en : e.getClickedBlock().getChunk().getEntities()) {
                if (en.getType() == EntityType.MINECART) {
                    if (e.getClickedBlock().getLocation().distanceSquared(en.getLocation()) < 3) return;
                }
            }
            Entity m = e.getClickedBlock().getWorld().spawn(e.getClickedBlock().getLocation(), Minecart.class);
            m.setPersistent(false);
            m.setCustomName("temp");
            m.setCustomNameVisible(false);
        }
    }

    @EventHandler
    public void minecartkill(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.MINECART) {
            if (e.getEntity().getCustomName().equals("temp")) {
                e.setCancelled(true);
                e.getEntity().remove();
            }
        }
    }
}
