/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private ComboBox<?> comboBox;

    @FXML
    private TableView<?> inventoryTable;

    @FXML
    private TextField itemName;

    @FXML
    private TextField moneyValue;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> serialColumn;

    @FXML
    private TextField serialNumber;

    @FXML
    private TableColumn<?, ?> valueColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //add items from textfield into the chart
    @FXML
    void addItem(ActionEvent event) {
        //create new instance of InventoryItems, make it get the stuff in the textfield
            //set specific string or boolean to be sent to the error message popup controller
            //if the serial number doesn't match the format, make the error message pop up
            //if the name of the item isn't long enough, make error message pop up
            //if value that is entered is negative, make error message pop up
        //set object to the tableview
        //set textfield values to columns in the table
        //autoclear textfield
    }

    //get rid of all the items in the table
    @FXML
    void clearTable(ActionEvent event) {
        //set all the items/indexes to null
        //clear();
    }

    //get rid of row
    @FXML
    void deleteItem(ActionEvent event) {
        //grab index of element in the table that is selected
        //then just remove the selected index
    }

    //edit item in table
    @FXML
    void editItem(ActionEvent event) {
        //grab the index of the selected element in the table
        //load up the edit stage
        //send the items over
    }

    //use combobox as a filter? maybe? or it may change to using the built-in table filter
    @FXML
    void filter(ActionEvent event) {

    }

    //open a premade file
    @FXML
    void loadFile(ActionEvent event) {

    }

    //create a new file
    @FXML
    void saveFile(ActionEvent event) {
        //make an instance of FileChooser
        //call functions to export the items in different formats
    }

    //clear the gui
    void clearText(){
        //clear textboxes
    }
}

