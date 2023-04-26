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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project4.*;
import java.util.ArrayList;

/**
 * This is the fragment that handles the customization of a coffee, and allows the user to
 * add it to their basket.
 * @author Viral Patel
 */

public class CoffeeFragment extends Fragment {

    private boolean cupSelected = false;
    private boolean quantitySelected = false;

    Coffee coffee = new Coffee();
    private Spinner cupSizes;

    private Toast toast;
    private CheckBox sweetCreamCheckBox;
    private CheckBox frenchVanillaCheckBox;
    private CheckBox irishCreamCheckBox;
    private CheckBox caramelCheckBox;
    private CheckBox mochaCheckBox;

    private Spinner coffeeQuantity;

    private TextView totalTextView;
    private Button addToOrderButton;


    /**
     * Default constructor.
     */
    public CoffeeFragment() {
        // Required empty public constructor
    }

    /**
     * Default method that creates a new instance of the fragment.
     * @return Fragment: returns the new instance of the fragment.
     */
    public static CoffeeFragment newInstance() {
        CoffeeFragment fragment = new CoffeeFragment();
        return fragment;
    }

    /**
     * Initializes the state of the coffee fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Handles the setup for all elements in the Coffee UI.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View: returns the view, which contains all the UI elements for the coffee fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);

        setCupSizeSpinner(view);
        setAddInCheckBoxes(view);
        setAddToOrderButton(view);
        setCoffeeQuantitySpinner(view);
        setTotalTextView(view);

        return view;
    }

    /**
     * Sets up the spinner for the cup size selection of the coffee.
     * @param view: the view which contains all UI elements for the coffee fragment.
     */
    private void setCupSizeSpinner(View view) {
        cupSizes = view.findViewById(R.id.cupSizes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cupSizeOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cupSizes.setAdapter(adapter);

        cupSizeOnSelect(cupSizes);
    }

    /**
     * Handles the selection for the spinner. Enables/Disables other functionalities, and updates
     * information as necessary.
     * @param spinner: the cup size spinner.
     */
    private void cupSizeOnSelect(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    clearAndDisableAddIns();
                    clearAndDisableQuantity();
                    cupSelected = false;
                    quantitySelected = false;
                }
                else {
                    if (toast != null)
                        toast.cancel();
                    String msg = getResources().getString(R.string.cupSizeSelected);
                    toast = Toast.makeText(getActivity(),
                            adapterView.getItemAtPosition(i).toString() + msg,
                            Toast.LENGTH_SHORT);

                    toast.show();

                    cupSelected = true;
                    enableAddIns();
                    enableQuantity();
                }

                displaySubtotal();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    /**
     * Sets up the checkboxes for the coffee add-ins.
     * @param view: the view which contains all UI elements for the coffee fragment.
     */
    private void setAddInCheckBoxes(View view) {
        String[] addInOptions = getResources().getStringArray(R.array.addInOptions);

        sweetCreamCheckBox = view.findViewById(R.id.sweetCreamCheckBox);
        frenchVanillaCheckBox = view.findViewById(R.id.frenchVanillaCheckBox);
        irishCreamCheckBox = view.findViewById(R.id.irishCreamCheckBox);
        caramelCheckBox = view.findViewById(R.id.caramelCheckBox);
        mochaCheckBox = view.findViewById(R.id.mochaCheckBox);

        sweetCreamCheckBox.setText(addInOptions[0]);
        frenchVanillaCheckBox.setText(addInOptions[1]);
        irishCreamCheckBox.setText(addInOptions[2]);
        caramelCheckBox.setText(addInOptions[3]);
        mochaCheckBox.setText(addInOptions[4]);

        addInsOnClick(sweetCreamCheckBox);
        addInsOnClick(frenchVanillaCheckBox);
        addInsOnClick(irishCreamCheckBox);
        addInsOnClick(caramelCheckBox);
        addInsOnClick(mochaCheckBox);

    }

    /**
     * Handler for checkbox clicks. Updates subtotal information and displays a toast message
     * based on the add-in that has been added/removed.
     * @param checkBox: the coffee add-in checkboxes.
     */
    private void addInsOnClick(CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null)
                    toast.cancel();
                if(((CompoundButton) view).isChecked()){
                    String msg = getResources().getString(R.string.added);
                    toast = Toast.makeText(getActivity(), checkBox.getText() + msg,
                            Toast.LENGTH_SHORT);
                } else {
                    String msg = getResources().getString(R.string.removed);
                    toast = Toast.makeText(getActivity(), checkBox.getText() + msg,
                            Toast.LENGTH_SHORT);
                }

                toast.show();
                displaySubtotal();
            }
        });
    }

    /**
     * Sets up the quantity spinner, which allows the user to select a quantity for their
     * created coffee.
     * @param view: the view which contains all UI elements for the coffee fragment.
     */
    private void setCoffeeQuantitySpinner(View view) {
        coffeeQuantity = view.findViewById(R.id.coffeeQuantity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.coffeeQuantityOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQuantity.setAdapter(adapter);

        coffeeQuantityOnSelect(coffeeQuantity);
    }

    /**
     * Handler for the quantity selection spinner. A toast message is displayed when the user
     * chooses a different quantity. The subtotal is updated accordingly.
     * @param spinner
     */
    private void coffeeQuantityOnSelect(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    quantitySelected = false;
                }
                else {
                    if (toast != null)
                        toast.cancel();
                    String msg = getResources().getString(R.string.quantityColon);
                    toast = Toast.makeText(getActivity(),
                            msg + adapterView.getItemAtPosition(i).toString(),
                            Toast.LENGTH_SHORT);

                    toast.show();
                    quantitySelected = true;
                }

                displaySubtotal();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    /**
     * Sets up the add to order button, which allows users to add a coffee to their basket.
     * @param view: the view which contains all UI elements for the coffee fragment.
     */
    private void setAddToOrderButton(View view) {
        addToOrderButton = view.findViewById(R.id.addToOrderButton);
        addToOrderButton.setText(R.string.addToOrder);

        addToOrderButtonOnClick(addToOrderButton);
    }

    /**
     * Handler for the add to order button click. When the button is clicked, a toast message is
     * displayed based on if the action went through or if there is an error. The coffee is
     * added to the order if all conditions are met.
     * @param addToOrderButton: the add to order button.
     */
    private void addToOrderButtonOnClick(Button addToOrderButton) {
        addToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cupSelected && quantitySelected) {
                    if (toast != null)
                        toast.cancel();

                    toast = Toast.makeText(getActivity(), R.string.coffeeAddedToOrderToastMessage,
                            Toast.LENGTH_SHORT);
                    toast.show();
                    OrdersFragment.order.addItemToOrder(coffee);
                }
                else if (cupSelected) {
                    noQuantitySelected();
                }
                else {
                    noCupSelected();
                }
            }
        });
    }

    /**
     * Helper method to enable all add-in checkboxes .
     */
    private void enableAddIns() {
        sweetCreamCheckBox.setEnabled(true);
        frenchVanillaCheckBox.setEnabled(true);
        irishCreamCheckBox.setEnabled(true);
        caramelCheckBox.setEnabled(true);
        mochaCheckBox.setEnabled(true);
    }

    /**
     * Helper method to clear previous checks and disable all add-in checkboxes.
     */
    private void clearAndDisableAddIns() {
        sweetCreamCheckBox.setChecked(false);
        frenchVanillaCheckBox.setChecked(false);
        irishCreamCheckBox.setChecked(false);
        caramelCheckBox.setChecked(false);
        mochaCheckBox.setChecked(false);

        sweetCreamCheckBox.setEnabled(false);
        frenchVanillaCheckBox.setEnabled(false);
        irishCreamCheckBox.setEnabled(false);
        caramelCheckBox.setEnabled(false);
        mochaCheckBox.setEnabled(false);
    }

    /**
     * Helper method to clear previous quantity selection and disable quantity spinner.
     */
    private void clearAndDisableQuantity() {
        coffeeQuantity.setSelection(Constants.FIRST_OPTION);
        coffeeQuantity.setEnabled(false);
    }

    /**
     * Helper method to enable the quantity selection spinner.
     */
    private void enableQuantity() {
        coffeeQuantity.setEnabled(true);
    }

    /**
     * Helper method to determine the add-ins the user has selected based on the checkboxes that
     * are checked.
     * @return ArrayList: returns the ArrayList of string add-ins.
     */
    private ArrayList<String> getAddInsSelected() {
        ArrayList<String> addIns = new ArrayList<>();
        if (sweetCreamCheckBox.isChecked())
            addIns.add((String) sweetCreamCheckBox.getText());
        if (frenchVanillaCheckBox.isChecked())
            addIns.add((String) frenchVanillaCheckBox.getText());
        if (irishCreamCheckBox.isChecked())
            addIns.add((String) irishCreamCheckBox.getText());
        if (caramelCheckBox.isChecked())
            addIns.add((String) caramelCheckBox.getText());
        if (mochaCheckBox.isChecked())
            addIns.add((String) mochaCheckBox.getText());

        return addIns;
    }

    /**
     * Helper method to get the cup size selected in the spinner.
     * @return String: returns the cup size selected.
     */
    private String getCupSizeSelected() {
        return (String) cupSizes.getSelectedItem();
    }

    /**
     * Helper method to get the quantity selected in the spinner.
     * @return int: returns the quantity selected.
     */
    private int getQuantitySelected() {
        return Integer.parseInt((String) coffeeQuantity.getSelectedItem());
    }

    /**
     * Sets up the text view where the total is displayed for the coffee.
     * @param view: the view which contains all UI elements for the coffee fragment.
     */
    private void setTotalTextView(View view) {
        totalTextView = view.findViewById(R.id.totalTextView);
    }

    /**
     * Displays the subtotal of the coffee order the user has created.
     */
    private void displaySubtotal() {
        if (calculateSubTotal()) {
            String coffeeOrderString = coffee.toString();
            String[] coffeeOrderParts = coffeeOrderString.split("\\$");
            double itemPrice = Double.parseDouble(coffeeOrderParts[1]) / getQuantitySelected();
            String newCoffeeOrderString = coffeeOrderParts[0] + "($" + String.format("%.2f", itemPrice) + ")";
            totalTextView.setText(newCoffeeOrderString);
            totalTextView.append("\n\nSubtotal:\t$" + String.format("%.2f", coffee.itemPrice()));
        }
        else
            totalTextView.setText(null);

    }

    /**
     * Calculates the subtotal of the coffee order if a cup and quantity are selected.
     * @return boolean: returns true if the subtotal was able to be calculated, false otherwise.
     */
    private boolean calculateSubTotal() {
        if (cupSelected && quantitySelected) {
            coffee = new Coffee(getCupSizeSelected(), getAddInsSelected());
            coffee.setQuantity(getQuantitySelected());
            return true;
        }

        return false;
    }

    /**
     * Creates an alert dialog to notify the user that they have not selected a cup.
     */
    private void noCupSelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.noCupSelectedTitle);
        builder.setMessage(R.string.noCupSelectedMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Creates an alert dialog to notify the user that they have not selected a quantity.
     */
    private void noQuantitySelected() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.noQuantitySelectedTitle);
        builder.setMessage(R.string.noQuantitySelectedMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}