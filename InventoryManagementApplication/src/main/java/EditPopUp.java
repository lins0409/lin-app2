/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPopUp implements Initializable {

    @FXML
    private TextField editItemName;

    @FXML
    private TextField editMoneyValue;

    @FXML
    private TextField editSerialNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //display text of selected item on table but in the textfields
        //make it so that they can be changed
    }

    //closes the edit popup and does not save the edit
    @FXML
    void cancelButton(ActionEvent event) {
        //if the button is pressed, close the popup

    }

    //commits edit to inventory item when selected
    @FXML
    void updateButton(ActionEvent event) {
        //if it is selected
        //replace the new text with new text
    }

}

