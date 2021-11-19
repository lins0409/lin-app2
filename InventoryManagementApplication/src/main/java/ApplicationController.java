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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class ApplicationController implements Initializable {

    //create an observable list  for elements being added to the table
    ObservableList <InventoryItems> inventoryItemsObservableList = FXCollections.observableArrayList();
    //create a global variable for character limit
    private static final int limit = 256;

    //to check if any of the text matches with items in that able
    private  boolean searchFilter(InventoryItems item, String searchText){
        return ((item.getSerialNum().toLowerCase().contains(searchText.toLowerCase(Locale.ROOT))) || (item.getItemName().toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))));
    }
    //create a filtered list that updates as the search box gets more characters
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

        //need this part in order to make the column editable
        serialColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
    }

    //add items from text field into the chart
    @FXML
    void addItem() {
        //flags
        boolean serialClear = true, nameClear = true, valueClear = true;
        //regex pattern to ensure that the pattern of the serial number is following the correct format
        String pattern = "[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}";

        //create new instance of InventoryItems, make it get the stuff in the text field
        InventoryItems items = new InventoryItems(serialNumber.getText(), itemName.getText(), Double.parseDouble(moneyValue.getText()));
        //set a temp value to the new value for comparing
        String tempItem = serialNumber.getText();
        //if the entered value matches up with a previous value on the chart make an error message pop up
        for (int i = 0; i < inventoryItemsObservableList.size(); i++){
            for(int j = i; j < inventoryItemsObservableList.size(); j ++){
                if(tempItem.equals(inventoryItemsObservableList.get(j).getSerialNum())){
                    try {
                        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SerialDuplicateErrorPopUp.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Error");
                        stage.setScene(new Scene(parent));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //set to false so item won't be added to table so user can go back and fix it
                    serialClear = false;
                }
            }
        }
        //if the serial number does not match with the pattern A-XXX-XXX-XXX throw an error message
        if(!Pattern.matches(pattern, tempItem)){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SerialInvalidFormatErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //set to false so item won't be added to table so user can go back and fix it
            serialClear = false;
        }
        //compares the serial number to the rest of the serial numbers in the table
        //if the name of the item isn't long enough or if empty, make error message pop up
        if((itemName.getText().length() < 2) || (itemName.getText() == null)){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NameErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Error");
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
                stage.setTitle("Error");
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
    //error regarding the controller, figure out how to display information from table on it
    @FXML
    void editItem() {
        //set table to true so that it can be edited
        inventoryTable.setEditable(true);
    }

    //on double click after the modify button has been pressed.
    @FXML
    void editName(CellEditEvent editCell) {
        //flag
        boolean validInput = true;
        InventoryItems items = inventoryTable.getSelectionModel().getSelectedItem();

        //check to make sure that the new value is valid
        if((editCell.getNewValue().toString().length() < 2) || (editCell.getNewValue().toString() == null)){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NameErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            validInput = false;
        }
        //if there are no issues encountered
        if (validInput){
            //update table with new value
            items.setItemName(editCell.getNewValue().toString());
        }
    }

    //edit the serial value
    @FXML
    void editSerial(CellEditEvent editCell) {
        //flag
        boolean validInput = true;
        //regex pattern for comparison
        String pattern = "[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}";
        InventoryItems items = inventoryTable.getSelectionModel().getSelectedItem();
        //set the new input to a new string to be compared
        String tempItem = editCell.getNewValue().toString();

        //if the entered value matches up with a previous value on the chart make an error message pop up
        for (int i = 0; i < inventoryItemsObservableList.size(); i++){
            for(int j = i; j < inventoryItemsObservableList.size(); j ++){
                if(tempItem.equals(inventoryItemsObservableList.get(j).getSerialNum())){
                    try {
                        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SerialDuplicateErrorPopUp.fxml")));
                        Stage stage = new Stage();
                        stage.setTitle("Error");
                        stage.setScene(new Scene(parent));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //set to false so item won't be added to table so user can go back and fix it
                    validInput = false;
                }
            }
        }
        //checks to make sure that the serial number is in the format of the pattern
        if((!Pattern.matches(pattern, editCell.getNewValue().toString()))){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SerialInvalidFormatErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            validInput = false;
        }
        //if no problems
        if (validInput){
            //update the old value with the new value
            items.setSerialNum(editCell.getNewValue().toString());
        }
    }

    //when you double-click the value cell after you press the modify button
    @FXML
    void editVal(CellEditEvent editCell) {
        //flag for error message
        boolean validInput = true;

        InventoryItems items = inventoryTable.getSelectionModel().getSelectedItem();
        //check to make sure that value is valid
        if(Double.parseDouble(editCell.getNewValue().toString()) < 0){
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MoneyErrorPopUp.fxml")));
                Stage stage = new Stage();
                stage.setTitle("Error");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //set bool to false so item won't be added to table
            validInput = false;
        }
        //if there are no problems with the value
        if (validInput) {
            items.setItemValue(Double.parseDouble(editCell.getNewValue().toString()));
        }
    }

    //open a pre-made file
    @FXML
    void loadFile(){
        //clear the values from the current list
        inventoryItemsObservableList.clear();

        FileChooser fc = new FileChooser();
        //add the type of files the user can export the data as
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML file", "*.html"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TSV file", "*.txt"));
        //set the file to whatever the user chooses to open
        File f = fc.showOpenDialog(new Stage());

        //if the selected file isn't null
        if(f != null){
            ImportFile imp = new ImportFile();
            //checking to see what type of file extension the user wants to open
            String extension = " ";
            String fileName = f.toString();
            //returns the last occurrence of .
            int index = fileName.lastIndexOf('.');
            if (index > 0){
                //returns the value after the ',', so the extension type
                extension = fileName.substring(index + 1);
            }
            //now that the file extension is found, if it's txt
            if(extension.equals("txt")){
                imp.openTSV(f);
            }
            //if it's html
            if(extension.equals("html")){
                imp.openHTML(f);
            }
            //if it's json
            if(extension.equals(".json")){
                imp.openJson(f);
            }
        }
    }

    //create a new file
    @FXML
    void saveFile() throws IOException {
        //make an instance of FileChooser
        FileChooser fc = new FileChooser();
        //add extensions for valid file formats the user can save to
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML file", "*.html"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TSV file", "*.txt"));

        //whatever and wherever the user decides to save the file as
        File newFile = fc.showSaveDialog(new Stage());
        //call functions to export the items in different formats
        if(newFile != null){
            ExportFileFormats exp = new ExportFileFormats();

            //checking to see what type of file extension the user used
            String extension = " ";
            String fileName = newFile.toString();
            //returns the last occurrence of .
            int index = fileName.lastIndexOf('.');
            if (index > 0){
                //returns the value after the ',', so the extension type
                extension = fileName.substring(index + 1);
            }
            //now that the file extension is found, if it's txt
            if(extension.equals("txt")){
                exp.exportTSV(newFile, inventoryTable.getItems());
            }
            //if it's html
            if(extension.equals("html")){
                exp.exportHTML(newFile, inventoryTable.getItems());
            }
            //if it's json
            if(extension.equals(".json")){
                exp.exportJSON(newFile, inventoryTable.getItems());
            }
        }
    }

    //set the values on the table
    @FXML
    void setTable() {
        //make the table uneditable
        inventoryTable.setEditable(false);
    }

    //clear the gui
    //no need to test because it is purely an effect for the gui
    void clearText(){
        //clear text boxes
        serialNumber.clear();
        itemName.clear();
        moneyValue.clear();
    }
}

