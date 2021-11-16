/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

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

        //set table to true so that it can be edited
        inventoryTable.setEditable(true);
    }

    //add items from text field into the chart
    @FXML
    void addItem() {
        boolean serialClear = true, nameClear = true, valueClear = true;
        //regex pattern to ensure that the pattern of the serial number is following the correct format
        String pattern = "[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}";

        //create new instance of InventoryItems, make it get the stuff in the text field
        InventoryItems items = new InventoryItems(serialNumber.getText(), itemName.getText(), Double.parseDouble(moneyValue.getText()));
        //set a temp value to the new value for comparing
        String tempItem = serialNumber.getText();
        //if the entered value matches up with a previous value on the chart make an error message pop up
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
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML file", "*.html"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TSV file", "*.tsv"));
        List <File> f = (List<File>) fc.showOpenDialog(null);
    }

    void openJson(){
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse((new FileReader("")));
            Object obj = null;
            JSONObject jsonObject = (JSONObject) obj;
            String number = (String) jsonObject.get("serial number");
            String name = (String) jsonObject.get("item name");
            String value = (String) jsonObject.get("item value");

            //loop array
            JSONArray itemsArray = (JSONArray) jsonObject.get("inventory");

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //create a new file
    @FXML
    void saveFile(ActionEvent event){
        //make an instance of FileChooser
        FileChooser fc = new FileChooser();
        File file = fc.showSaveDialog(new Stage());
        //call functions to export the items in different formats
        if (file != null){

        }
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

