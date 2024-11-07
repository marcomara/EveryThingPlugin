package it.Misc.KnockDown;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static it.plugin.Plugin.intMap;
import static it.plugin.Plugin.plugin;

public class KnockListener implements Listener {
    public static Map<Player, Map<Attribute,Double >> attbs = new HashMap<>();
    public static Map<Player, BukkitTask> down = new HashMap<>();
    boolean prtclb;
    public KnockListener(boolean prtclb){
        this.prtclb = prtclb;
    }
    @EventHandler
    public void Listener(PlayerDeathEvent e) {
        if (down.keySet().contains(e.getPlayer())){
            down.get(e.getPlayer()).cancel();
            down.remove(e.getPlayer());
            onRess(e.getPlayer());
            return;
        }
        e.setCancelled(true);
        onDeath(e.getPlayer());
        e.getPlayer().setPose(Pose.SWIMMING);
        BukkitTask br= new D(e.getPlayer()).runTaskTimer(plugin, 0L, 0L);
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
            if(i==200){
                p.sendMessage(Component.text("10").color(NamedTextColor.GOLD));
            }
            if (i<101&&i>0){
                if (i%20==0){
                    p.sendMessage(Component.text(i/20).color(NamedTextColor.RED));
                }
            }
            if (i==0){
                p.setHealth(0);
            }
            i--;
            p.setPose(Pose.SWIMMING);

        }
    }

    public static void onDeath(Player p){
        p.setHealth(1);
        p.setSaturatedRegenRate(999999999);
        p.setUnsaturatedRegenRate(999999999);
        Map<Attribute, Double> pmap = new HashMap<>();
        pmap.put(Attribute.GENERIC_MOVEMENT_SPEED, p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue());
        pmap.put(Attribute.GENERIC_JUMP_STRENGTH, p.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).getBaseValue());
        attbs.put(p, pmap);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.05);
        p.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.4);
    }
    public static void onRess(Player p){
        Map<Attribute, Double> pmap = attbs.get(p);
        for (Map.Entry<Attribute,Double> e : pmap.entrySet()){
            p.getAttribute(e.getKey()).setBaseValue(e.getValue());
        }
        p.setSaturatedRegenRate(10);
        p.setUnsaturatedRegenRate(80);
        attbs.remove(p);
    }
}
