package com.example.rucafe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project4.Constants;
import com.example.project4.MenuItem;
import com.example.project4.Order;
import com.example.project4.OrderTracker;

/**
 * This is the fragment that displays the store order details.
 * @author Viral Patel
 */
public class StoreOrdersFragment extends Fragment {

    public static OrderTracker orderTracker = new OrderTracker();
    private int selectedPosition = Constants.NOT_FOUND;

    private ListView storeOrdersListView;
    private Button cancelStoreOrdersButton;
    private Toast toast;

    /**
     * Default constructor.
     */
    public StoreOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the store orders fragment.
     * @return Fragment: returns the new instance of the store orders fragment.
     */
    public static StoreOrdersFragment newInstance() {
        StoreOrdersFragment fragment = new StoreOrdersFragment();
        return fragment;
    }

    /**
     * Initializes the state of the store orders fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the ListView of store orders and the cancel order button.
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
        View view = inflater.inflate(R.layout.fragment_store_orders, container, false);
        // Inflate the layout for this fragment
        setStoreOrdersListView(view);
        setCancelStoreOrdersButton(view);
        return view;
    }

    /**
     * Sets up the ListView which displays the store orders.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setStoreOrdersListView(View view) {
        storeOrdersListView = view.findViewById(R.id.storeOrdersListView);
        updateStoreOrdersList();
        storeOrdersListViewOnSelection();
    }

    /**
     * Updates the store orders ListView if changes have been made (order added or removed).
     */
    private void updateStoreOrdersList() {
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, orderTracker.getOrdersInTracker());
        storeOrdersListView.setAdapter(adapter);
    }

    /**
     * Handles the selection of an order in the store orders list, which updates the index
     * that indicates which order is currently selected.
     */
    private void storeOrdersListViewOnSelection() {
        storeOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item position
                selectedPosition = position;
            }
        });
    }

    /**
     * Sets up the cancel order button.
     * @param view: the view which contains all UI elements for the orders fragment.
     */
    private void setCancelStoreOrdersButton(View view) {
        cancelStoreOrdersButton = view.findViewById(R.id.cancelStoreOrdersButton);
        cancelStoreOrdersButtonOnClick();
    }

    /**
     * Sets up the on click handler for the cancel order button. Displays a toast if an error has
     * occurred, otherwise displays an alert dialog for confirmation of the cancellation.
     */
    private void cancelStoreOrdersButtonOnClick() {
        cancelStoreOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast != null)
                    toast.cancel();

                if (orderTracker.getOrdersInTracker().isEmpty()) {
                    toast = Toast.makeText(getActivity(),
                            R.string.noOrdersToBeCanceled,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                if (selectedPosition == Constants.NOT_FOUND) {
                    toast = Toast.makeText(getActivity(),
                            R.string.noOrderSelected,
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
//                    toast = Toast.makeText(getActivity(), R.string.orderCanceled,
//                            Toast.LENGTH_SHORT);
//                    toast.show();
                    cancelOrderAlert();
                }
            }
        });
    }

    /**
     * Creates the alert dialog prompting the user to confirm if they want to cancel an order.
     */
    private void cancelOrderAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cancelOrderTitle);
        builder.setMessage(R.string.cancelOrderMsg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                orderTracker.cancel(selectedPosition);
                selectedPosition = Constants.NOT_FOUND;
                updateStoreOrdersList();
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

}