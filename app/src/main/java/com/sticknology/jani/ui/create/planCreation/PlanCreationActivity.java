package com.sticknology.jani.ui.create.planCreation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sticknology.jani.R;
import com.sticknology.jani.ui.calendar.CalendarPager;
import com.sticknology.jani.ui.create.CreatePager;
import com.sticknology.jani.ui.plan.PlanPager;
import com.sticknology.jani.ui.profile.ProfilePager;

public class PlanCreationActivity extends AppCompatActivity {

    protected enum TABSET {VIEW, TEMPLATES}
    protected static TABSET currentTabSet = TABSET.VIEW;

    protected static ViewPager2 viewPager2PCA;
    protected static TabLayout tabLayoutPCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plancreation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_create, PlanCreateInterFragment.class, null)
                    .commit();
        }

    }


}
