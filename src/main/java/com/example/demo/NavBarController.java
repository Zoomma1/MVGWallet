package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Nav bar controller.
 */
public abstract class NavBarController {
    /**
     * The Root.
     */
    protected Parent root;
    /**
     * The Stage.
     */
    protected Stage stage;
    /**
     * The Scene.
     */
    protected Scene scene;

    /**
     * Switch page.
     *
     * @param event    the event
     * @param pageFXML the page fxml
     * @throws IOException the io exception
     */
    public void switchPage(ActionEvent event, String pageFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pageFXML));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
