package it.Misc.KnockDown;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static it.plugin.Plugin.intMap;

public class KnockListener implements Listener {
    public static Map<Player, BukkitTask> down = new HashMap<>();
    Plugin p;
    public KnockListener(Plugin p){
        this.p = p;
    }
    @EventHandler
    public void Listener(PlayerDeathEvent e) {
        if (down.keySet().contains(e.getPlayer())){
            down.get(e.getPlayer()).cancel();
            down.remove(e.getPlayer());
            e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.10000000149011612);
            e.getPlayer().getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.41999998688697815);
            e.getPlayer().setSaturatedRegenRate(10);
            e.getPlayer().setUnsaturatedRegenRate(80);
            return;
        }
        e.setCancelled(true);
        e.getPlayer().setHealth(1);
        e.getPlayer().setPose(Pose.SWIMMING);
        e.getPlayer().setSaturatedRegenRate(999999999);
        e.getPlayer().setUnsaturatedRegenRate(999999999);
        e.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.05);
        e.getPlayer().getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.4);
        BukkitTask br = new D(e.getPlayer()).runTaskTimer(p,0L,1L);
        down.put(e.getPlayer(), br);
    }
    public class D extends BukkitRunnable{
        Player p;
        int i = 20*intMap.get("KnockDown.Timer");

        public D (Player p){
            this.p=p;
        }
        @Override
        public void run() {
            if (i==0){
                p.setHealth(0);
            }
            i--;
            p.setPose(Pose.SWIMMING);
        }
    }
}
