package com.sticknology.jani.ui.create.planCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sticknology.jani.R;

import static com.sticknology.jani.ui.create.planCreation.PlanCreationActivity.currentTabSet;

public class PlanCreateInterFragment extends Fragment {

    public static int mPagerPosition;
    public static ViewPager2 viewPager2;

    private final String[] titlesV = {"Overview", "Week by Week"};
    private final String[] titlesT = {"Run", "Workout"};
    private static TabLayout tabLayout;
    private int mDayIndex;


    public static PlanCreateInterFragment newInstance(int day, int pagerPosition) {

        PlanCreateInterFragment f = new PlanCreateInterFragment();
        Bundle b = new Bundle();
        b.putInt("day", day);
        b.putInt("pagerPosition", pagerPosition);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        mDayIndex = bundle.getInt("day");
        mPagerPosition = bundle.getInt("pagerPosition");

        return inflater.inflate(R.layout.fragment_plancreation_inter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = getView().findViewById(R.id.pager_plancreation);
        tabLayout = getView().findViewById(R.id.tab_plancreation);
        setTabs(viewPager2, tabLayout);
        if(mPagerPosition == 1){
            viewPager2.setCurrentItem(1, false);
            mPagerPosition = 0;
        }
    }

    protected void setTabs(ViewPager2 viewPager2, TabLayout tabLayout){

        PlanCreationPager planCreationPager = new PlanCreationPager(this, mDayIndex);
        viewPager2.setAdapter(planCreationPager);
        if (currentTabSet == PlanCreationActivity.TABSET.VIEW){
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titlesV[position])).attach();
        } else if (currentTabSet == PlanCreationActivity.TABSET.TEMPLATES){
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titlesT[position])).attach();
        }
        viewPager2.setUserInputEnabled(false);
    }
}
