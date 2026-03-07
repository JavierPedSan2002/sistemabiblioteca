package com.biblioteca.frontend.services;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.biblioteca.frontend.models.Prestamo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PrestamoService {

    private static final String API_URL = "http://localhost:8081/api/prestamos";

    private final HttpClient client;
    private final Gson gson;

    public PrestamoService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Prestamo> obtenerPrestamos() {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            Type listType = new TypeToken<List<Prestamo>>() {}.getType();

            return gson.fromJson(response.body(), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean crearPrestamo(Prestamo prestamo) {

        try {

            String json = gson.toJson(prestamo);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() >= 200 && response.statusCode() < 300;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarPrestamo(Prestamo prestamo) {

        try {

            if (prestamo == null || prestamo.getId() == null) {
                return false;
            }

            String json = gson.toJson(prestamo);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + prestamo.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() >= 200 && response.statusCode() < 300;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarPrestamo(Integer id) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() >= 200 && response.statusCode() < 300;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean devolverPrestamo(Integer id) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + id + "/devolver"))
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() >= 200 && response.statusCode() < 300;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}