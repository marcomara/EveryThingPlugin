package it.Misc.KnockDown;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.Misc.KnockDown.KnockListener.down;

public class Reanimate implements Listener {
    Plugin p;

    public static Map<Player, BukkitTask> map = new HashMap<>();
    public static Map<Player, List<Player>> revmap = new HashMap<>();
    static boolean prtclb;

    public Reanimate (Plugin p, boolean prtclb){
        this.p=p;
        this.prtclb=prtclb;
    }

    @EventHandler
    public void ReanimateListener (PlayerToggleSneakEvent e){
        if (down.keySet().contains(e.getPlayer())){
            return;
        }
        List<Entity> el = e.getPlayer().getNearbyEntities(1.5,1,1.5);
        List<Player> pl = new ArrayList<>();
        for (Entity en : el){
            if (en instanceof Player){
                Player p = (Player) en;
                if (down.keySet().contains(p)){
                    pl.add(p);
                }
            }
        }
        if (!pl.isEmpty()){
            revmap.put(e.getPlayer(), pl);
            Counter(e.getPlayer());
        }
    }


    public void Counter( Player r){
        r.sendMessage(Component.text("You are now reanimating nearby players!!").color(NamedTextColor.GREEN));
        for (Player p : revmap.get(r)){
            p.sendMessage(Component.text("You are being reanimated by " + r.getName()).color(NamedTextColor.GREEN));
        }
        BukkitRunnable rn = new C(0,r);
        map.put(r,rn.runTaskTimer(p,0L,2L));
    }

    public static void Revive(Player p){
        map.get(p).cancel();
        List<Entity> el = p.getNearbyEntities(1.5,1,1.5);
        List<Player> pl = new ArrayList<>();
        for (Entity en : el){
            if (en instanceof Player){
                Player dp = (Player) en;
                if (down.keySet().contains(dp)){
                    pl.add(dp);
                }
            }
        }
        p.sendMessage(Component.text("You reanimated nearby players").color(NamedTextColor.GREEN));
        for (Player dp: pl){
                KnockListener.onRess(dp);
                down.get(dp).cancel();
                down.remove(dp);
            dp.sendMessage(Component.text(p.getName() + " reanimated you").color(NamedTextColor.GREEN));
        }
    }

    public static void Abort(Player r){
        map.get(r).cancel();
        r.sendMessage(Component.text("You stopped reanimating!!").color(NamedTextColor.RED));
        for (Player p : revmap.get(r)){
            p.sendMessage(Component.text("You are no longer being revived").color(NamedTextColor.RED));
        }
    }

    public static class C extends BukkitRunnable{

        public static int i;
        public static Player p;

        public C(int i, Player p){
            this.i=i;
            this.p=p;
        }
        @Override
        public void run() {
            if (p.isSneaking()) {
                i++;
                p.getWorld().playSound(p,Sound.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.PLAYERS, 10,1, 5);
            }
            if (i==5){
                Revive(p);
                cancel();
            }
            if (!p.isSneaking()){
                Abort(p);
                cancel();
            }
        }
    }
}
