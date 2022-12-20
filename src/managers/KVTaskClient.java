package managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    static HttpClient client = HttpClient.newHttpClient();
    private static URL url;

    private static String apiToken;

    public KVTaskClient(URL url) throws IOException, InterruptedException {
        this.url = url;
        apiToken = getApiToken();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        URI uri = URI.create(url.toString() + "/save/" + key + "?" + "API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static String load(String key) throws IOException, InterruptedException {
        URI uri = URI.create(url.toString() + "/load/" + key + "?" + "API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        String httpTaskManager = "";

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonElement jsonElement = JsonParser.parseString(response.body());
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonObject managerObject = jsonObject.get(key).getAsJsonObject();
                httpTaskManager = managerObject.get(key).getAsString();
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return httpTaskManager;
    }

    private String getApiToken() throws IOException, InterruptedException {

        URI uri = URI.create("http://localhost:8080/register");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String apiToken = response.body();
        return apiToken;
    }
}
