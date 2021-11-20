/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.Iterator;

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
                double value = (double) jObj.get("itemValue");
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
            String line;
            while ((line = bw.readLine()) != null){
                String[] lineItems = line.split("\t");
                //to make it ignore the dollar signs in the txt file
                String newStr = lineItems[2].replaceAll("[^\\d.]+", "");
                InventoryItems inventoryItems = new InventoryItems(lineItems[0], lineItems[1], Double.parseDouble(newStr));
                itemsObList.add(inventoryItems);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //read in HTML file
    void openHTML(File file){
        Document doc;
        try {
            doc = Jsoup.parse(file, "UTF-8");
            Element table = doc.selectFirst("table");
            assert table != null;
            Iterator <Element> row = table.select("tr").iterator();

            //skip the table headers
            row.next();
            while (row.hasNext()){
                Iterator<Element> iterator = row.next().select("td").iterator();
                //set temp values to store the items as it goes through the iterator
                String number = iterator.next().text();
                String name = iterator.next().text();
                String formattedVal = iterator.next().text().replaceAll("[^\\d.]+", "");
                double val = Double.parseDouble(formattedVal);

                //add temp variables
                InventoryItems inventoryItems = new InventoryItems(number, name , val);
                itemsObList.add(inventoryItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //don't need to test because it is a getter
    public ObservableList<InventoryItems> getItemsObList(){
        return itemsObList;
    }
}
