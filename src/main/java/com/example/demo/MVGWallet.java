package com.example.demo;

import Entity.Email.EmailUtility;
import Entity.User;
import Entity.UserSQL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TreeMap;

/*************************************************************************************
 *This is the application class it is used to launch the application                 *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/

public class MVGWallet extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException, SQLException, NoSuchAlgorithmException /*ParseException*/ {
        UserSQL user = new UserSQL();
        ArrayList<String> select = new ArrayList<>();
        select.add("identifiant");
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());

        user.checkKnowUser("mathys","123456");
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();
        LoginPageController loginPageController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(800);
        stage.setMinWidth(900);
        stage.show();*/
        }

    public static void main(String[] args) {
        launch();
    }
}
