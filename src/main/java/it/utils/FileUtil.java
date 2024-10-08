package it.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileUtil {
    static final int BUFFER_SIZE = 8192;

    public static void saveToFile( InputStream stream, File output, boolean force ) {
        if ( force && output.exists() ) output.delete();
        if ( !output.exists() ) {
            output.getParentFile().mkdirs();
            try {
                byte[] buffer = new byte[ stream.available() ];

                OutputStream outStream = new FileOutputStream( output );
                int len;
                while ( ( len = stream.read( buffer ) ) > 0)  {
                    outStream.write( buffer, 0, len );
                }
                stream.close();
                outStream.close();
            } catch ( Exception exception ) {
                exception.printStackTrace();
            }
        }
    }

    public static void updateConfigFromFile( File toUpdate, InputStream toCopy ) {
        updateConfigFromFile( toUpdate, toCopy, false );
    }

    public static void updateConfigFromFile( File toUpdate, InputStream toCopy, boolean trim ) {
        FileConfiguration config = YamlConfiguration.loadConfiguration( new InputStreamReader( toCopy ) );
        FileConfiguration old = YamlConfiguration.loadConfiguration( toUpdate );

        for ( String key : config.getKeys( true ) ) {
            if ( !old.contains( key ) ) {
                old.set( key, config.get( key ) );
            }
        }

        if ( trim ) {
            for ( String key : old.getKeys( true ) ) {
                if ( !config.contains( key ) ) {
                    old.set( key, null );
                }
            }
        }

        old.set("ConfigVersion", config.get("ConfigVersion"));
        try {
            old.save( toUpdate );
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }

    }

    public static void updateConfigPurge(File toUpdate, InputStream toCopy) throws Exception{
        FileConfiguration nvc = YamlConfiguration.loadConfiguration(new InputStreamReader(toCopy));
        FileConfiguration old = YamlConfiguration.loadConfiguration(toUpdate);
        Map<String, Object> oldmap = old.getValues(true);
        Map<String, Object> nvalues = nvc.getValues(true);
        Files.delete(Paths.get(toUpdate.getAbsolutePath()));
        toUpdate.createNewFile();
        FileConfiguration nfc = YamlConfiguration.loadConfiguration(toUpdate);
        for (String s : nvalues.keySet()){
            if (s.endsWith("example")){
                String parent = s.substring(0, s.length()-8);
                if (!old.getConfigurationSection(parent).getKeys(false).contains("example")){
                    continue;
                }
            }
            if (oldmap.containsKey(s)){
                nfc.set(s, oldmap.get(s));
            }else nfc.set(s, nvalues.get(s));
        }
        nfc.set("ConfigVersion", nvalues.get("ConfigVersion"));
        nfc.save(toUpdate);
    }

    public static boolean move( File original, File dest, boolean force ) {
        if ( dest.exists() ) {
            if ( !force ) {
                return false;
            } else {
                recursiveDelete( dest );
            }
        }
        dest.getParentFile().mkdirs();
        original.renameTo( dest );
        recursiveDelete( original );
        return true;
    }

    public static void recursiveDelete( File file ) {
        if ( !file.exists() ) {
            return;
        }
        if ( file.isDirectory() ) {
            for ( File lower : file.listFiles() ) {
                recursiveDelete( lower );
            }
        }
        file.delete();
    }

    public static < T > T readObject( Class< T > clazz, File file ) {
        T head = null;
        try {
            FileInputStream fis = new FileInputStream( file );
            ObjectInputStream ois = new ObjectInputStream( fis );

            head = ( T ) ois.readObject();

            ois.close();
            fis.close();
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }
        return head;
    }

    public static void writeObject(Serializable object, File file ) {
        try {
            FileOutputStream fos = new FileOutputStream( file );
            ObjectOutputStream oos = new ObjectOutputStream( fos );

            oos.writeObject( object );

            oos.close();
            fos.close();
        } catch ( Exception exception ) {
            exception.printStackTrace();
        }
    }
}
