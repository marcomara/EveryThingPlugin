package it.commands.Misc;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.plugin;

public class Jumpscare implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ArmorStandJumpscare(Bukkit.getPlayer(args[0]));
        return true;
    }
    public void ArmorStandJumpscare(Player p){
        p.addPotionEffect( new PotionEffect(PotionEffectType.DARKNESS, 40, 0));
        Location near = getLocation(p);
        ArmorStand as = p.getWorld().spawn(near, ArmorStand.class);
        as.setInvulnerable(true);
        as.setArms(true);
        as.getEquipment().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta =(LeatherArmorMeta) chestplate.getItemMeta();
        meta.setColor(Color.BLACK);
        chestplate.setItemMeta(meta);
        as.getEquipment().setChestplate(chestplate);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setColor(Color.BLACK);
        leggings.setItemMeta(meta);
        as.getEquipment().setLeggings(leggings);
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        as.getEquipment().setItemInMainHand(stoneSword);
        as.setVisible(true);
        as.setCanMove(true);
        Bukkit.getScheduler().runTaskTimer(plugin,(jsc)->{
            if (p.getLocation().distanceSquared(as.getLocation())>0.05){
                p.addPotionEffect( new PotionEffect(PotionEffectType.DARKNESS, 40, 1));
                as.getLocation().setY(p.getY());
                as.setVelocity(p.getLocation().toVector().subtract(as.getLocation().toVector()).multiply(0.3));
            }else {
                p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 100L, -7L);
                as.remove();
                jsc.cancel();
            }
        }, 1L,0L);
    }

    private static @NotNull Location getLocation(Player p) {
        Location near;
        double facing = p.getLocation().getYaw();
        double x = -3.0 * Math.sin(facing);
        double z = 3.0 * Math.cos(facing);
        near = new Location(p.getWorld(), p.getLocation().getBlockX()+x, p.getLocation().getBlockY(), p.getLocation().getBlockZ()+z);
        return near;
    }
}
