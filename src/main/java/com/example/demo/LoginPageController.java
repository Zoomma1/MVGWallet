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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.scene.input.KeyCode.ENTER;

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
    private final CSVLogin csvLogin = new CSVLogin();


    public void loginOnAction(ActionEvent event) throws IOException {
        if(username != null && password != null && !username.getText().isEmpty() && !password.getText().isEmpty()){
//          todo: instead of using the csv here use the sql database
            if(csvLogin.isUserCorrect(username.getText(),password.getText())){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardPage.fxml"));
                root = loader.load();
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                double currentWidth = stage.getWidth();
                double currentHeight = stage.getHeight();
                scene = new Scene(root, currentWidth , currentHeight);
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
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        scene = new Scene(root, currentWidth , currentHeight);
        stage.setScene(scene);
        stage.show();
    }

    public void loginOnEnter(KeyEvent keyEvent) throws IOException {
        if(keyEvent.getCode() == ENTER){
//          todo: Same thing here
            if(username != null && password != null && !username.getText().isEmpty() && !password.getText().isEmpty()){
                if(csvLogin.isUserCorrect(username.getText(),password.getText())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardPage.fxml"));
                    root = loader.load();
                    stage = (Stage)((Node)keyEvent.getSource()).getScene().getWindow();
                    double currentWidth = stage.getWidth();
                    double currentHeight = stage.getHeight();
                    scene = new Scene(root, currentWidth , currentHeight);
                    stage.setScene(scene);
                    stage.show();
                    correctInfo.setVisible(false);
                }else {
                    correctInfo.setVisible(true);
                }
            }
        }
    }

    public void stayConnected(){
    }
}