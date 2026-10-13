package com.example.topic;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.topic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        binding.navView.setOnItemSelectedListener(item -> showFragment(item.getItemId()));
        if (savedInstanceState == null) {
            showFragment(R.id.navigation_home);
        }
    }

    /**
     * Replaces the visible Fragment when another bottom-navigation item is chosen.
     * These are sibling screens, so tab changes are not added to the Back stack.
     *
     * @param itemId the selected menu item
     * @return true if this is one of the three screen items
     */
    private boolean showFragment(int itemId) {
        Fragment fragment;
        if (itemId == R.id.navigation_home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.navigation_dashboard) {
            fragment = new DashboardFragment();
        } else if (itemId == R.id.navigation_notifications) {
            fragment = new NotificationsFragment();
        } else {
            return false;
        }
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (current == null || current.getClass() != fragment.getClass()) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
        return true;
    }
}
