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
    public void start(Stage stage) throws IOException, InterruptedException, SQLException /*ParseException*/ {
        UserSQL user = new UserSQL();
        ArrayList<String> select = new ArrayList<>();
        select.add("*");
        Date date = new Date();
        user.insert(2,"gigabg","123456","salt","mail@", new Timestamp(date.getTime()),false);
        System.out.println(user.selectWhere(select,"User","password","123456"));
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
