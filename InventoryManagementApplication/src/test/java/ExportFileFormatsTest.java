/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ExportFileFormatsTest {

    @Test
    public void testOutputTSV() throws IOException {
        File f = new File("data/exportTestText/testingTSVActual.txt");
        Path input = Paths.get("data/exportTestText/testingTSVExpected.txt");
        ExportFileFormats exp = new ExportFileFormats();
        String serialNumber = "V-ui9-000-t6Y";
        String itemName = "ear buds";
        double itemValue = 13.99;
        InventoryItems items = new InventoryItems(serialNumber, itemName, itemValue);
        ObservableList<InventoryItems> inventoryItemsObservableList = FXCollections.observableArrayList();
        inventoryItemsObservableList.add(items);

        exp.exportTSV(f, inventoryItemsObservableList);
        Path path = Paths.get("data/exportTestText/testingTSVActual.txt");

        long actual = Files.mismatch(input, path);
        long expected = -1;

        assertEquals(expected, actual);
    }

    @Test
    public void testOutputJSON() throws IOException {
        File f = new File("data/exportTestText/testingJSONActual.json");
        Path input = Paths.get("data/exportTestText/testingJSONExpected.json");
        ExportFileFormats exp = new ExportFileFormats();
        String serialNumber = "V-ui9-000-t6Y";
        String itemName = "ear buds";
        double itemValue = 13.99;
        InventoryItems items = new InventoryItems(serialNumber, itemName, itemValue);
        ObservableList<InventoryItems> inventoryItemsObservableList = FXCollections.observableArrayList();
        inventoryItemsObservableList.add(items);

        exp.exportJSON(f, inventoryItemsObservableList);
        Path path = Paths.get("data/exportTestText/testingJSONActual.json");

        long actual = Files.mismatch(input, path);
        long expected = -1;

        assertEquals(expected, actual);
    }

    @Test
    public void testOutputHTML() throws IOException {
        File f = new File("data/exportTestText/testingHTMLActual.html");
        Path input = Paths.get("data/exportTestText/testingHTMLExpected.html");
        ExportFileFormats exp = new ExportFileFormats();
        String serialNumber = "V-ui9-000-t6Y";
        String itemName = "ear buds";
        double itemValue = 13.99;
        InventoryItems items = new InventoryItems(serialNumber, itemName, itemValue);
        ObservableList<InventoryItems> inventoryItemsObservableList = FXCollections.observableArrayList();
        inventoryItemsObservableList.add(items);

        exp.exportTSV(f, inventoryItemsObservableList);
        Path path = Paths.get("data/exportTestText/testingHTMLActual.html");

        long actual = Files.mismatch(input, path);
        long expected = -1;

        assertEquals(expected, actual);
    }
}