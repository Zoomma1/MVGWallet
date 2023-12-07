package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class NavBarController {
    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    public void switchPage(ActionEvent event, String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pageFXML));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
