/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ImportFileTest {
    //inventory from demoTSVFile
    ObservableList<InventoryItems> tsvInventory = FXCollections.observableArrayList(
            new InventoryItems("A-123-456-789", "Tea", 7.77),
            new InventoryItems("G-0j0-SAT-0Ru", "Gojo Figure", 300.0),
            new InventoryItems("Q-5h7-586-YuH", "Animal Crossing", 59.99),
            new InventoryItems("s-quA-reE-NIx", "Hit MMORPG Final Fantasy XIV", 210.0),
            new InventoryItems("t-0kR-3VN-End", "Mikey Nendoroid", 60.84)
    );

    //inventory from demoJSONFile
    ObservableList<InventoryItems> jsonInventory = FXCollections.observableArrayList(
            new InventoryItems("A-123-456-789", "Tea", 7.77),
            new InventoryItems("S-yTU-7Yh-86R", "Picture Frame", 12.99),
            new InventoryItems("F-iuy-98i-00i", "headphones", 150.00)
    );

    //inventory from demoHTMLFile
    ObservableList<InventoryItems> htmlInventory = FXCollections.observableArrayList(
            new InventoryItems("A-123-456-789", "book", 13.00),
            new InventoryItems("B-u88-b5e-547", "Wacom Tablet", 200.00),
            new InventoryItems("G-iu9-555-87u", "Smart TV", 330.00),
            new InventoryItems("F-45u-YU9-8tu", "chocolate bar", 4.99)
    );

    @Test
    public void testImportTSV(){
        boolean actual = false;
        File file = new File("data/demoTSVFile.txt");
        ImportFile imp = new ImportFile();
        imp.openTSV(file);
        for (int i = 0; i < tsvInventory.size(); i++){
            if((imp.itemsObList.get(i).getSerialNum().toString().equals(tsvInventory.get(i).getSerialNum().toString()))){
                actual = true;
            }
            else { actual = false;}
        }
        assertEquals(true, actual);
    }

    @Test
    public void testImportJSON(){
        boolean actual = false;
        File file = new File("data/demoJSONFile.json");
        ImportFile imp = new ImportFile();
        imp.openJson(file);

        for (int i = 0; i < jsonInventory.size(); i++){
            if((imp.itemsObList.get(i).getSerialNum().toString().equals(jsonInventory.get(i).getSerialNum().toString()))){
                actual = true;
            }
            else { actual = false;}
        }
        assertEquals(true, actual);
    }

    @Test
    public void testImportHTML(){
        boolean actual = false;
        File file = new File("data/demoHTMLFile.html");
        ImportFile imp = new ImportFile();
        imp.openHTML(file);
        for (int i = 0; i < htmlInventory.size(); i++){
            if((imp.itemsObList.get(i).getSerialNum().toString().equals(htmlInventory.get(i).getSerialNum().toString()))){
                actual = true;
            }
            else { actual = false;}
        }
        assertEquals(true, actual);
    }
}