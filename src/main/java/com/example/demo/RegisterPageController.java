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

public class RegisterPageController {
    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    PasswordField password;
    @FXML
    PasswordField password1;
    @FXML
    Label correctInfo;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private CSVLogin csvLogin = new CSVLogin();

    public void registerOnAction(ActionEvent event) throws IOException {
        if(!username.getText().equals("") && !email.getText().equals("") && !password.getText().equals("") && password.getText().equals(password1.getText())){
            csvLogin.setNewUserData(username.getText() + "," + email.getText() + "," + password.getText());
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
