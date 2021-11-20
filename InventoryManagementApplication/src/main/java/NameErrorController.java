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

public class NameErrorController implements Initializable {

    @FXML
    private Label errorMessage= new Label("There is an error with the length of your item name. Please ensure that the name is between 2 and 256 characters.");

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

