package it.AdminUtility;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class OnTime implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("The server has been online for " + Timer.hours + " hours, " + Timer.minutes + " minutes and " + Timer.seconds + " seconds!");
        return true;
    }

    public static class Timer extends BukkitRunnable{
        public static int seconds;
        public static int minutes;
        public static int hours;

        @Override
        public void run() {
            seconds++;
            if (seconds==60){
                seconds=0;
                minutes++;
            }
            if (minutes==60){
                minutes=0;
                hours++;
            }
        }

        public static int getSeconds(){
            return seconds;
        }
        public static int getMinutes(){
            return minutes;
        }
        public static int getHours(){
            return hours;
        }
    }
}
