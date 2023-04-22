package com.example.project4;

import java.util.ArrayList;

/**
 * This is a class that keeps track of a customers order and order number.
 * @author Viral Patel, Rohan Patel
 */

public class Order {
    private int orderNumber;

    private double total;
    private ArrayList<MenuItem> itemsInOrder;

    /**
     * Default constructor for the order object. Initializes a new ArrayList, sets orderNumber to the default value
     * (which is 1), and sets the total of the order to no cost, or 0.
     */
    public Order() {
        itemsInOrder = new ArrayList<>();
        orderNumber = Constants.DEFAULT_VALUE;
        total = Constants.NO_COST;
    }

    /**
     * Copy constructor for the order object.
     * @param order: the order that is being copied.
     */
    public Order(Order order) {
        orderNumber = order.orderNumber;
        itemsInOrder = order.itemsInOrder;
    }

    /**
     * Constructor for the order object, which contains the order number and list of menu items in the order.
     * @param orderNumber int: The order number of the current customer.
     * @param itemsInOrder Object: The list of menu items (donut and coffee) in the order.
     */
    public Order(int orderNumber, ArrayList<MenuItem> itemsInOrder) {
        this.orderNumber = orderNumber;
        this.itemsInOrder = itemsInOrder;
    }

    /**
     * Setter method, which sets the order number of the order to the specified order number.
     * @param orderNumber int: the order number that will be assigned to the order.
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Getter method, which retrieves the order number of the order.
     * @return int: returns the order number.
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Getter method, which retrieves all items in the order.
     * @return ArrayList: the arraylist of menu items in the order.
     */
    public ArrayList<MenuItem> getItemsInOrder() {
        return itemsInOrder;
    }

    /**
     * Adds a menu item to the order, specified by menuItem.
     * @param menuItem: the MenuItem being added to the order.
     */
    public void addItemToOrder(MenuItem menuItem) {
        itemsInOrder.add(menuItem);
    }

    /**
     * Removes a menu item from the order, specified by an index.
     * @param index: the index of the menu item desired to be removed.
     */
    public void removeItemInOrder(int index) {
        itemsInOrder.remove(index);
    }

    /**
     * Calculates the subtotal of the order.
     * @return double: returns the subtotal amount of the order.
     */
    public double calculateSubtotal() {
        double subtotal = 0;
        for (MenuItem menuItem: itemsInOrder)
            subtotal += menuItem.itemPrice();

        return subtotal;
    }

    /**
     * Calculates the sales tax of the order, which is based on the NJ sales tax amount.
     * @return double: returns the sales tax of the order.
     */
    public double calculateSalesTax() {
        double subtotal = calculateSubtotal();
        double tax = subtotal * Constants.NJ_SALES_TAX;

        return tax;
    }

    /**
     * Calculates the total of the order, which is the subtotal and tax added together.
     * @return double: returns the total of the order.
     */
    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        double tax = subtotal * Constants.NJ_SALES_TAX;

        total = subtotal + tax;
        return total;
    }

    /**
     * Overrides the toString method, displays items in order and their prices in a list view.
     * @return String: the string that contains the list of formatted items in the order.
     */
    @Override
    public String toString() {
        calculateTotal();
        String ret = "Order #" + orderNumber + "\n\n";

        for (MenuItem menuItem : itemsInOrder) {
            ret += menuItem.toString();
        }

        String formattedTotal = String.format("%.2f", total);
        ret += String.format("\n%1$222s\n", "Total: $" + formattedTotal);

        return ret;
    }
}
