/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

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
    void exportJSON(){
        JSONObject obj = new JSONObject();

        //while table is not empty, print out to json file
        //close file/writer
    }

    //export the inventory as a TSV file, end with .txt
    void exportTSV(File file) throws FileNotFoundException {
        //print out the initial formatting of the table; number, name, value
        PrintWriter out = new PrintWriter(file);
        StringBuilder formatedData = new StringBuilder();
        //for each row on the table while it does not equal null
            //print it out (array val 0-2)
            //separate the items with tab
        //close file/writer
    }
}
