package com.example.rucafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This is the fragment that displays a welcome message and image for the cafe (home page).
 * @author Viral Patel
 */

public class HomeFragment extends Fragment {

    private TextView homeWelcomeText;

    /**
     * Default constructor.
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the home fragment.
     * @return Fragment: returns the new home fragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    /**
     * Initializes the state of the home fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the welcome message text and the image displayed.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View: returns the view, which contains all the UI elements for the home fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHomeWelcomeText(view);
        return view;
    }

    /**
     * Sets up the welcome text displayed in the home fragment.
     * @param view: the view which contains all UI elements for the home fragment.
     */
    private void setHomeWelcomeText(View view) {
        homeWelcomeText = view.findViewById(R.id.homeWelcomeText);
        homeWelcomeText.setText(R.string.homeWelcomeMsg);
    }
}