package it.commands.Utils;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSize implements CommandExecutor {
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
}
