package Entity.Email;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.Arrays;

public class EmailUtility {

    private static final String host = "smtp.sendgrid.net";
    private static final String port = "587";
    private static final String userName = "apikey";
    private static final String SENDGRID_API_KEY = "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";
    public static void sendEmail(String toAddress, String subject, String message)
            throws AddressException, MessagingException, IOException, InterruptedException {
        String requestBody = String.format(
                "{\"personalizations\": [{\"to\": [{\"email\": \"%s\"}],\"subject\": \"%s\"}],"
                        + "\"content\": [{\"type\": \"text/plain\", \"value\": \"%s\"}],"
                        + "\"from\": {\"email\": \"mvgwallet@gmail.com\"}}",
                toAddress, subject, message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sendgrid.com/v3/mail/send"))
                .header("Authorization", "Bearer " + SENDGRID_API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        System.out.println(request);
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
    }
}
