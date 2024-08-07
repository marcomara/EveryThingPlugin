package it.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class SaveUtility {
    public static void save(File file, FileConfiguration fileyml){
        try {
            fileyml.save(file);
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
    public static void create(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){
                e.fillInStackTrace();
            }
        }
    }
    public static FileConfiguration createyml(File file){
        return YamlConfiguration.loadConfiguration(file);
    }
    public static void saveList(List<?> list, FileConfiguration fileC, String path , File file){
        fileC.set(path,list);
        save(file, fileC);
    }
    public static void saveHashSet(HashSet<?> list, FileConfiguration fileC, String path, File file){
        fileC.set(path,list);
        save(file,fileC);
    }
    public static void copyFile(File source, File target) {
        try {
            Scanner s = new Scanner(source);
            FileWriter tw = new FileWriter(target);
            while (s.hasNextLine()) {
                tw.write(s.next());
            }
            s.close();
            tw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void saveFile(String url, File file){
        try{
            file.getParentFile().mkdirs();
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
}
