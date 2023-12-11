package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Register page controller.
 */
public class RegisterPageController {
    /**
     * The Username.
     */
    @FXML
    TextField username;
    /**
     * The Entity.Email.Email.
     */
    @FXML
    TextField email;
    /**
     * The Password.
     */
    @FXML
    PasswordField password;
    /**
     * The Password 1.
     */
    @FXML
    PasswordField password1;
    /**
     * The Correct info.
     */
    @FXML
    Label correctInfo;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private CSVLogin csvLogin = new CSVLogin();

    /**
     * Register on action.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void registerOnAction(ActionEvent event) throws IOException {
        if(!username.getText().equals("") && !email.getText().equals("") && !password.getText().equals("") && password.getText().equals(password1.getText()) && Email.isEmailValid(email.getText())){
            csvLogin.setNewUserData(username.getText() + "," + email.getText() + "," + csvLogin.encryptPassword(password.getText()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            correctInfo.setVisible(true);
        }
    }
}
