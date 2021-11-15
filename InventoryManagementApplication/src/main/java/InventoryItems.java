/*
 *  UCF COP3330 Fall 2021 Application Assignment 2 Solution
 *  Copyright 2021 Sue Lin
 */

//do not need to make tests for this class because all it does is set or get the values
public class InventoryItems {

    private String serialNum;
    private String itemName;
    private double itemValue;

    //constructor
    public InventoryItems(String serialNum, String itemName, double itemValue){
        this.serialNum = serialNum;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    //make setters and getters
    //setter and getter for item name
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    //setter and getter for serial number
    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    //setter and getter for item value
    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
    }
}
