package com.example.rucafe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project4.Coffee;
import com.example.project4.Constants;
import com.example.project4.Donut;
import com.example.project4.MenuItem;
import com.example.project4.Order;

/**
 * This is the fragment that displays the customer order details and the navigation
 * to the store orders fragment.
 * @author Viral Patel
 */
public class OrdersFragment extends Fragment {

    public static Order order = new Order();
    private int selectedPosition = Constants.NOT_FOUND;

    private ListView currentOrderListView;

    private ImageButton storeOrdersButton;

    private Button removeFromOrderButton, placeOrderButton;

    private TextView orderSubtotalText, orderSalesTaxText, orderTotalText, subtotalText,
            salesTaxText, totalForOrderText;
    Toast toast;


    /**
     * Default Constructor
     */
    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the orders fragment.
     * @return Fragment: returns the new instance of the orders fragment.
     */
    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    /**
     * Initializes the state of the orders fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the listview which displays the list of items in the user's order and sets up
     * the textview which displays the payment details. Also sets up the button to navigate
     * to the store orders fragment.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View: returns the view, which contains all the UI elements for the orders fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        setStoreOrdersButton(view);
        setRemoveFromOrderButton(view);
        setTextViews(view);
        setCurrentOrderListView(view);
        setPlaceOrderButton(view);

        return view;
    }

    /**
     * Sets up the ListView for the user's order.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setCurrentOrderListView(View view) {
        currentOrderListView = view.findViewById(R.id.currentOrderListView);
        updateOrderList();
        currentOrderListViewSelection();
    }

    /**
     * Sets up the TextViews which contain the payment details for the user's order.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setTextViews(View view) {
        orderSubtotalText = view.findViewById(R.id.orderSubtotalText);
        orderSalesTaxText = view.findViewById(R.id.orderSalesTaxText);
        orderTotalText = view.findViewById(R.id.orderTotalText);

        subtotalText = view.findViewById(R.id.subtotalText);
        salesTaxText = view.findViewById(R.id.salesTaxText);
        totalForOrderText = view.findViewById(R.id.totalForOrderText);
    }

    /**
     * Handles the selection of an item in the order, which updates the index that indicates which
     * item is currently selected.
     */
    private void currentOrderListViewSelection() {
        currentOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item position
                selectedPosition = position;
            }
        });
    }

    /**
     * Set up the store orders button, which will allow the user to navigate to a list of
     * store orders.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setStoreOrdersButton(View view) {
        storeOrdersButton = view.findViewById(R.id.storeOrdersButton);
        StoreOrdersFragment storeOrdersFragment = new StoreOrdersFragment();
        storeOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(storeOrdersFragment);
            }
        });
    }

    /**
     * Sets up the remove from order button, which allows the user to remove an item from their
     * order. Displays a toast with relevant error or confirmation message.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setRemoveFromOrderButton(View view) {
        removeFromOrderButton = view.findViewById(R.id.removeFromOrderButton);
        removeFromOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null)
                    toast.cancel();

                if (order.getItemsInOrder().isEmpty()) {
                    toast = Toast.makeText(getActivity(),
                            R.string.noItemToBeRemovedOrPlaced,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (selectedPosition == Constants.NOT_FOUND) {
                    toast = Toast.makeText(getActivity(),
                            R.string.noItemSelected,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    toast = Toast.makeText(getActivity(), R.string.itemRemoved,
                            Toast.LENGTH_SHORT);
                    toast.show();

                    order.removeItemInOrder(selectedPosition);
                    selectedPosition = Constants.NOT_FOUND;
                    updateOrderList();
                }
            }
        });
    }

    /**
     * Sets up the place order button, which will allow the user to place their order. Displays
     * a toast for error messages, otherwise displays an alert dialog with order number to show
     * that the order has been placed.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setPlaceOrderButton(View view) {
        placeOrderButton = view.findViewById(R.id.placeOrderButton);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null)
                    toast.cancel();

                if (order.getItemsInOrder().isEmpty()) {
                    toast = Toast.makeText(getActivity(),
                            R.string.noItemToBeRemovedOrPlaced,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    placeOrderAlert();
                }
            }
        });
    }

    /**
     * Creates the alert dialog when the user goes to place their order. Only will happen if
     * there are no errors.
     */
    private void placeOrderAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.placeOrderTitle);
        builder.setMessage(R.string.placeOrderMsg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                StoreOrdersFragment.orderTracker.add(order);
//                toast = Toast.makeText(getActivity(), R.string.orderPlaced,
//                        Toast.LENGTH_SHORT);
//                toast.show();
                placeOrderConfirmationAlert();
                order = new Order();
                updateOrderList();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Creates an alert dialog notifying the user that their order has successfully been placed.
     * Shows the order number of their order.
     */
    private void placeOrderConfirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.placeOrderConfirmationTitle);
        String orderNum = Integer.toString(order.getOrderNumber());
        String orderText = getResources().getString(R.string.placeOrderConfirmationMsg);
        builder.setMessage(orderText + orderNum);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Updates the ListView of MenuItems if changes were made. Sets or clears the payment details
     * if possible.
     */
    private void updateOrderList() {
        ArrayAdapter<MenuItem> adapter = new ArrayAdapter<>(getContext(),
        android.R.layout.simple_list_item_1, order.getItemsInOrder());
        currentOrderListView.setAdapter(adapter);
        if (order.getItemsInOrder().isEmpty()) {
            clearReceipt();
        }
        else {
            setReceipt();
        }
    }

    /**
     * Helper method to clear the payment details, if not enough information.
     */
    private void clearReceipt() {
        orderSubtotalText.setText("");
        orderSalesTaxText.setText("");
        orderTotalText.setText("");

        subtotalText.setText("");
        salesTaxText.setText("");
        totalForOrderText.setText("");
    }

    /**
     * Helper method to set the payment details, if enough information.
     */
    private void setReceipt() {
        orderSubtotalText.setText("$" + String.format("%.2f", order.calculateSubtotal()));
        orderSalesTaxText.setText("$" + String.format("%.2f", order.calculateSalesTax()));
        orderTotalText.setText("$" + String.format("%.2f", order.calculateTotal()));

        subtotalText.setText(R.string.subtotalMsg);
        salesTaxText.setText(R.string.salesTaxMsg);
        totalForOrderText.setText(R.string.totalMsg);
    }

    /**
     * Helper method which replaces the fragment being displayed in the activity.
     * @param fragment: the fragment to be displayed.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}