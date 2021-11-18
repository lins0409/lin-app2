/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ImportFile {

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

    void openTSV(){

    }
}
