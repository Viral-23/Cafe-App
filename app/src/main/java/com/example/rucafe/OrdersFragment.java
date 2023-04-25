package com.example.rucafe;

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
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
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


    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment OrdersFragment.
     */

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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

    private void setCurrentOrderListView(View view) {
        currentOrderListView = view.findViewById(R.id.currentOrderListView);
        updateOrderList();
        currentOrderListViewSelection();
    }
    private void setTextViews(View view) {
        orderSubtotalText = view.findViewById(R.id.orderSubtotalText);
        orderSalesTaxText = view.findViewById(R.id.orderSalesTaxText);
        orderTotalText = view.findViewById(R.id.orderTotalText);

        subtotalText = view.findViewById(R.id.subtotalText);
        salesTaxText = view.findViewById(R.id.salesTaxText);
        totalForOrderText = view.findViewById(R.id.totalForOrderText);
    }
    private void currentOrderListViewSelection() {
        currentOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the selected item position
                selectedPosition = position;
            }
        });
    }
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
                    toast = Toast.makeText(getActivity(), R.string.orderPlaced,
                            Toast.LENGTH_SHORT);
                    toast.show();

                    // TODO: Add order to orderTracker in store orders view
                    order = new Order();
                    updateOrderList();
                }
            }
        });
    }

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

    private void clearReceipt() {
        orderSubtotalText.setText("");
        orderSalesTaxText.setText("");
        orderTotalText.setText("");

        subtotalText.setText("");
        salesTaxText.setText("");
        totalForOrderText.setText("");
    }

    private void setReceipt() {
        orderSubtotalText.setText("$" + String.format("%.2f", order.calculateSubtotal()));
        orderSalesTaxText.setText("$" + String.format("%.2f", order.calculateSalesTax()));
        orderTotalText.setText("$" + String.format("%.2f", order.calculateTotal()));

        subtotalText.setText(R.string.subtotalMsg);
        salesTaxText.setText(R.string.salesTaxMsg);
        totalForOrderText.setText(R.string.totalMsg);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}