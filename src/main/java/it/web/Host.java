package it.web;

import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.swing.*;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Host{
    //private static ServerSocket socket;
    private static HttpServer server;
    public static void main(int port) throws Exception{

        /*socket = new ServerSocket(port);
        byte[] page = Bukkit.getPluginManager().getPlugin("EveryThingPlugin").getResource("config.yml").readAllBytes();
        while(!socket.isClosed()){
            Socket client = socket.accept();
            OutputStream clouts = client.getOutputStream();
            clouts.write("HTTP/1.1 200 OK \r\n".getBytes());
            clouts.write("ContentType: text/html\r\n".getBytes());
            clouts.write("\r\n".getBytes());
            clouts.write(page);
            clouts.write("\r\n\r\n".getBytes());
            clouts.flush();
            client.close();
        }*/
        server = HttpServer.create(new InetSocketAddress(port),0);
        for(Plugin pl : Bukkit.getPluginManager().getPlugins()){
            server.createContext("/PluginManager/" + pl.getName(), new Handler(pl));
        }
        server.setExecutor(null);
        server.start();
    }

    public static void stop() throws Exception{
        //socket.close();
        server.stop(0);
    }

}
