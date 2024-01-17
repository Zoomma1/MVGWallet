package com.example.demo;

import Entity.User;
import Entity.UserSQL;
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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

import static Entity.UserSQL.hashingWord;

/*************************************************************************************
 *This is the RegisterPageController class it is used to control the                 *
 *  CryptocurrencyPage                                                               *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
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
    private User utilisateur = LoginPageController.utilisateur;
    private UserSQL sql = new UserSQL();

    public void registerOnAction(ActionEvent event) throws IOException, NoSuchAlgorithmException, SQLException {
//      todo: sql database for register
        if(!username.getText().equals("") && !email.getText().equals("") && !password.getText().equals("") && password.getText().equals(password1.getText()) && Email.isEmailValid(email.getText())){
            sql.insertToUser(username.getText(), hashingWord(password.getText()),"1",email.getText(),new Timestamp(System.currentTimeMillis()),false);
            System.out.println("User inscrit");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
            root = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            scene = new Scene(root, currentWidth , currentHeight);
            scene.getStylesheets().addAll(stage.getScene().getStylesheets());
            scene.setFill(stage.getScene().getFill());
            stage.setScene(scene);

            stage.setWidth(currentWidth);
            stage.setHeight(currentHeight);
            stage.show();
        }else{
            correctInfo.setVisible(true);
        }
    }

    public void backOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();

        scene = new Scene(root, currentWidth , currentHeight);
        scene.getStylesheets().addAll(stage.getScene().getStylesheets());
        scene.setFill(stage.getScene().getFill());
        stage.setScene(scene);

        stage.setWidth(currentWidth);
        stage.setHeight(currentHeight);
        stage.show();
    }
}
