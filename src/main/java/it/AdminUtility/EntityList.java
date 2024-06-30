package it.AdminUtility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] strings) {
        if(!commandSender.isOp()) {
            commandSender.sendMessage("Only ops can do that");
            return true;
        }
        List<World> wl = Bukkit.getWorlds();
        int a=0,m=0,pl=0,pr=0;
        for(World w : wl){
            List<Entity> el = w.getEntities();
            for(Entity e : el) {
                if (e.getType() == EntityType.ARMOR_STAND) {
                    a++;
                }
                if (e.getType() == EntityType.MINECART) {
                    m++;
                }
                if (e.getType() == EntityType.SNOWBALL || e.getType() == EntityType.ARROW) {
                    pr++;
                }
                if (e.getType() == EntityType.PLAYER) {
                    pl++;
                }
            }
        }
        commandSender.sendMessage(Component.text("There are:\n").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD).append(
                Component.text(" -"+a + " armor stand/s\n -"+m+" minecart/s\n -"+pr+" projectile/s\n -"+pl+" player/s").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)));
        return true;
    }
}
