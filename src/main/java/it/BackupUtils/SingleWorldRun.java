package it.BackupUtils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipOutputStream;

import static it.plugin.Plugin.lgg;

public class SingleWorldRun extends BukkitRunnable {
    String world;
    File bkfolder;

    public SingleWorldRun(String world, File bkfolder){
        this.world=world;
        this.bkfolder=bkfolder;
    }
    @Override
    public void run() {
        File world = Bukkit.getWorld(this.world).getWorldFolder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        LocalDateTime now = LocalDateTime.now();
        String name = "Backup".concat(this.world).concat(dtf.format(now)).concat(".zip");
        File bkp = new File(bkfolder, name);
        try{
            FileOutputStream fos = new FileOutputStream(bkp);
            ZipOutputStream zos = new ZipOutputStream(fos);
            new ZipDirectory(world, world.getName(), zos);
        }catch (Exception e) {
            e.printStackTrace();
        }
        lgg.info("Backup of " + this.world + " done");
    }
}
