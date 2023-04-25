package com.example.rucafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import java.util.ArrayList;

import com.example.project4.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonutFragment extends Fragment {

    Donut donut;
    private ArrayList<Item> items = new ArrayList<>();
    private int [] itemImages = {R.drawable.glazedyeastdonutimg, R.drawable.chocolateyeastdonutimg,
            R.drawable.strawberryyeastdonutimg, R.drawable.cinnamonyeastdonutimg,
            R.drawable.matchacakedonutimg, R.drawable.blackberrycakedonutimg,
            R.drawable.saltedtehinacakedonutimg, R.drawable.lemoncakedonutimg,
            R.drawable.glazeddonutholeimg, R.drawable.pumpkinspicedonutholeimg,
            R.drawable.blueberrydonutholeimg, R.drawable.rainbowdonutholeimg
    };

    private RecyclerView donutOptionsRecyclerView;

    private NumberPicker donutQuantitySelector;

    public DonutFragment() {
        // Required empty public constructor
    }

    public static DonutFragment newInstance() {
        DonutFragment fragment = new DonutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donut, container, false);
        setDonutOptionsRecyclerView(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void setDonutOptionsRecyclerView(View view) {
        donutOptionsRecyclerView = view.findViewById(R.id.donutOptionsRecyclerView);
        setupDonutItems();
        ItemsAdapter adapter = new ItemsAdapter(getActivity(), items); //create the adapter
        donutOptionsRecyclerView.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        donutOptionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setupDonutItems() {
        /*
         * Item names are defined in a String array under res/string.xml.
         * Your item names might come from other places, such as an external file, or the database
         * from the backend.
         */
        String [] itemNames = getResources().getStringArray(R.array.donutNamesOptions);
        /* Add the items to the ArrayList.
         */
        for (int i = 0; i < itemNames.length; i++) {
            if (itemNames[i].contains("Yeast"))
                donut = new YeastDonut();
            else if (itemNames[i].contains("Cake"))
                donut = new CakeDonut();
            else
                donut = new DonutHole();
            items.add(new Item(itemNames[i], itemImages[i],
                    "$" + String.format("%.2f", donut.itemPrice())));
        }
    }


}