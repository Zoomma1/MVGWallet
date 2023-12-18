package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/*************************************************************************************
 *This is the NavBarController class it is used to change de Pages of all its        *
 * children class                                                                    *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
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
