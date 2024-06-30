package it.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class Motd {
    public Motd(Player p, Plugin plugin) throws Exception {
        File file = new File(plugin.getDataFolder(), "motd.txt");
        if(!file.exists()){
            InputStream is = plugin.getResource("motd.txt");
            FileWriter fw = new FileWriter(file);
            assert is != null;
            for(byte b : is.readAllBytes()){
                fw.write(b);
            }
            fw.close();
            is.close();
        }
        String fp = file.getPath();
        try(BufferedReader bf = new BufferedReader(new FileReader(fp))){
            String line;
            while((line= bf.readLine())!=null){
                line = Colors.AlternateColorCodes('&',line).toString();
                p.sendMessage(replacements(line, p));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private static String replacements(String line, Player p){
        line = line.replaceAll("%Player%",p.getName());
        line = line.replaceAll("%OnlinePlayers%", String.valueOf(Bukkit.getOnlinePlayers().size()));
        line = line.replaceAll("%WorldTime%", time(p));
        return line;
    }
    private static String time(Player p){
        int ticks = (int) p.getWorld().getTime()+6000;
        if(ticks>=24000){
            ticks=ticks-24000;
        }
        long k = 1440/1200;
        int min = (int) ((ticks/20)*k);
        int h=0;
        while (min >= 60) {
            h++;
            min =min-60;
        }
        String time = (h + ":" +min);
        if(min<10){
            time = (h+":0"+min);
        }
        return time;
    }
}
