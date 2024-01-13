package com.example.demo;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.Desktop;
import java.net.URL;

public class NewsTile {
    String source;
    String title;
    String articleURL;

    public NewsTile(String source, String title, String articleURL) {
        this.source = source;
        this.title = title;
        this.articleURL = articleURL;
    }

    public Node createNewNewsTile() {
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.getStyleClass().add("vBoxNewsTile");
        vBox.setPrefWidth(720);
        vBox.setFillWidth(true);

        Label source = new Label(this.source);
        TextFlow titleFlow = new TextFlow();
        Text title = new Text(this.title);

        source.setFont(new Font("Liberation Mono",15));
        title.setFont(new Font("Liberation Mono",15));
        source.setTextFill(Color.WHITE);
        title.setFill(Color.WHITE);
        titleFlow.setPrefWidth(720);
        titleFlow.setLineSpacing(5);
        titleFlow.getChildren().add(title);

        vBox.getChildren().addAll(source, title);

        vBox.setOnMouseClicked(event -> {
            openBrowser(this.articleURL);
        });
        return vBox;
    }

    public static void openBrowser(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

