package pt.inescid.safecloudfs.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;

public class UploadHandler implements HttpHandler {
    private final String mountDir;

    public UploadHandler(String mountDir) {
        this.mountDir = mountDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream is = exchange.getRequestBody();
            String key = exchange.getRequestURI().getQuery().split("=")[1].replace("/", ".");
            Path dest = Paths.get(this.mountDir, key);

            try (OutputStream os = Files.newOutputStream(dest.toFile().toPath())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }

            exchange.sendResponseHeaders(200, "Uploaded successfully".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("Uploaded successfully".getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }
}