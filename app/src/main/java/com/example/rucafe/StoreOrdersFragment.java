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
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreOrdersFragment extends Fragment {

    public static OrderTracker orderTracker = new OrderTracker();
    private int selectedPosition = Constants.NOT_FOUND;

    private ListView storeOrdersListView;
    private Button cancelStoreOrdersButton;
    private Toast toast;

    public StoreOrdersFragment() {
        // Required empty public constructor
    }
    public static StoreOrdersFragment newInstance() {
        StoreOrdersFragment fragment = new StoreOrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_orders, container, false);
        // Inflate the layout for this fragment
        setStoreOrdersListView(view);
        setCancelStoreOrdersButton(view);
        return view;
    }

    private void setStoreOrdersListView(View view) {
        storeOrdersListView = view.findViewById(R.id.storeOrdersListView);
        updateStoreOrdersList();
        storeOrdersListViewOnSelection();
    }

    private void updateStoreOrdersList() {
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, orderTracker.getOrdersInTracker());
        storeOrdersListView.setAdapter(adapter);
    }
    private void storeOrdersListViewOnSelection() {
        storeOrdersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item position
                selectedPosition = position;
            }
        });
    }

    private void setCancelStoreOrdersButton(View view) {
        cancelStoreOrdersButton = view.findViewById(R.id.cancelStoreOrdersButton);
        cancelStoreOrdersButtonOnClick();
    }

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

                }
            }
        });
    }

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