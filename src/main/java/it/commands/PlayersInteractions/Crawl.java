package it.commands.PlayersInteractions;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import it.Misc.KnockDown.TRY;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.plugin;

public class Crawl implements CommandExecutor, TabCompleter {
    Listener ls;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        ls =new PlayerMovement();
        Location l = player.getLocation();
        l.getBlock().getRelative(BlockFace.UP).setType(Material.BARRIER, true);
        player.setPose(Pose.SWIMMING);
        Bukkit.getPluginManager().registerEvents(ls, plugin);
        Bukkit.getPluginManager().registerEvents(new StopCrawling(), plugin);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }

    public class StopCrawling implements Listener {
        @EventHandler
        public void playerSneak(PlayerToggleSneakEvent e) {
            PlayerMoveEvent.getHandlerList().unregister(ls);
            PlayerJumpEvent.getHandlerList().unregister(ls);
            e.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
        }
    }
    public class PlayerMovement implements Listener{
        @EventHandler
        public void listener(PlayerMoveEvent e){
            if(e.getPlayer().isFlying()){
                new StopCrawling().playerSneak(new PlayerToggleSneakEvent(e.getPlayer(), true));
            }
            Block from = e.getFrom().getBlock();
            Block to = e.getTo().getBlock();
            if(from.getX()!=to.getX()||from.getZ()!=to.getZ()){
                Block toReplace = to.getRelative(BlockFace.UP);
                if (toReplace.getType() == Material.AIR) {
                    toReplace.setType(Material.BARRIER, true);
                }
                if (from.getRelative(BlockFace.UP).getType() == Material.BARRIER){
                    from.getRelative(BlockFace.UP).setType(Material.AIR);
                }
            }
        }

        @EventHandler
        public void listener2(PlayerJumpEvent e){
            Block from = e.getFrom().getBlock();
            from.getRelative(BlockFace.UP).setType(Material.AIR);
            BukkitTask run = new BukkitRunnable() {
                @Override
                public void run() {
                    if(e.getPlayer().getLocation().getY() == e.getPlayer().getLocation().getY()+1){
                        from.getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.BARRIER);
                        from.setType(Material.BARRIER);
                        this.cancel();
                    }
                }
            }.runTaskTimer(plugin, 0 , 1);
        }
    }
}
