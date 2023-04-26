package com.example.rucafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.project4.*;

/**
 * This is the fragment that handles the selection of a donut, and allows the user to
 * add it to their basket.
 * @author Viral Patel
 */
public class DonutFragment extends Fragment {

    public static Toast donutToast;
    private Donut donut;
    private ArrayList<Item> items = new ArrayList<>();
    private int [] itemImages = {R.drawable.glazedyeastdonutimg, R.drawable.chocolateyeastdonutimg,
            R.drawable.strawberryyeastdonutimg, R.drawable.cinnamonyeastdonutimg,
            R.drawable.matchacakedonutimg, R.drawable.blackberrycakedonutimg,
            R.drawable.saltedtehinacakedonutimg, R.drawable.lemoncakedonutimg,
            R.drawable.glazeddonutholeimg, R.drawable.pumpkinspicedonutholeimg,
            R.drawable.blueberrydonutholeimg, R.drawable.rainbowdonutholeimg
    };

    private RecyclerView donutOptionsRecyclerView;

    /**
     * Default constructor for the donut fragment.
     */
    public DonutFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the donut fragment.
     * @return Fragment: returns the new donut fragment.
     */
    public static DonutFragment newInstance() {
        DonutFragment fragment = new DonutFragment();
        return fragment;
    }

    /**
     * Initializes the state of the donut fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Handles the setup for all elements in the Donut UI.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View: returns the view, which contains all the UI elements for the donut fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donut, container, false);
        setDonutOptionsRecyclerView(view);
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Sets up the recycler view for the donut selection.
     * @param view: the view which contains all UI elements for the donut fragment.
     */
    private void setDonutOptionsRecyclerView(View view) {
        donutOptionsRecyclerView = view.findViewById(R.id.donutOptionsRecyclerView);
        setupDonutItems();
        ItemsAdapter adapter = new ItemsAdapter(getActivity(), items); //create the adapter
        donutOptionsRecyclerView.setAdapter(adapter); //bind the list of items to the RecyclerView
        //use the LinearLayout for the RecyclerView
        donutOptionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Adds all donut information (name, image, and price) to a new item in the recycler view.
     * The information is stored in the strings.xml.
     */
    private void setupDonutItems() {

        String [] itemNames = getResources().getStringArray(R.array.donutNamesOptions);

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