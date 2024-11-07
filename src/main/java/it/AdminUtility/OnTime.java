package it.AdminUtility;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class OnTime implements CommandExecutor {
    private static long time;
    public OnTime(){
        this.time = System.currentTimeMillis();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        int hours = 0, minutes = 0,seconds = (int) ((System.currentTimeMillis()-time)/1000);
        while (seconds > 60){
            minutes++;
            seconds -= 60;
        }
        while (minutes > 60){
            hours++;
            minutes -= 60;
        }
        commandSender.sendMessage("The server has been online for " + hours + " hours, " + minutes + " minutes and " + seconds + " seconds!");
        return true;
    }

}
