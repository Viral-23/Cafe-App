package com.example.rucafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoffeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoffeeFragment extends Fragment {

    Coffee coffee = new Coffee();
    private Spinner cupSizes;

    private CheckBox sweetCreamCheckBox;
    private CheckBox frenchVanillaCheckBox;
    private CheckBox irishCreamCheckBox;
    private CheckBox caramelCheckBox;
    private CheckBox mochaCheckBox;

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

        // Inflate the layout for this fragment
        return view;
    }

    private void setCupSizeSpinner(View view) {
        cupSizes = view.findViewById(R.id.cupSizes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cupSizeOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cupSizes.setAdapter(adapter);
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
    }

    private void addInsOnClick(View view) {

    }
}