import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerTest {

    ObservableList<InventoryItems> tesInventory = FXCollections.observableArrayList(
            new InventoryItems("A-123-456-789", "Tea", 7.77),
            new InventoryItems("s-quA-reE-NIx", "Hit MMORPG Final Fantasy XIV", 210.0),
            new InventoryItems("Q-5h7-586-YuH", "Animal Crossing", 59.99)
    );

    @Test
    public void testingStorageCapacity(){
        ApplicationController applicationController = new ApplicationController();
        int actual = 0;
        int expected = 1024;

        Random r = new Random();
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 1; i < 1025; i ++){
            char one = (char)(r.nextInt(validCharacters.length()));
            char two = (char)(r.nextInt(validCharacters.length()));
            char three = (char)(r.nextInt(validCharacters.length()));
            String serial = "A-123-456-" + one + two + three;
            InventoryItems items = new InventoryItems(serial, "stress testing", 1024);
            applicationController.inventoryItemsObservableList.add(items);
            actual = applicationController.inventoryItemsObservableList.size();
        }
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd(){
        ApplicationController applicationController = new ApplicationController();
        InventoryItems items = new InventoryItems("S-845-8lu-oi8", "test", 111);

        applicationController.addItemHelper(items);

        assertEquals(1, applicationController.inventoryItemsObservableList.size());
    }

    //test to see if the formatting is valid
    @Test
    public void serialFormatErrorTest(){
        ErrorChecker ec = new ErrorChecker();
        String tempString = "A-254-iuh-999";

        //sec.serialErrors(tempString, tesInventory);
        assertTrue(ec.serialErrors(tempString, tesInventory));
    }

    //test to see if the serial is a duplicate
    @Test
    public void serialDuplicateErrorTest(){
        ErrorChecker ec = new ErrorChecker();
        String tempString = "A-123-456-789";
        ec.serialErrors(tempString, tesInventory);

        assertFalse(ec.serialErrors(tempString, tesInventory));
    }

    @Test
    public void nameErrorTest(){
        ErrorChecker ec = new ErrorChecker();
        String tempString = "b";

        assertFalse(ec.nameErrors(tempString));
    }

    @Test
    public void valueErrorTest(){
        ErrorChecker ec = new ErrorChecker();
        double tempString = -1;

        assertFalse(ec.valueErrors(tempString));
    }

}