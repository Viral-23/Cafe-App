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
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 *
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 * @author Lily Chang
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    private Context context; //need the context to inflate the layout
    private ArrayList<Item> items; //need the data binding to each row of RecyclerView

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
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
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
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


        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            donut_name = itemView.findViewById(R.id.donut_flavor);
            donut_price = itemView.findViewById(R.id.donut_price);
            donut_image = itemView.findViewById(R.id.donut_image);
            add_donut_button = itemView.findViewById(R.id.add_donut_button);
            donut_quantity_selector = itemView.findViewById(R.id.donutQuantitySelector);
            setDonutQuantitySelector();
            parentLayout = itemView.findViewById(R.id.rowLayout);
            setDonutAddToOrderButtonOnClick(itemView); //register the onClicklistener for the button on each row.
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView
         */
        private void setDonutAddToOrderButtonOnClick(@NonNull View itemView) {
            add_donut_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Add to order");
                    Donut donut = createDonut();
                    alert.setMessage(getSubtotalString(donut));
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearDonutQuantitySelector();
                            OrdersFragment.order.addItemToOrder(donut);
                            Toast.makeText(itemView.getContext(),
                                    donut_name.getText().toString() + " added to order.", Toast.LENGTH_LONG).show();
                        }
                        //handle the "NO" click
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(),
                                    donut_name.getText().toString() + " not added to order.", Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }
        private void setDonutQuantitySelector() {
            donut_quantity_selector.setMinValue(Constants.MIN_DONUT_QUANTITY);
            donut_quantity_selector.setMaxValue(Constants.MAX_DONUT_QUANTITY);
        }

        private void clearDonutQuantitySelector() {
            donut_quantity_selector.setValue(Constants.MIN_DONUT_QUANTITY);
        }

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

