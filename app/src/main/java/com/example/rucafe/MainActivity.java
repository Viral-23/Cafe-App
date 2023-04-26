package com.example.rucafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.rucafe.databinding.ActivityMainBinding;

/**
 * This is the main activity which hosts all the fragments, including the coffee/donut and orders
 * fragments.
 * @author Viral Patel
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    /**
     * This method is called when the activity is created. It sets up the layout and
     * initializes the bottom navigation view to handle fragment transitions.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        OrdersFragment ordersFragment = new OrdersFragment();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home)
                replaceFragment(new HomeFragment());
            else if (item.getItemId() == R.id.menu)
                replaceFragment(new MenuFragment());
            else if (item.getItemId() == R.id.orders)
                replaceFragment(ordersFragment);

            return true;
        });
    }

    /**
     * Helper method which replaces the fragment being displayed in the activity.
     * @param fragment: the fragment to be displayed.
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}