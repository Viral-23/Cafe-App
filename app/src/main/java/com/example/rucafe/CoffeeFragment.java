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
 * A simple {@link Fragment} subclass.
 * Use the {@link CoffeeFragment#newInstance} factory method to
 * create an instance of this fragment.
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


    public CoffeeFragment() {
        // Required empty public constructor
    }
    public static CoffeeFragment newInstance(String param1, String param2) {
        CoffeeFragment fragment = new CoffeeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);

        setCupSizeSpinner(view);
        setAddInCheckBoxes(view);
        setAddToOrderButton(view);
        setCoffeeQuantityButton(view);
        setTotalTextView(view);

        return view;
    }

    private void setCupSizeSpinner(View view) {
        cupSizes = view.findViewById(R.id.cupSizes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cupSizeOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cupSizes.setAdapter(adapter);

        cupSizeOnSelect(cupSizes);
    }

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

                    toast = Toast.makeText(getActivity(),
                            adapterView.getItemAtPosition(i).toString() + " cup selected",
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

    private void addInsOnClick(CheckBox checkBox) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null)
                    toast.cancel();

                if(((CompoundButton) view).isChecked()){
                    toast = Toast.makeText(getActivity(), checkBox.getText() + " added", Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(getActivity(), checkBox.getText() + " removed", Toast.LENGTH_SHORT);
                }

                toast.show();
                displaySubtotal();
            }
        });
    }

    private void setCoffeeQuantityButton(View view) {
        coffeeQuantity = view.findViewById(R.id.coffeeQuantity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.coffeeQuantityOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coffeeQuantity.setAdapter(adapter);

        coffeeQuantityOnSelect(coffeeQuantity);
    }

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
                    toast = Toast.makeText(getActivity(),
                            "quantity: " + adapterView.getItemAtPosition(i).toString(),
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

    private void setAddToOrderButton(View view) {
        addToOrderButton = view.findViewById(R.id.addToOrderButton);
        addToOrderButton.setText(R.string.addToOrder);

        addToOrderButtonOnClick(addToOrderButton);
    }

    private void addToOrderButtonOnClick(Button addToOrderButton) {
        addToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cupSelected && quantitySelected) {
                    // TODO: Add to orders list
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

    private void enableAddIns() {
        sweetCreamCheckBox.setEnabled(true);
        frenchVanillaCheckBox.setEnabled(true);
        irishCreamCheckBox.setEnabled(true);
        caramelCheckBox.setEnabled(true);
        mochaCheckBox.setEnabled(true);
    }
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

    private void clearAndDisableQuantity() {
        coffeeQuantity.setSelection(Constants.FIRST_OPTION);
        coffeeQuantity.setEnabled(false);
    }

    private void enableQuantity() {
        coffeeQuantity.setEnabled(true);
    }

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

    private String getCupSizeSelected() {
        return (String) cupSizes.getSelectedItem();
    }

    private int getQuantitySelected() {
        return Integer.parseInt((String) coffeeQuantity.getSelectedItem());
    }

    private void setTotalTextView(View view) {
        totalTextView = view.findViewById(R.id.totalTextView);
    }
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
    private boolean calculateSubTotal() {
        if (cupSelected && quantitySelected) {
            coffee = new Coffee(getCupSizeSelected(), getAddInsSelected());
            coffee.setQuantity(getQuantitySelected());
            return true;
        }

        return false;
    }

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