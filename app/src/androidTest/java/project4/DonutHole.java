package project4;

/**
 * This is a class that extends the Donut class, which allows for the creation of a donut hole object.
 * @author Viral Patel, Rohan Patel
 */
public class DonutHole extends Donut {

    /**
     * Default constructor for the donut hole object.
     */
    public DonutHole(){}

    /**
     * Constructor for donut hole object, contains flavor. Also contains a quantity initialized to one in the
     * superclass MenuItem.
     * @param flavor String: flavor of the donut.
     */
    public DonutHole(String flavor) {
        super(flavor);
    }

    /**
     * Calculates the price of a donut hole.
     * @return double: returns the price of the donut hole.
     */
    @Override
    public double itemPrice() {
        return Constants.DONUT_HOLE_PRICE * super.getQuantity();
    }

    /**
     * Overrides the toString method, which formats the donut hole object.
     * @return String: returns the formatted donut hole object, which includes the quantity, flavor, and price.
     */
    @Override
    public String toString() {
        String ret = super.getQuantity() + "x\t Donut Hole (" + super.getFlavor() + ")\n";
        String price = String.format("%.2f", itemPrice());
        ret += String.format("%1$204s", "$" + price);
        return ret + "\n";
    }
}
