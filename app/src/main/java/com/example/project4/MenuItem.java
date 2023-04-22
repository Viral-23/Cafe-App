package com.example.project4;

/**
 * This is an abstract class which is the superclass of all menu items.
 * @author Viral Patel, Rohan Patel
 */
public abstract class MenuItem {

    private int quantity;

    /**
     * Default constructor for a MenuItem object. It sets the quantity equal to the default value, which is 1.
     */
    public MenuItem() {
        quantity = Constants.DEFAULT_VALUE;
    }

    /**
     * Setter method for the quantity field. Updates the quantity based on the provided number.
     * @param num int: the new desired quantity.
     */
    public void setQuantity(int num) {
        quantity = num;
    }

    /**
     * Getter method for the quantity field.
     * @return int: returns the quantity of the MenuItem object.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Abstract method which calculates the price of a menu item. All MenuItem subclasses implement this class,
     * each having their own price based on the item.
     * @return double: returns the price of the item.
     */
    public abstract double itemPrice(); //subclasses must implement this method
}
