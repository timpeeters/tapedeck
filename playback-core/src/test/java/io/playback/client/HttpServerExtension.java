package io.playback.client;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;

public class HttpServerExtension implements AfterAllCallback, BeforeAllCallback, Extension {
    private final HttpServer server;

    public HttpServerExtension(HttpServer server) {
        this.server = server;
    }

    public URL getBaseUrl() {
        try {
            return new URL("http", server.getAddress().getHostString(), server.getAddress().getPort(), "");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Unable to deduct base URL", e);
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        server.start();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        server.stop(0);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public HttpServerExtension build() {
            try {
                return new HttpServerExtension(createServer());
            } catch (IOException e) {
                throw new UncheckedIOException("Unable to create HttpServer", e);
            }
        }

        private HttpServer createServer() throws IOException {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", findFreePort()), 0);
            server.createContext("/").setHandler(createHandler());

            return server;
        }

        private HttpHandler createHandler() {
            return he -> {
                he.sendResponseHeaders(200, 0);
                he.close();
            };
        }

        private int findFreePort() {
            try (ServerSocket socket = new ServerSocket(0)) {
                return socket.getLocalPort();
            } catch (IOException e) {
                throw new UncheckedIOException("Unable to find a free port", e);
            }
        }
    }
}
