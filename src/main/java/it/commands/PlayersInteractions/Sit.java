package it.commands.PlayersInteractions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.sitlist;

public class Sit implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player) commandSender;
        SlimeSit(p, p.getLocation().getBlock().getRelative(BlockFace.DOWN), false);
        return true;
    }

    public static void SlimeSit(Player p, Block b, boolean isSlab){
        if(isSlab){
            func(p, b, 0.0);
            return;
        }
        if(b.getBlockData() instanceof Slab || b.getType() == Material.STONECUTTER) {
            func(p, b, 0.0);
        } else func(p, b, +0.5);
    }

    private static void func(Player p, Block b, double y){
        Slime m = p.getWorld().spawn(b.getLocation().add(0.5, y, 0.5), Slime.class);
        m.setAI(false);
        m.setInvisible(true);
        m.setInvulnerable(true);
        m.setSize(0);
        m.setSilent(true);
        m.setPassenger(p);
        sitlist.add(m);
    }
}
