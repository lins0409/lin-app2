/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPopUp implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private TextField editItemName;

    @FXML
    private TextField editMoneyValue;

    @FXML
    private TextField editSerialNumber;

    @FXML
    private Button updateButton;

    private String itemNum;
    private String itemName;
    private double itemVal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //display text of selected item on table but in the textfields
        editSerialNumber.setText(itemNum);
        editItemName.setText(itemName);
        editMoneyValue.setText(String.valueOf(itemVal));
        //make it so that they can be changed
    }

    //closes the edit popup and does not save the edit
    @FXML
    void cancelButton(ActionEvent event) {
        //if the button is pressed, close the popup
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        //close the stage
        stage.close();
    }

    //commits edit to inventory item when selected
    @FXML
    void updateButton(ActionEvent event) {
        //if it is selected
        //fill all the textboxes with info from the table; textfields = value of items at selected index

        //replace the new text with new text
        InventoryItems items = new InventoryItems(editSerialNumber.getText(), editItemName.getText(), Double.parseDouble(editMoneyValue.getText()));
        ApplicationController applicationController = new ApplicationController();
        applicationController.inventoryItemsObservableList.add(items);

        //close the stage
        Stage stage = (Stage) updateButton.getScene().getWindow();
        stage.close();
    }
}

