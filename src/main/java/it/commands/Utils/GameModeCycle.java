package it.commands.Utils;

import it.utils.Colors;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GameModeCycle implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if(p.getGameMode()==GameMode.SURVIVAL){
            p.setGameMode(GameMode.SPECTATOR);
            return true;
        }else
        if(p.getGameMode()==GameMode.SPECTATOR){
            p.setGameMode(GameMode.SURVIVAL);
            return true;
        }else {
            p.sendMessage(Colors.RED + "Unable to do that");
            return true;
        }
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}