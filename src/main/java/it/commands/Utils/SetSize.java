package it.commands.Utils;

import it.utils.TabCompleteUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetSize implements CommandExecutor, TabCompleter {
    public static final String[] arguments1 = {"0.05 - 16"};

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,  String[] args) {
        if(args.length == 1 && commandSender instanceof Player){
            func((Player) commandSender, Double.parseDouble(args[0]));
            return true;
        }
        if(args.length == 2 ){
            func(Bukkit.getPlayer(args[1]),Double.parseDouble(args[0]));
            return true;
        }
        return false;
    }
    private void func (Player target, double NewScale){
        target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(NewScale);
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length==1)return new ArrayList<>(Arrays.asList(arguments1));
        if (args.length == 2 && commandSender.isOp()) return TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender);
        return new ArrayList<>();
    }
}
