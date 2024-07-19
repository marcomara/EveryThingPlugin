package it.commands.ResourcePacks;

import it.utils.Codecs;
import it.utils.Colors;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.plugin.Plugin.instance;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if(args.length == 0){
            return false;
        } else if (args.length == 1) {
            if(args[0].equals("reset")){
                p.clearResourcePacks();
                return true;
            }
            return givePack(p, args[0]);
        } else if (args.length == 2) {
            return givePack(Bukkit.getPlayer(args[1]),args[0]);
        }else return false;
    }

    public static void sendResourcePack(Player p, String resourcepack){
        try{
            File file = instance.getRP().get(resourcepack);
            p.setResourcePack("http://"+instance.getIp()+":"+instance.getPort()+"/"+resourcepack, Codecs.calcSHA1String(file));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean givePack(Player p, String fileName){
        if(!instance.getRP().containsKey(fileName)){
            p.sendMessage(Colors.RED + fileName + " does not exist");
            return true;
        }
        sendResourcePack(p,fileName);
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length==1){
            return StringUtil.copyPartialMatches(args[args.length -1], instance.getRPListForCommand(),  new ArrayList<>());
        }
        if(commandSender.isOp()&&args.length==2){
            List<String> playernames= new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()){
                playernames.add(p.getName());
            }
            return StringUtil.copyPartialMatches(args[args.length -1], playernames, new ArrayList<>());
        }
        return null;
    }
}
