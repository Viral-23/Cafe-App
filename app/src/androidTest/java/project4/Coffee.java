package project4;

import java.util.ArrayList;

/**
 * This is a class that allows for the creation of a coffee object.
 * @author Viral Patel, Rohan Patel
 */
public class Coffee extends MenuItem {
    private String cupSize;
    private ArrayList<String> addIns;

    /**
     * Default constructor for coffee class, allows new coffee object to be created without any parameters.
     */
    public Coffee() {}

    /**
     * Constructor for the Coffee object. Creates a coffee object, which contains a cupSize and a list of add-ins.
     * All coffee objects also have a quantity, which is initialized to one in the superclass constructor found in
     * MenuItem.
     * @param cupSize: String that determines the cup size for coffee.
     * @param addIns: List of strings that contains all add-ins for the coffee.
     */
    public Coffee(String cupSize, ArrayList<String> addIns) {
        this.cupSize = cupSize;
        this.addIns = addIns;
    }

    /**
     * Getter method to retrieve the cup size of a coffee object.
     * @return String: returns the cup size for the coffee.
     */
    public String getCupSize() {
        return cupSize;
    }

    /**
     * Getter method to retrieve the add-ins for a coffee object.
     * @return ArrayList: returns list of strings which are the add-ins for the coffee.
     */
    public ArrayList<String> getAddIns() {
        return addIns;
    }

    /**
     * Calculates the price of a coffee.
     * @return double: returns the price of the coffee.
     */
    public double itemPrice() {
        double total = 0;
        switch (cupSize) {
            case "Short": {
                total = Constants.SHORT_CUP_COST;
                break;
            }
            case "Tall": {
                total = Constants.TALL_CUP_COST;
                break;
            }
            case "Grande": {
                total = Constants.GRANDE_CUP_COST;
                break;
            }
            case "Venti": {
                total = Constants.VENTI_CUP_COST;
                break;
            }
            default: {
                total = Constants.NO_COST;
                break;
            }
        }

        total += (addIns.size() * Constants.ADD_IN_COST);

        return total * super.getQuantity();
    }

    /**
     * Overrides the toString method to display the Coffee object in the form of a string.
     * @return String: returns the Coffee object formatted as a String, which includes the quantity,
     * cup size, list of add-ins, and the price.
     */
    @Override
    public String toString() {
        String ret = super.getQuantity() + "x\t" + cupSize + " black coffee";

        if (addIns.size() > 0) {
            ret += " with ";
            for (int i = 0; i < addIns.size(); i++) {
                ret += addIns.get(i);

                if (addIns.size() > 1 && i != addIns.size() - 1) {
                    ret += ", ";
                    if (i == addIns.size() - 2) {
                        ret += " and ";
                    }

                }
            }
        }

        ret += "\n";


        String price = String.format("%.2f", itemPrice());
        ret += String.format("%1$204s", "$" + price);


        return ret + "\n";
    }
}
