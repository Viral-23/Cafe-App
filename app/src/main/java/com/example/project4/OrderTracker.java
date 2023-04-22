package com.example.project4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a class that allows for the creation of an OrderTracker object.
 * @author Viral Patel, Rohan Patel
 */

public class OrderTracker {

    private ArrayList<Order> orderTracker;

    /**
     * Default constructor for an OrderTracker object.
     */
    public OrderTracker() {
        orderTracker = new ArrayList<>();
    }

    /**
     * Adds an order to the list of orders, assigning an order number based on its position in the list.
     * @param order Order: the order being added.
     */
    public void add(Order order) {
        order.setOrderNumber(orderTracker.size() + 1);
        orderTracker.add(order);
    }

    /**
     * Cancels (removes) the order from the list of orders. The order numbers are reassigned to the other orders
     * in the list to account for the removal of the order.
     * @param index int: the index of the order that is being removed
     */
    public void cancel(int index) {
        orderTracker.remove(index);

        for (int i = 0; i < orderTracker.size(); i++) {
            orderTracker.get(i).setOrderNumber(i + 1);
        }
    }

    /**
     * Getter method, retrieves the arraylist of orders in the OrderTracker object.
     * @return ArrayList: returns the arraylist of Order objects.
     */
    public ArrayList<Order> getOrdersInTracker() {
        return orderTracker;
    }

    /**
     * Prints all orders in the list.
     * @return String: returns the string which contains the printed list of formatted orders.
     */
    public String printOrders() {
        String ret = "";
        for (int i = 0; i < orderTracker.size(); i++)
            ret += (orderTracker.get(i).toString() + "\n\n");

        return ret;
    }

    /**
     * Method that exports all orders in the OrderTracker to a txt file named storeOrders.txt. Formatting is retained.
     * @return String: returns the confirmation/error message based on if the orders sucessfully exported or not.
     */
    public String exportStoreOrders() {
        if (orderTracker.isEmpty()) {
            return "No orders to be export.";
        }
        try {
            FileWriter writer = new FileWriter("src/main/resources/rucafe/project4/storeOrders.txt");
            String export = printOrders();
            writer.write(export);
            writer.close();
            return "Orders successfully exported.";
        }
        catch (IOException e) {
            return "File does not exist.";
        }
    }
}
