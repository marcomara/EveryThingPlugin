package it.leash;

import it.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LeashEvent implements Listener{
    private List<String> leashed = new ArrayList();
    private Plugin plugin;

    public LeashEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeashEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("leashplayer.use")) {
            EquipmentSlot slot = event.getHand();
            if (slot.equals(EquipmentSlot.HAND) && event.getRightClicked() instanceof Player) {
                Player leashedPlayer = (Player)event.getRightClicked();
                if (!this.getLeashed().contains(leashedPlayer.getUniqueId().toString())) {
                    if (player.getInventory().getItemInMainHand().getType() == Material.LEAD) {
                        this.leashed.add(leashedPlayer.getUniqueId().toString());
                        if (player.getGameMode() != GameMode.CREATIVE) {
                            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        }
                        Slime slime = (Slime)leashedPlayer.getWorld().spawnEntity(leashedPlayer.getLocation().add(0.0, 1.0, 0.0), EntityType.SLIME);
                        slime.setSize(0);
                        slime.setAI(false);
                        slime.setMetadata(leashedPlayer.getUniqueId().toString(), new FixedMetadataValue(this.plugin, "NoCollision"));
                        slime.setGravity(false);
                        slime.setLeashHolder(player);
                        slime.setInvulnerable(true);
                        slime.setInvisible(true);
                        this.plugin.team.getTeam().addEntry(slime.getUniqueId().toString());
                        leashedPlayer.setScoreboard(this.plugin.team.getBoard());
                        player.setMetadata(leashedPlayer.getUniqueId().toString(), new FixedMetadataValue(this.plugin, "Holder"));
                        new LeashEventFollow(this, leashedPlayer, player).runTaskTimer(this.plugin, 0L, 0L);
                    }
                } else {
                    this.unleashPlayer(leashedPlayer, player);
                }
            }
        }

    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (this.getLeashed().contains(event.getPlayer().getUniqueId().toString())) {
            Player leashedPlayer = event.getPlayer();
            Iterator var4 = leashedPlayer.getNearbyEntities(5.0, 5.0, 5.0).iterator();

            while(var4.hasNext()) {
                Entity entities = (Entity)var4.next();
                if (entities instanceof Slime && entities.hasMetadata(leashedPlayer.getUniqueId().toString())) {
                    Slime slime = (Slime)entities;
                    slime.teleport(leashedPlayer.getLocation().add(0.0, 1.0, 0.0));
                }
            }
        }

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (this.getLeashed().contains(event.getEntity().getUniqueId().toString())) {
            Player leashedPlayer = event.getEntity();
            Iterator var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                Player players = (Player)var4.next();
                if (players.hasMetadata(leashedPlayer.getUniqueId().toString())) {
                    this.unleashPlayer(leashedPlayer, players);
                }
            }
        }

    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (this.getLeashed().contains(event.getPlayer().getUniqueId().toString())) {
            Player leashedPlayer = event.getPlayer();
            Iterator var4 = Bukkit.getOnlinePlayers().iterator();

            while(var4.hasNext()) {
                Player players = (Player)var4.next();
                if (players.hasMetadata(leashedPlayer.getUniqueId().toString())) {
                    this.unleashPlayer(leashedPlayer, players);
                }
            }
        }

    }

    @EventHandler
    public void onHangingPlaceEvent(HangingPlaceEvent event) {
        if (event.getEntity() instanceof LeashHitch) {
            LeashHitch leash = (LeashHitch)event.getEntity();
            Iterator var4 = leash.getNearbyEntities(7.0, 7.0, 7.0).iterator();

            while(var4.hasNext()) {
                Entity entities = (Entity)var4.next();
                if (this.getLeashed().contains(entities.getUniqueId().toString())) {
                    event.setCancelled(true);
                }
            }

        }
    }

    private void unleashPlayer(Player leashedPlayer, Player leashHolder) {
        Iterator var4 = leashedPlayer.getNearbyEntities(1.0, 1.0, 1.0).iterator();

        while(var4.hasNext()) {
            Entity entities = (Entity)var4.next();
            if (entities instanceof Slime && entities.hasMetadata(leashedPlayer.getUniqueId().toString())) {
                Slime slime = (Slime)entities;
                slime.setLeashHolder((Entity)null);
                this.plugin.team.getTeam().removeEntry(slime.getUniqueId().toString());
                slime.remove();
                this.getLeashed().remove(leashedPlayer.getUniqueId().toString());
                if (leashHolder.getGameMode() != GameMode.CREATIVE) {
                    leashHolder.getInventory().addItem(new ItemStack[]{new ItemStack(Material.LEAD)});
                }
            }
        }

    }

    public List<String> getLeashed() {
        return this.leashed;
    }
}
