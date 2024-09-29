package pt.inescid.safecloudfs.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;

public class DownloadHandler implements HttpHandler {
    private final String mountDir;

    public DownloadHandler(String mountDir) {
        this.mountDir = mountDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        String filename = pathParts[2];
        String version = pathParts[3];
        Path filePath = Paths.get(this.mountDir, filename + "." + version);

        if (Files.exists(filePath)) {
            exchange.sendResponseHeaders(200, Files.size(filePath));
            try (OutputStream os = exchange.getResponseBody()) {
                Files.copy(filePath, os);
            }
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }
}