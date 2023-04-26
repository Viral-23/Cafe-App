package com.example.rucafe;

/**
 * Item class, which is used to create item objects that store all the necessary information for
 * donuts to be displayed in a recycler view.
 * @author Viral Patel
 */
public class Item {
    private String itemName;
    private int image;
    private String unitPrice;

    /**
     * Constructor for the items stored in the donut recycler view.
     * @param itemName: the name for the donut.
     * @param image: the image for the donut.
     * @param unitPrice: the price for the donut.
     */
    public Item(String itemName, int image, String unitPrice) {
        this.itemName = itemName;
        this.image = image;
        this.unitPrice = unitPrice;
    }

    /**
     * Getter method that returns the item name of an item.
     * @return the item name of an item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Getter method that returns the image of an item.
     * @return the image of an item.
     */
    public int getImage() {
        return image;
    }

    /**
     * Getter method that returns the unit price.
     * @return the unit price of the item.
     */
    public String getUnitPrice() {
        return unitPrice;
    }
}
