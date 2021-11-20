/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import java.io.*;

import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExportFileFormats{
    //for test cases later, to make sure that the functions are passing everything in
    boolean pass;

    //export the inventory as an HTML file, end with .html
    void exportHTML(File file, ObservableList<InventoryItems> itemsList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        bw.write("<!DOCTYPE html>\n<html>\n<style>\n");
        //styling so that I can see the lines of the table when it's open in a web browser
        bw.write("""
                table, th, td {
                \tborder:1px solid black;
                }
                """);
        bw.write("</style>\n<body>\n");
        //store in <table> element
        bw.write("<table><tr>\n<th>Serial Number</th>\n<th>Name</th>\n<th>Value</th>\n");
        bw.write("  </tr>\n");
        for (InventoryItems items : itemsList) {
            bw.write("<tr>\n");
            bw.write("\t<td>" + items.getSerialNum() + "</td>\n");
            bw.write("\t<td>" + items.getItemName() + "</td>\n");
            String doubleFormat = String.format("%.2f", items.getItemValue());
            bw.write("\t<td>" + "$" + doubleFormat + "</td>\n");
            bw.write("  </tr>\n");
        }
        bw.write("</table>\n");
        //while the table rows are not empty, print out the elements
        bw.write("\n</body>\n</html>");
        //close file/writer
        bw.close();
        pass = true;
    }

    //export the inventory as a JSON file, end with .json
    void exportJSON(File file, ObservableList <InventoryItems> itemsList){
        //create a JSONObject first
        JSONObject obj = new JSONObject();
        //make an array to store all items
        JSONArray jsonArray = new JSONArray();

        //while table is not empty,put all the values into the JSONObject
        for (InventoryItems items : itemsList){
            JSONObject tempObj = new JSONObject();
            tempObj.put("serialNumber", items.getSerialNum());
            tempObj.put("itemName", items.getItemName());
            tempObj.put("itemValue", items.getItemValue());
            //add to array
            jsonArray.add(tempObj);
        }
        //this allows for all the objects to be put in an array for printing
        obj.put("inventory", jsonArray);
        //print out the file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(obj.toJSONString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pass = true;
    }

    //export the inventory as a TSV file, end with .txt
    void exportTSV(File file, ObservableList <InventoryItems> itemsList) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            //print out the initial formatting of the table; number, name, value
            bw.write("Serial Number\tName\tValue\n");

            //for each item in the list, print out the value formatted by tab
            for (InventoryItems items : itemsList){
                bw.write(items.getSerialNum() + "\t" + items.getItemName());
                //format the value so that it shows two decimals
                String doubleFormat = String.format("%.2f", items.getItemValue());
                bw.write("\t$" + doubleFormat);
                bw.newLine();
                //close file/writer
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pass = true;
    }

    //for test cases
    public boolean getResults(){
        return pass;
    }
}
