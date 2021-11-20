/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class ImportFile {
    //create a new observable list to be filled with the new data
    ObservableList <InventoryItems> itemsObList = FXCollections.observableArrayList();

    //read in JSON file
    void openJson(File file){
        JSONParser parser = new JSONParser();
        try {
            //direct the parser to the file that is being read
            Object object = parser.parse((new FileReader(file)));
            //create a new object to read the values in
            JSONObject jsonObject = (JSONObject) object;
            //set a JSONArray to equal the array from the file, so it can be accessed
            JSONArray itemsArray = (JSONArray) jsonObject.get("inventory");
            for (Object items : itemsArray){
                JSONObject jObj = (JSONObject) items;
                String number = (String) jObj.get("serialNumber");
                String name = (String) jObj.get("itemName");
                Double value = (double) jObj.get("itemValue");
                InventoryItems inventoryItems = new InventoryItems(number, name, value);
                itemsObList.add(inventoryItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //reads in tsv and sets temp observable list to it's elements
    void openTSV(File file){
        try(BufferedReader bw = new BufferedReader(new FileReader(file))){
            bw.readLine(); //reads in the first line
            String line = null;
            while ((line = bw.readLine()) != null){
                String[] lineItems = line.split("\t");
                //to make it ignore the dollar signs in the txt file
                String newStr = lineItems[2].replaceAll("[^\\d.]+", "");
                InventoryItems inventoryItems = new InventoryItems(lineItems[0], lineItems[1], Double.parseDouble(newStr));
                itemsObList.add(inventoryItems);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read ing HTML file
    void openHTML(File file){

    }

    //don't need to test because it is a getter
    public ObservableList<InventoryItems> getItemsObList(){
        return itemsObList;
    }
}
