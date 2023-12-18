module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.mail;
    requires jdk.httpserver;
    requires java.net.http;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}
