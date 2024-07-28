package it.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.OutputStream;

public class Handler implements HttpHandler {
    Plugin plugin;
    public Handler(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        byte[] page = plugin.getResource("config.yml").readAllBytes();
        ex.sendResponseHeaders(200, page.length);
        OutputStream os = ex.getResponseBody();
        os.write(page);
        os.close();
    }
}
