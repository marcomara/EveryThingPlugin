package it.commands.TPA;

import it.utils.TabCompleteUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.plugin.Plugin.*;


public class Command implements CommandExecutor {
    static int tseconds;
    static int tminutes;
    static int thours;
    public static Map<Player /*sender*/,Player /*target*/> table = new HashMap<>();
    public static Map<Player, BukkitTask> tasks = new HashMap<>();
    public Command(int tpatimer){
        tseconds=tpatimer;
        while(tseconds>=60){
            tseconds=tseconds-60;
            tminutes++;
        }
        while(tminutes>=60){
            tminutes=tminutes-60;
            thours++;
        }
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String string, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender){
            lgg.warning("Only players can perform this command");
            return true;
        }
        if(args.length<1){
            sender.sendMessage("You need to specify an online player");
            return true;
        }
        if(args.length==2){
            if(args[0].equals("allow") || args[0].equals("deny")){
                Player csender = Bukkit.getPlayer(args[1]);
                if (args[0].equals("allow")) {
                    if (table.get(csender) == sender) {
                        table.remove(csender);
                        tasks.get(csender).cancel();
                        tasks.remove(csender);
                        Chunk[] chunks =((Player) sender).getWorld().getLoadedChunks();
                        Bukkit.getScheduler().runTaskLater(plugin, ()->{
                            csender.teleport(((Player) sender).getLocation());
                        }, 1L);
                        for (Chunk c : chunks){
                            if (c.getPlayersSeeingChunk().isEmpty()){
                                c.unload();
                            }
                        }
                        return true;
                    } else {
                        sender.sendMessage("You have no requests from " + args[1]);
                        return true;
                    }
                }else{
                    table.remove(csender);
                    tasks.get(csender).cancel();
                    tasks.remove(csender);
                    csender.sendMessage(sender.getName() + " has refused your tpa request");
                    return true;
                }
            }
            sender.sendMessage("Wrong use of the command syntax");
            return false;
        }
        if(args.length==1){
            if(sender.getName().equals(args[0])){
                sender.sendMessage("You can't tpa to yourself");
                return true;
            }
            if (table.containsKey(sender)){
                sender.sendMessage("Your previous tpa request has been cancelled");
                table.remove(sender);
                tasks.get(sender).cancel();
                tasks.remove(sender);
            }
            Bukkit.getPlayer(args[0]).sendMessage(request(sender.getName()));
            table.put((Player) sender, Bukkit.getPlayer(args[0]));
            tasks.put((Player) sender, new TPAtimer(Bukkit.getPlayer(args[0]), (Player) sender).runTaskTimerAsynchronously(plugin, 0L, 20L));
            return true;
        }

        return false;
    }

    public static class TPAtimer extends BukkitRunnable{
        int seconds, minutes, hours;
        Player target, sender;
        public TPAtimer(@NotNull Player target, @NotNull Player sender){
            this.seconds=tseconds;
            this.minutes=tminutes;
            this.hours=thours;
            this.target=target;
            this.sender=sender;
        }

        @Override
        public void run() {
            if (seconds==0&&minutes==0&&hours==0){
                sender.sendMessage("Your tpa request has expired");
                target.sendMessage("The tpa request from " + sender.getName() + " has expired");
                table.remove(sender,target);
                cancel();
            }
            if (seconds==0){
                if (minutes==0){
                    hours--;
                    minutes=minutes+60;
                }
                minutes--;
                seconds=seconds+60;
            }
        }
    }

    public static class TPACommandCompleter implements TabCompleter{
        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] args) {
            List<String> toreturn = new ArrayList<>();
            if (args.length == 1) {
                toreturn.add("allow");
                toreturn.add("deny");
                toreturn.addAll(TabCompleteUtils.getOtherOnlinePlayers((Player) commandSender));
            }
            if (args.length == 2 && (args[0].equals("allow")||args[0].equals("deny"))) {

                return getTpaSpecificOnlinePlayers(commandSender);
            }
            return toreturn;
        }

        public static List<String> getTpaSpecificOnlinePlayers(CommandSender sender) {
            List<String> applicants = new ArrayList<>();
            if (!table.isEmpty()) {
                for (Map.Entry<Player,Player> e : table.entrySet()){
                    if (e.getValue()==sender){
                        applicants.add(e.getKey().getName());
                    }
                }
            }
            return applicants;
        }
    }

    private static Component request(String name){
        TextComponent allow = Component.text("[Allow]").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD)
                .clickEvent(ClickEvent.runCommand("/tpa allow "+name))
                .hoverEvent(HoverEvent.showText(Component.text("Allow the tpa request and teleport " + name + " to you")));
        TextComponent deny = Component.text("[Deny]").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)
            .clickEvent(ClickEvent.runCommand("/tpa deny "+name))
                .hoverEvent(HoverEvent.showText(Component.text("Deny the tpa request")));
        return Component.text(name + " wants to teleport to you!")
                .append(Component.text("\n" ))
                .append(Component.text("        "))
                .append(allow)
                .append(Component.text("        "))
                .append(deny);
    }
}
