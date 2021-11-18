/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import java.io.*;

import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class ExportFileFormats {
    //export the inventory as an HTML file, end with .html
    void exportHTML(){
        //store in <table> element
        //while the table rows are not empty, print out the elements
        //close file/writer
    }

    //export the inventory as a JSON file, end with .json
    void exportJSON(File file, ObservableList <InventoryItems> itemsList){
        JSONObject obj = new JSONObject();

        //while table is not empty,put all of the values into the JSONbject
        //print out the file
        //close file/writer
    }

    //export the inventory as a TSV file, end with .txt
    void exportTSV(File file, ObservableList <InventoryItems> itemsList) throws FileNotFoundException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            //print out the initial formatting of the table; number, name, value
            bw.write("Serial Number\tName\tValue");

            //for each item in the list, print out the value formatted by tab
            for (InventoryItems items : itemsList){
                bw.write(items.getSerialNum() + "\t" + items.getItemName());
                //format the value so that it shows two decimals
                String doubleFormat = String.format("%2f", items.getItemValue());
                bw.write("\t$" + doubleFormat);
                bw.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
