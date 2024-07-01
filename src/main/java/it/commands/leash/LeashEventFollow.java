package it.commands.leash;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LeashEventFollow extends BukkitRunnable {
    private final Player val$leashedPlayer;
    private final Player val$player;
    private final LeashEvent event;

    LeashEventFollow(LeashEvent var1, Player var2, Player var3) {
        this.event = var1;
        this.val$leashedPlayer = var2;
        this.val$player = var3;
    }

    public void run() {
        if (!this.event.getLeashed().contains(this.val$leashedPlayer.getUniqueId().toString())) {
            this.cancel();
        }

        if (this.val$player.getLocation().distanceSquared(this.val$leashedPlayer.getLocation()) > 10.0) {
            this.val$leashedPlayer.setVelocity(this.val$player.getLocation().toVector().subtract(this.val$leashedPlayer.getLocation().toVector()).multiply(0.05));
        }
        if(!val$player.getWorld().getPlayers().contains(val$leashedPlayer)){
            val$leashedPlayer.teleport(val$player);
        }
    }
}
