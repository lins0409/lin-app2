/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MoneyErrorController implements Initializable {

    @FXML
    private Label errorMessage= new Label("The monetary value you have entered is invalid. Please make sure that you enter a positive value.");

    public Label staticLabel = errorMessage;

    @FXML
    private Button okButton;

    //button to close the popup
    //no test made for it since it just closes the window
    @FXML
    public void closeErrorMessage() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticLabel = errorMessage;
    }
}

