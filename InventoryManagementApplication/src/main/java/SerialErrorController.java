/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SerialErrorController implements Initializable {

    @FXML
    private Label errorMessage= new Label("The serial number you have entered is already in use. Please enter another value.");

    public Label staticLabel = errorMessage;

    @FXML
    private Button okButton;

    //button to close the popup
    @FXML
    void closeErrorMessage() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticLabel = errorMessage;
    }
}

