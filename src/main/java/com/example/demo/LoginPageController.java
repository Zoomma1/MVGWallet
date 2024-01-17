package com.example.demo;

import Entity.User;
import Entity.UserSQL;
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
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static Entity.UserSQL.hashingWord;
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
    public UserSQL sql = new UserSQL();
    public static User utilisateur;


    public void loginOnAction(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        if(username != null && password != null && !username.getText().isEmpty() && !password.getText().isEmpty()){
            if(sql.checkKnowUser(username.getText(),hashingWord(password.getText()))){
                utilisateur = new User(username.getText(),password.getText());
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
            if(username != null && password != null && !username.getText().isEmpty() && !password.getText().isEmpty()){
                if(sql.checkKnowUser(username.getText(),password.getText())){
                    utilisateur = new User(username.getText(),password.getText());
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
//    todo: stayconnected
    public void stayConnected(){
    }
}
