package it.AdminUtility.ClearLag;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import static it.plugin.Plugin.intMap;
import static it.plugin.Plugin.plugin;
import static it.utils.ChatUtils.BroadcastToOPsSwitch;

public class ClearLagCommand implements CommandExecutor {

    static BukkitTask msg;
    static BukkitTask action;

    public ClearLagCommand(){
        msg = new ClearLagTasks.ItemRemoveMsg().runTaskTimerAsynchronously(plugin,
                20L*60*(intMap.get("ClearLag.CheckTimer")-1) ,
                20L*60*intMap.get("ClearLag.CheckTimer"));
        action = new ClearLagTasks.ItemsRemove().runTaskTimer(plugin,
                20L*60*intMap.get("ClearLag.CheckTimer"),
                20L*60*intMap.get("ClearLag.CheckTimer"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
         if (sender.hasPermission("admin.clearlag")&&args.length==1){
             switch (args[0]){
                 case "skip" -> Skip();
                 case "start" -> Start();
                 case "stop" -> Stop();
                 default -> sender.sendMessage("Wrong use of arguments");
             }
         }
         return true;
    }


    public static void Skip(){
        Stop();
        Start();
    }

    public static void Start(){
        msg = new ClearLagTasks.ItemRemoveMsg().runTaskTimerAsynchronously(plugin,
                20L*60*(intMap.get("ClearLag.CheckTimer")-1) ,
                20L*60*intMap.get("ClearLag.CheckTimer"));
        action = new ClearLagTasks.ItemsRemove().runTaskTimer(plugin,
                20L*60*intMap.get("ClearLag.CheckTimer"),
                20L*60*intMap.get("ClearLag.CheckTimer"));
    }
    public static void Stop(){
        action.cancel();
        msg.cancel();
        BroadcastToOPsSwitch("Clearlag disabled", 2);
    }
}
