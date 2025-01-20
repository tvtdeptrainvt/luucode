package bai2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientExample {
    private static final String API_URL = "https://jsonplaceholder.typicode.com/albums";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Response from API:");
                System.out.println(response.body());

                writeFile("albums_httpclient.json", response.body());
            } else {
                System.out.println("Failed to connect to API. Response Code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String fileName, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            System.out.println("Data written to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

