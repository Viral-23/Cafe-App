package com.example.rucafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.CakeDonut;
import com.example.project4.Constants;
import com.example.project4.Donut;
import com.example.project4.DonutHole;
import com.example.project4.YeastDonut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is an Adapter class to be used to instantiate an adapter for the donut RecyclerView.
 * @author Viral Patel
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    private Context context; //need the context to inflate the layout
    private ArrayList<Item> items; //need the data binding to each row of RecyclerView

    /**
     * Constructor for the donut recycler view.
     * @param context: the host activity/fragment.
     * @param items: item object which stores the name, image, and price of each donut.
     */
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * This method will inflate the row layout for the items in the donut RecyclerView.
     * @param parent: The parent ViewGroup of the item view.
     * @param viewType: The type of the view to inflate.
     * @return ItemsHolder: returns a new ItemsHolder instance representing the inflated view.
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view, parent, false);

        return new ItemsHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder: The instance of ItemsHolder.
     * @param position: The index of the item in the list of items.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        //assign values for each row
        holder.donut_name.setText(items.get(position).getItemName());
        holder.donut_price.setText(items.get(position).getUnitPrice());
        holder.donut_image.setImageResource(items.get(position).getImage());
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size(); //number of MenuItem in the array list.
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView donut_name, donut_price;
        private ImageView donut_image;
        private Button add_donut_button;

        private NumberPicker donut_quantity_selector;
        private ConstraintLayout parentLayout; //this is the row layout

        /**
         * Sets up the ItemsHolder.
         * @param itemView
         */
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            donut_name = itemView.findViewById(R.id.donut_flavor);
            donut_price = itemView.findViewById(R.id.donut_price);
            donut_image = itemView.findViewById(R.id.donut_image);
            add_donut_button = itemView.findViewById(R.id.add_donut_button);
            donut_quantity_selector = itemView.findViewById(R.id.donutQuantitySelector);
            setDonutQuantitySelector();
            parentLayout = itemView.findViewById(R.id.rowLayout);
            setDonutAddToOrderButtonOnClick(itemView);
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView: The view of the row that contains the button.
         */
        private void setDonutAddToOrderButtonOnClick(@NonNull View itemView) {
            add_donut_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle(R.string.addToOrder);
                    Donut donut = createDonut();
                    alert.setMessage(getSubtotalString(donut));
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (DonutFragment.donutToast != null) {
                                DonutFragment.donutToast.cancel();
                            }
                            clearDonutQuantitySelector();
                            OrdersFragment.order.addItemToOrder(donut);
                            String msg = itemView.getResources().getString( R.string.addedToOrder);
                            DonutFragment.donutToast = Toast.makeText(itemView.getContext(),
                                    donut_name.getText().toString() + msg, Toast.LENGTH_SHORT);
                            DonutFragment.donutToast.show();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (DonutFragment.donutToast != null) {
                                DonutFragment.donutToast.cancel();
                            }
                            String msg = itemView.getResources().getString( R.string.notAddedToOrder);
                            DonutFragment.donutToast = Toast.makeText(itemView.getContext(),
                                    donut_name.getText().toString() + msg, Toast.LENGTH_SHORT);
                            DonutFragment.donutToast.show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }

        /**
         * Sets up the donut quantity number picker.
         */
        private void setDonutQuantitySelector() {
            donut_quantity_selector.setMinValue(Constants.MIN_DONUT_QUANTITY);
            donut_quantity_selector.setMaxValue(Constants.MAX_DONUT_QUANTITY);
        }

        /**
         * Clears the donut quantity number picker, sets the selected number to the default value
         * of 1.
         */
        private void clearDonutQuantitySelector() {
            donut_quantity_selector.setValue(Constants.MIN_DONUT_QUANTITY);
        }

        /**
         * Creates a donut object based on the name of the donut. Sets the quantity based on the
         * number picker.
         * @return Donut: returns the created donut object.
         */
        private Donut createDonut() {
            String donutFullName = (String) donut_name.getText();

            String[] donutNameArray = donutFullName.split(" ");
            String[] flavorArray = Arrays.copyOfRange(donutNameArray, 0,
                    donutNameArray.length - 2);
            String donutFlavor = String.join(" ", flavorArray);
            String donutType = donutNameArray[donutNameArray.length - 2] + " " +
                    donutNameArray[donutNameArray.length - 1];

            Donut donut = null;
            switch (donutType) {
                case "Yeast Donut": {
                    donut = new YeastDonut();
                    break;
                }
                case "Cake Donut": {
                    donut = new CakeDonut();
                    break;
                }
                case "Donut Hole": {
                    donut = new DonutHole();
                    break;
                }
            }

            donut.setFlavor(donutFlavor);
            donut.setQuantity(donut_quantity_selector.getValue());

            return donut;
        }

        /**
         * Gets the subtotal string to display in the alert dialog.
         * @param donut: The donut object whose information is being displayed.
         * @return String: returns the string to be displayed.
         */
        private String getSubtotalString(Donut donut) {
            String donutOrderString = donut.toString();
            String[] donutOrderParts = donutOrderString.split("\\$");
            double itemPrice = Double.parseDouble(donutOrderParts[1]);
            String newCoffeeOrderString =
                    donutOrderParts[0] + "Subtotal: $" + String.format("%.2f", itemPrice);

            return newCoffeeOrderString;
        }
    }

}

