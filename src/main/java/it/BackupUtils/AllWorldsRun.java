package it.BackupUtils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipOutputStream;

import static it.plugin.Plugin.intMap;
import static it.plugin.Plugin.lgg;

public class AllWorldsRun extends BukkitRunnable {

    File bkfolder;
    public AllWorldsRun(File bkfolder){
        this.bkfolder=bkfolder;
    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String name = "Backup".concat(dtf.format(now)).concat(".zip");
        while(bkfolder.listFiles().length >= intMap.get("Backup.NumOfBackups")){
            File[] bkps = bkfolder.listFiles();
            Map<Integer, File> names = new HashMap<>();
            for(File file : bkps){
                names.put(Integer.parseInt(wrldNmeDte(file.getName())),file);
            }
            List<Integer> sort = new ArrayList<>(names.keySet());
            Collections.sort(sort);
            try {
                Files.delete(Paths.get(names.get(sort.getFirst()).getAbsolutePath()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
            FileOutputStream fos = new FileOutputStream(new File(bkfolder, name));
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(World w : Bukkit.getWorlds()){
                File in = w.getWorldFolder();
                new ZipDirectory(in, w.getWorldFolder().getName(), zos);
                lgg.info("Backup of " + w.getName() + " done");
            }
            zos.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private String wrldNmeDte(String str){
        String month = str.substring(11,13);
        String day = str.substring(14,16);
        String hour = str.substring(17,19);
        String min = str.substring(20,22);
        return month+day+hour+min;
    }
}
