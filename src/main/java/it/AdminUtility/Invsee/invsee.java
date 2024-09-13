package it.AdminUtility.Invsee;

import it.plugin.Plugin;
import it.utils.TabCompleteUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.logging.Level;

import static it.plugin.Plugin.lgg;
import static it.plugin.Plugin.plugin;

public class invsee implements CommandExecutor {
    public static final String[] arguments1 = {"getOtherOnlinePlayers"};
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!commandSender.hasPermission(command.getPermission())){commandSender.sendMessage(command.getPermissionMessage());return true;}
        if(commandSender instanceof ConsoleCommandSender){ lgg.log(Level.WARNING,  "Only players can do that"); return true;}
        if(args.length!=1&&!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){commandSender.sendMessage("You need to specify a player");return true;}
        Player p = (Player) commandSender;
        Player t = Bukkit.getPlayer(args[0]);
        if(t==null){
            p.sendMessage("Player " + args[0] + " isn't online");
            return true;
        }
        Inventory dinv = Bukkit.createInventory(p,45);
        p.openInventory(dinv).setTitle(t.getName() + "'s inventory");
        InvListener l = new InvListener(plugin,dinv,t,p);
        BukkitRunnable r = new Runner(p,t,dinv, l);
        int n = 0;
        for (ItemStack i : t.getInventory()) {
            dinv.setItem(n, i);
            n++;
        }
        plugin.getServer().getPluginManager().registerEvents(l,plugin);
        r.runTaskTimer(plugin,0,1L);
        return true;
    }
    public static class InvSeeTab implements TabCompleter {
        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
            return TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender);
        }
    }
}
