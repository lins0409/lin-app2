import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerTest {

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

}