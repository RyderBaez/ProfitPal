import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;
import com.google.gson.Gson; // Google's JSON library

public class ChatGPTAPI {
    // Use the correct endpoint for making chat completion requests
    static String apiURL = "https://api.openai.com/v1/chat/completions";
    static String apiKey = "sk-proj-7Bs1cGDHefFofv4Tp8v5T3BlbkFJactc1gAauSuxOeVSQuwa"; // Replace with your actual API key
    static String promptText = "give me a random string and don't respond with anything else your response should follow the following format with no additional text:\\r\\n"
            + "xYz9876AbCdEfGhI\\r\\n"
            + "EMPHASIS ON ONLY RETURNING THE RANDOM CODE NOT THE ONE I GIVE YOU";

    public static String sendPrompt() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        Map<String, Object> data = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "user", "content", promptText)
                },
                "max_tokens", 64,
                "temperature", 0.5 
        );

        String requestBody = gson.toJson(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiURL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body(); // Assuming the API returns JSON with the response
            } else {
                return "Error: HTTP " + response.statusCode(); // Prints the status code
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage(); // Generic error handling
        }
    }
}
