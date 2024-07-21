package it.BackupUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
    public ZipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws Exception{
        for(File file: folder.listFiles()){
            if(file.isDirectory()){
                new ZipDirectory(file, parentFolder+ "/" + file.getName(), zos);
                continue;
            }
            if(file.getPath().endsWith("session.lock")){
                continue;
            }
            zos.putNextEntry(new ZipEntry(parentFolder+"/"+file.getName()));
            try(FileInputStream fis = new FileInputStream(file)){
                byte[] buffer = new byte[1024];
                int len;
                while((len = fis.read(buffer))>0){
                    zos.write(buffer, 0, len);
                }
            }
            zos.closeEntry();
        }
    }
}
