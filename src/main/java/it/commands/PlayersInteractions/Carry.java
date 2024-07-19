package it.commands.PlayersInteractions;

import it.utils.TabCompleteUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Carry implements CommandExecutor, TabCompleter {
    public static final String[] arguments1 = {"getOtherOnlinePlayers"};
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player s = (Player) sender;
        Player p = Bukkit.getPlayer(args[0]);
        assert p != null;
        assert p != s;
        s.addPassenger(p);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender);
    }
}
