/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import java.io.*;

import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class ExportFileFormats{

    //export the inventory as an HTML file, end with .html
    void exportHTML(File file, ObservableList<InventoryItems> itemsList) throws IOException {
        FileWriter fw = new FileWriter(file);

        fw.write("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
        fw.write("</head>\n<body>\n");
        //store in <table> element
        fw.write("<table>");
        fw.write("<tr>\n");
        fw.write("<th>Serial Number</th>\n");
        fw.write("<th>Name</th>\n");
        fw.write("<th>Value</th>\n");
        fw.write("  </tr>\n");
        for (InventoryItems items : itemsList) {
            fw.write("<tr>\n");
            fw.write("\t<td>" + items.getSerialNum() + "</td>\n");
            fw.write("\t<td>" + items.getItemName() + "</td>\n");
            fw.write("\t<td>" + items.getItemValue() + "</td>\n");
            fw.write("  </tr>\n");
        }
        fw.write("</table>\n");
        //while the table rows are not empty, print out the elements
        fw.write("\n</body>\n</html>");
        //close file/writer
        fw.close();
    }

    //export the inventory as a JSON file, end with .json
    //not working, file won't appear in docs
    void exportJSON(File file, ObservableList <InventoryItems> itemsList){
        JSONObject obj = new JSONObject();

        //while table is not empty,put all of the values into the JSONbject
        for (InventoryItems items : itemsList){
            obj.put("Serial number", items.getSerialNum());
            obj.put("Item name", items.getItemName());
            obj.put("Item value", items.getItemValue());
        }
        //print out the file
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(obj.toJSONString());
            //close file/writer
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //export the inventory as a TSV file, end with .txt
    void exportTSV(File file, ObservableList <InventoryItems> itemsList) throws FileNotFoundException {

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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
