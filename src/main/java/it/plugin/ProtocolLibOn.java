package it.plugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLibOn {
    public static ProtocolManager manager;

    public void start(){
        manager = ProtocolLibrary.getProtocolManager();
    }
}
