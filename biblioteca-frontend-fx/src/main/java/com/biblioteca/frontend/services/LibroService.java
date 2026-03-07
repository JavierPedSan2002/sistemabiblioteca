package com.biblioteca.frontend.services;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.biblioteca.frontend.models.Libro;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LibroService {

    private static final String API_URL = "http://localhost:8081/api/libros";

    private final HttpClient client;
    private final Gson gson;

    public LibroService() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public List<Libro> obtenerLibros() {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            Type listType = new TypeToken<List<Libro>>() {}.getType();

            return gson.fromJson(response.body(), listType);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Libro obtenerLibroPorId(String idLibro) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + idLibro))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return gson.fromJson(response.body(), Libro.class);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean crearLibro(Libro libro) {

        try {

            String json = gson.toJson(libro);

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

    public boolean actualizarLibro(Libro libro) {

        try {

            if (libro == null || libro.getIdLibro() == null) {
                return false;
            }

            String json = gson.toJson(libro);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + libro.getIdLibro()))
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

    public boolean eliminarLibro(String idLibro) {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/" + idLibro))
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
}