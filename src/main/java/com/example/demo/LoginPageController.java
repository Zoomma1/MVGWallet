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
/*************************************************************************************
 *This is the LoginPageController class it is used to control the LoginPage          *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class LoginPageController {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label correctInfo;
    @FXML
    CheckBox stayConnected;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private CSVLogin csvLogin = new CSVLogin();

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

    public void registerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}