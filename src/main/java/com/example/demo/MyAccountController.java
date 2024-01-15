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
/*************************************************************************************
 *This is the MyAccountController class it is used to control the MyAccountPage      *
 *                                                                                   *
 *GERMAIN Victor @Zoomma1/@Kirat0s                                                   *
 *************************************************************************************/
public class MyAccountController extends NavBarController{

    @FXML
    TextField newEmailTextField;
    @FXML
    TextField newEmailRepeatTextField;
    @FXML
    PasswordField newPasswordPasswordField;
    @FXML
    PasswordField newPasswordRepeatPasswordField;
    @FXML
    Label errorLabel;

    public void dashboardOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "DashboardPage.fxml");
    }

    public void cryptocurrencyOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "CryptocurrencyPage.fxml");
    }

    public void stockOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "StockPage.fxml");
    }

    public void tradesOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "TradesPage.fxml");
    }

    public void overviewOnAction(ActionEvent event) throws IOException, InterruptedException {
        switchPage(event, "OverviewPage.fxml");
    }

    public void disconnectOnAction(ActionEvent event) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        LoginPageController loginPageController = loader.getController();
        switchPage(event, "LoginPage.fxml");
//      todo: disconnect user if needed with the db
    }


    public void eMailOkButtonOnAction(ActionEvent event) {
        if(newEmailTextField.getText().equals(newEmailRepeatTextField.getText())){
//            todo: change email in db
        }else{
            errorLabel.setText("Mail address must match");
            errorLabel.setVisible(true);
        }
    }

    public void passwordOkButtonOnAction(ActionEvent event) {
        if(newPasswordPasswordField.getText().equals(newPasswordRepeatPasswordField.getText())){
//            todo: change pwd in db
        }else{
            errorLabel.setText("Password must match");
            errorLabel.setVisible(true);
        }
    }
}
