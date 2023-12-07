package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Login page controller.
 */
public class LoginPageController {
    /**
     * The Username.
     */
    @FXML
    TextField username;
    /**
     * The Password.
     */
    @FXML
    PasswordField password;
    /**
     * The Correct info.
     */
    @FXML
    Label correctInfo;
    /**
     * The Stay connected.
     */
    @FXML
    CheckBox stayConnected;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private CSVLogin csvLogin = new CSVLogin();

    /**
     * Login on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void loginOnAction(ActionEvent event) throws IOException {
        if(username != null && password != null && !username.getText().equals("") && !password.getText().equals("")){
            if(csvLogin.isUserCorrect(username.getText(),password.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardPage.fxml"));
                root = loader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                correctInfo.setVisible(false);
            }else {
                correctInfo.setVisible(true);
            }
        }
    }

    /**
     * Register on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void registerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}