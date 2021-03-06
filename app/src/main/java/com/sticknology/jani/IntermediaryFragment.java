package com.sticknology.jani;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.sticknology.jani.ui.calendar.CalendarPager;
import com.sticknology.jani.ui.create.CreatePager;
import com.sticknology.jani.ui.placeHolders.AboutPHFragment;
import com.sticknology.jani.ui.placeHolders.DayPHFragment;
import com.sticknology.jani.ui.placeHolders.PHCreateFragment;
import com.sticknology.jani.ui.placeHolders.PlanPHFragment;
import com.sticknology.jani.ui.plan.PlanPager;
import com.sticknology.jani.ui.profile.ProfilePager;

public class IntermediaryFragment extends Fragment {

    private final String[] titlesCalendar = new String[]{"Day", "Week", "Month"};
    private final String[] titlesPlan = new String[]{"Past", "Overview", "Future"};
    private final String[] titlesCreate = new String[]{"Manage", "Export"};
    private final String[] titlesProfile = new String[]{"Stats", "Preferences", "About"};

    private CalendarPager calendarPager;
    private PlanPager planPager;
    private CreatePager createPager;
    private ProfilePager profilePager;

    private String test;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_intermediary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Bundle test3 = getArguments();
        test = test3.getString("Category");
        System.err.println(test);

        ViewPager2 viewPager2 = getView().findViewById(R.id.inter_pager2);
        TabLayout tabLayout = getView().findViewById(R.id.inter_tab);

        //When below is reimplemented, comment out or remove the fragment holder
        /*
        if(test.equals("CALENDAR")){
            calendarPager = new CalendarPager(this);
            viewPager2.setAdapter(calendarPager);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                    tab.setText(titlesCalendar[position])).attach();
            viewPager2.setUserInputEnabled(false);
        } else if(test.equals("PLAN")) {
            planPager = new PlanPager(this);
            viewPager2.setAdapter(planPager);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                    tab.setText(titlesPlan[position])).attach();
            viewPager2.setCurrentItem(1, false);
        } else if(test.equals("CREATE")) {
            createPager = new CreatePager(this);
            viewPager2.setAdapter(createPager);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                    tab.setText(titlesCreate[position])).attach();
        } else {
            profilePager = new ProfilePager(this);
            viewPager2.setAdapter(profilePager);
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) ->
                    tab.setText(titlesProfile[position])).attach();
        }*/

        //Code below here to end of method is place holder only
        //!----------------------------------------------------
        //!----------------------------------------------------
        viewPager2.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);

        if(test.equals("CALENDAR")){
            //Create Fragment
            DayPHFragment dayPHFragment = new DayPHFragment().newInstance(0);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.ph_fragment_holder, dayPHFragment, null)
                    .commit();
        } else if(test.equals("PLAN")){
            PlanPHFragment planPHFragment = new PlanPHFragment().newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.ph_fragment_holder, planPHFragment, null)
                    .commit();
        } else if(test.equals("CREATE")){
            PHCreateFragment phCreateFragment = new PHCreateFragment().newInstance("");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.ph_fragment_holder, phCreateFragment, null)
                    .commit();
        } else{
            AboutPHFragment aboutPHFragment = new AboutPHFragment().newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.ph_fragment_holder, aboutPHFragment, null)
                    .commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
