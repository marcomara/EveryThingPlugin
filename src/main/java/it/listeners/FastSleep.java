package it.listeners;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.HashSet;
import java.util.Set;

public class FastSleep implements Listener {
    private final Plugin plugin;
    public FastSleep(Plugin plugin){
        this.plugin=plugin;
    }
    private final Set<World> sleepingWorlds = new HashSet<>();
    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent e){
        if(e.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK){
            return;
        }
        Player p = e.getPlayer();
        World world = p.getWorld();
        if(!(world.getTime() <= 12542) && world.getPlayers().stream().filter(Player::isSleeping).count()>=((long) world.getPlayers().size() *plugin.getConfig().getInt("misc.FastSleepPercentage")/100)){
            sleepingWorlds.add(world);
            world.setTime(0);
            world.setStorm(false);
            world.setThundering(false);
            Bukkit.broadcastMessage("The night has been skipped");
        }
    }
}
