/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class SerialErrorChecker {

    boolean serialErrors(String tempItem, ObservableList<InventoryItems> inventoryItemsObservableList){
        //regex pattern to ensure that the pattern of the serial number is following the correct format
        String pattern = "[a-zA-Z]-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}-[a-zA-Z0-9]{3}";

        boolean status = true;
        //this loops checks to make sure that there's no duplicate values
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
                    status = false;
                }
            }
        }

        //this is the loop that checks to make sure that the format is right
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
            status = false;
        }

        return status;
    }

}
