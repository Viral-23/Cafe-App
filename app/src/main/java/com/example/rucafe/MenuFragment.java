package com.example.rucafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.rucafe.databinding.FragmentMenuBinding;

/**
 * This is the fragment that handles the navigation to the coffee and donut fragment.
 * @author Viral Patel
 */
public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    /**
     * Default constructor
     */
    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the menu fragment.
     * @return Fragment: returns the new menu fragment.
     */
    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    /**
     * Initializes the state of the menu fragment.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Sets up the on click handlers for the two buttons that will navigate to their respective
     * fragment (coffee or donut).
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View: returns the view, which contains all the UI elements for the menu fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater, container, false);
        binding.coffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CoffeeFragment());
            }
        });

        binding.donutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new DonutFragment());
            }
        });
        return binding.getRoot();
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
