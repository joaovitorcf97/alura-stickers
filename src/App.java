import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endress = URI.create(url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endress).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        JsonParser parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);

        for (Map<String, String> movie : movieList) {
            String imageURL = movie.get("image");
            InputStream inputStream = new URL(imageURL).openStream();
            String title = movie.get("title");
            String newTitle = title + ".png";

            var generator = new StickersGenerate();
            generator.create(inputStream, newTitle);

            System.out.println(title);
            System.out.println();
        }
    }
}
