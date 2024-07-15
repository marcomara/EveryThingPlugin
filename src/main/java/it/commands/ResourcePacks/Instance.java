package it.commands.ResourcePacks;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Instance {
    public static int port;
    public static String ip;

    protected Map<String, File> rpacks;



    public Instance(int p, String i, Map<String,File> map){
        port=p;
        ip=i;
        rpacks=map;
    }
    public int getPort(){
        return port;
    }

    public  String getIp() {
        return ip;
    }

    public Map<String,File> getRP(){
        return rpacks;
    }
    public Set<String> getRPListForCommand(){
        Set<String> set = new HashSet<>(rpacks.keySet());
        set.add("reset");
        return set;
    }
}
