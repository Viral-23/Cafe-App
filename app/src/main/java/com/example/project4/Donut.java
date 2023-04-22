package com.example.project4;

/**
 * This is a class that extends the MenuItem class, which allows for the creation of a donut object.
 * @author Viral Patel, Rohan Patel
 */
public abstract class Donut extends MenuItem {
    private String flavor;

    /**
     * Default constructor for the donut object.
     */
    public Donut(){}

    /**
     * Constructor for donut object, contains flavor. Also contains a quantity initialized to one in the
     * superclass MenuItem.
     * @param flavor String: flavor of the donut.
     */
    public Donut(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Getter method to retrieve the flavor of a donut.
     * @return String: returns the flavor of the donut.
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Setter method to set the flavor of a donut.
     * @param flavor String: the flavor of the donut that is wish to be set/updated.
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Method from parent class MenuItem. It is also abstract in the donut class as donut is an abstract class
     * with three subclasses.
     * @return double: returns the price of the donut.
     */
    public abstract double itemPrice();
}
