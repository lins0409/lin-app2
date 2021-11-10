/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorPopUp {

    @FXML
    private Label errorMessage;

    @FXML
    private Button okButton;

    //button to close the popup
    @FXML
    void closeErrorMessage(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    //set the error message to be whatever caused it, ex. duplicate serial number
    void labelChanger(){
        //if the value that's passed in is for a duplicate serial number, make the error message say that the number is a duplicate
        //if the value that's passed in is for an invalie name, make the error message say that it's for an invalid name
            //most likely for it's too short
        //if the value that's passed in for an invalid monetary value, make the message that say that it's for invalid money value
    }
}

