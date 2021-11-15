/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class ApplicationController implements Initializable {

    //create an observable list  for elements being added to the table
    ObservableList <InventoryItems> inventoryItemsObservableList = FXCollections.observableArrayList();
    //create a global variable for character limit
    private static final int limit = 256;

    //to check if any of the text matches with items in that able
    private  boolean searchFilter(InventoryItems item, String searchText){
        return ((item.getSerialNum().toLowerCase().contains(searchText.toLowerCase(Locale.ROOT))) || (item.getItemName().toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))));
    }
    private ObservableList<InventoryItems> filteredList(List<InventoryItems> list, String searchText){
        List<InventoryItems> filterList = new ArrayList<>();
        for (InventoryItems items : list){
            if(searchFilter(items, searchText)){
                filterList.add(items);
            }
        }
        return FXCollections.observableList(filterList);
    }

    @FXML
    private TableView<InventoryItems> inventoryTable;

    @FXML
    private TextField itemName;

    @FXML
    private TextField moneyValue;

    @FXML
    private TextField searchBox;

    @FXML
    private TableColumn<InventoryItems, String> nameColumn;

    @FXML
    private TableColumn<InventoryItems, String> serialColumn;

    @FXML
    private TextField serialNumber;

    @FXML
    private TableColumn<InventoryItems, Double> valueColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //adds a listener to the search box so that it is dynamic and responsive to the table
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> inventoryTable.setItems(filteredList(inventoryItemsObservableList, newValue))
        );

        //sets it so that the max amount of characters allowed in the text box is 256
        itemName.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()){
                if(itemName.getText().length() >= limit){
                    itemName.setText(itemName.getText().substring(0, limit));
                }
            }
        });

        //set the values from text box to items in the table
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serialNum"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("itemValue"));

        //set table to true so that it can be edited
        inventoryTable.setEditable(true);
    }

    //add items from text field into the chart
    @FXML
    void addItem() {
        boolean serialClear = true, nameClear = true, valueClear = true;

        //create new instance of InventoryItems, make it get the stuff in the text field
        InventoryItems items = new InventoryItems(serialNumber.getText(), itemName.getText(), Double.parseDouble(moneyValue.getText()));

        //set a temp value to the new value for comparing
        String tempItem = serialNumber.getText();
        //if the entered value matches up with a previous value on the chart make an error message pop up

        //if the name of the item isn't long enough or if empty, make error message pop up
        if((itemName.getText().length() < 2) || (itemName.getText() == null)){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NameErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Modify Item");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //set to false so item won't be added to table
            nameClear = false;
        }
        //if value that is entered is negative, make error message pop up
        if(Double.parseDouble(moneyValue.getText()) < 0){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MoneyErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Modify Item");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //set bool to false so item won't be added to table
            valueClear = false;
        }
        //set object to the tableview as long as there are no issues
        if (serialClear && nameClear && valueClear){
            inventoryItemsObservableList.add(items);
            //set text field values to columns in the table
            inventoryTable.setItems(inventoryItemsObservableList);
            //auto clear text field
            clearText();
        }
    }

    //get rid of all the items in the table
    @FXML
    void clearTable(){
        //clear all
        inventoryTable.getItems().clear();
    }

    //get rid of row
    @FXML
    void deleteItem(){
        //grab index of element in the table that is selected
        int selectedItem = inventoryTable.getSelectionModel().getSelectedIndex();
        //then just remove the selected index
        inventoryTable.getItems().remove(selectedItem);
    }

    //edit item in table
    @FXML
    void editItem(ActionEvent event) {
        //grab the index of the selected element in the table
        //int items = inventoryTable.getSelectionModel().getSelectedIndex();

        //send the items over
        //EditPopUp editPopUp = new EditPopUp(inventoryItemsObservableList.get(items).getSerialNum(), inventoryItemsObservableList.get(items).getItemName(), inventoryItemsObservableList.get(items).getItemValue());

        //load up the edit stage
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("EditPopUp.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Modify Item");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //open a pre-made file
    @FXML
    void loadFile(ActionEvent event){

    }

    //create a new file
    @FXML
    void saveFile(ActionEvent event){
        //make an instance of FileChooser
        //call functions to export the items in different formats
    }

    //clear the gui
    //no need to test because it is purely an effect for the gui
    void clearText(){
        //clear text boxes
        serialNumber.clear();
        itemName.clear();
        moneyValue.clear();
    }

    //search filter
    //do not need to test since this updates the gui
    @FXML
    void search(ActionEvent event) {

    }
}

