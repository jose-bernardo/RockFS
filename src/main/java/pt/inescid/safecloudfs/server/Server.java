package pt.inescid.safecloudfs.server;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import java.io.*;

public class Server {
    private int port;
    private String mountDir;

    public Server(int port, String mountDir) {
        this.port = port;
        this.mountDir = mountDir;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/download/", new DownloadHandler(this.mountDir));
        server.createContext("/upload", new UploadHandler(this.mountDir));
        server.setExecutor(null);
        server.start();
        System.out.println("Server is running on port " + this.port);
    }
}
