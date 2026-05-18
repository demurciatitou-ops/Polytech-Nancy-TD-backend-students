package com.example.todoapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.parseBoolean;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;

/**
 * Main class of the application. Managing routing and HTTP layer.
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final Pattern ID_PATH = Pattern.compile("^/tasks/([0-9]+)$");
    private static final TaskDao dao = new TaskDao();

    public static void main(String[] args) throws Exception {
        log.info("In-memory repository initialised");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/tasks", Application::handleTasks);
        server.setExecutor(null);
        server.start();
        log.info("HTTP server started on http://localhost:8080");
    }

    private static void sendResponse(HttpExchange exchange, int status, String json) throws IOException {
        if(nonNull(json)) {
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            byte[] bytes = json.getBytes(UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } else {
            exchange.sendResponseHeaders(status, 0);
            exchange.close();
        }
    }

    private static void handleTasks(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        //region Manage POST /tasks
        if ("POST".equals(method) && "/tasks".equals(path)) {
            Task input = JsonUtils.deserialize(new String(exchange.getRequestBody().readAllBytes(), UTF_8), Task.class);
            Task createdTask = dao.save(input);

            exchange.getResponseHeaders().add("Location", "/tasks/" + createdTask.id());
            sendResponse(exchange, 201, JsonUtils.serialize(createdTask));
            return;
        }
        //endregion

        //region Manage GET /tasks/{id}
        Matcher m = ID_PATH.matcher(path);
        if ("GET".equals(method) && m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Optional<Task> task = dao.findById(id);

            if (task.isPresent()) {
                sendResponse(exchange, 200, JsonUtils.serialize(task.get()));
            } else {
                sendResponse(exchange, 404, null);
            }
            return;
        }
        //endregion

        //region Manage GET /tasks
        if ("GET".equals(method) && "/tasks".equals(path)) {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());
            if (params.get("todo") == null){
                sendResponse(exchange, 404, "missing argument");
                return;
            }
            else {
                List<Task> tasks = dao.getAll(Boolean.parseBoolean(params.get("todo")));
                sendResponse(exchange, 200, JsonUtils.serialize(tasks));
                return;
            }
        }
        //endregion

        //region Manage DELETE/tasks/{id}
        if ("DELETE".equals(method) && m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Optional<Task> deletedTask = dao.delete(id);

            if (deletedTask.isPresent()) {
                sendResponse(exchange, 200, JsonUtils.serialize(deletedTask.get()));
            } else {
                sendResponse(exchange, 404, null);
            }
            return;
        }
        //endregion

        if ("PUT".equals(method) && m.matches()){
            int id = Integer.parseInt((m.group(1)));
            Task input = JsonUtils.deserialize(new String(exchange.getRequestBody().readAllBytes(), UTF_8), Task.class);

            if (dao.findById(id).isPresent()){
                Optional<Task> result = dao.modify(input, id);
                if (result.isPresent()){
                    sendResponse(exchange, 200, JsonUtils.serialize(result.get()));
                }
                else{
                    sendResponse(exchange, 404, null);
                }
            }
            else{
                sendResponse(exchange, 204, null);
            }
        }

        // Otherwise → 404
        sendResponse(exchange, 404, null);
    }

    public static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();

        if (query == null) return params;

        for (String pair : query.split("&")) {
            String[] kv = pair.split("=");

            String key = kv[0];
            String value = kv.length > 1 ? kv[1] : "";

            params.put(key, value);
        }

        return params;
    }
}

