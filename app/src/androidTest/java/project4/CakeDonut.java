package project4;

/**
 * This is a class that extends the Donut class, which allows for the creation of a cake donut object.
 * @author Viral Patel, Rohan Patel
 */
public class CakeDonut extends Donut {

    /**
     * Default constructor for the cake donut object.
     */
    public CakeDonut() {}

    /**
     * Constructor for cake donut object, contains flavor. Also contains a quantity initialized to one in the
     * superclass MenuItem.
     * @param flavor String: flavor of the donut.
     */
    public CakeDonut(String flavor) {
        super(flavor);
    }

    /**
     * Calculates the price of a cake donut.
     * @return double: returns the price of the cake donut.
     */
    @Override
    public double itemPrice() {
        return Constants.CAKE_DONUT_PRICE * super.getQuantity();
    }

    /**
     * Overrides the toString method, which formats the cake donut object.
     * @return String: returns the formatted cake donut object, which includes the quantity, flavor, and price.
     */
    @Override
    public String toString() {
        String ret = super.getQuantity() + "x\t Cake Donut (" + super.getFlavor() + ")\n";
        String price = String.format("%.2f", itemPrice());
        ret += String.format("%1$204s", "$" + price);
        return ret + "\n";
    }
}
