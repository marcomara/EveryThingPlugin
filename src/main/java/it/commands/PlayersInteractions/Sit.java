package it.commands.PlayersInteractions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.sitlist;

public class Sit implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        SlimeSit(p, p.getLocation().getBlock().getRelative(BlockFace.DOWN));
        return true;
    }

    public static void SlimeSit(Player p, Block b){
        BlockData bd = b.getBlockData();
        if((bd instanceof Slab&&((Slab)bd).getType() == Slab.Type.BOTTOM)|| bd instanceof Stairs ||  b.getType() == Material.STONECUTTER) {
            func(p, b, 0.0);
        }
        else func(p, b, +0.5);
    }

    private static void func(Player p, Block b, double y){
        Slime m = p.getWorld().spawn(b.getLocation().add(0.5, y, 0.5), Slime.class);
        m.setAI(false);
        m.setInvisible(true);
        m.setInvulnerable(true);
        m.setSize(0);
        m.setSilent(true);
        m.addPassenger(p);
        sitlist.add(m);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }

    public static class SlimeFollow extends BukkitRunnable {
        Player p;
        Slime s;
        public SlimeFollow(Player p,Slime s){
            this.p=p;
            this.s=s;
        }
        @Override
        public void run() {
            if (!s.isDead()) {
                s.getLocation().setYaw(p.getYaw());
            }else this.cancel();
        }
    }
}
