package it.commands.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BedrockBuild implements Listener {
    @EventHandler
    public void blockPlace(PlayerInteractEvent e) {
        Block sb = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        Block rb = sb.getRelative(e.getPlayer().getFacing());
        ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
        if (e.getAction().isRightClick() &&
                e.getClickedBlock() == null &&
                is.getType().isBlock() &&
                sb.getType() != Material.AIR &&
                rb.getType() == Material.AIR &&
                is.getType().isSolid() &&
                is.getType().createBlockData().isOccluding()) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType().toString().endsWith("_SLAB")) {
                Material bb = is.getType();
                rb.setType(bb);
                Slab s = (Slab) rb.getBlockData();
                s.setType(Slab.Type.TOP);
                rb.setBlockData(s, true);
                is.setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                return;
            }
            rb.setType(is.getType(), true);
            is.setAmount(is.getAmount() - 1);
        }
    }
}
