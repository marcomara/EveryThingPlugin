package it.commands.ResourcePacks.Server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Server {
    private static HttpServer httpServer;
    public static void main(int port, Map<String,File> rpcks) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        for(String rp: rpcks.keySet()){
            httpServer.createContext("/"+rp, new ResourcePackHandler(rpcks.get(rp)));
        }
        httpServer.setExecutor(null);
        httpServer.start();
    }
    public static void terminate(){
        httpServer.stop(0);
    }
    static class ResourcePackHandler implements HttpHandler{
        private File folder;

        public ResourcePackHandler(File folder){
            this.folder=folder;
        }
        @Override
        public void handle(HttpExchange ex) throws IOException{
            byte[] fileBytes = Files.readAllBytes(Paths.get(folder.getPath()));
            ex.sendResponseHeaders(200, fileBytes.length);
            OutputStream os = ex.getResponseBody();
            os.write(fileBytes);
            os.close();
        }
    }
}
