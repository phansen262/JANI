package com.sticknology.jani.ui.create.planCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sticknology.jani.R;
import com.sticknology.jani.data.ListCreation;
import com.sticknology.jani.data.TrainingPlan;
import com.sticknology.jani.data.TrainingWeek;
import com.sticknology.jani.dataProcessing.InterpretTrainingPlan;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WByWFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static int weekPosition;

    public static TrainingWeek mTrainingWeek;

    private Spinner weekSpinner;
    private ArrayAdapter<String> spinnerAdapter;
    private WeekByWeekRevAdapter mWByWRevAdapter;
    private List<String> weeks;
    private int newWeekIndex;

    public static WByWFragment newInstance() {

        WByWFragment f = new WByWFragment();
        Bundle b = new Bundle();

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("GOT IN ONCREATEVIEW WBYWFRAG");

        return inflater.inflate(R.layout.fragment_wbyw, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("THIS IS WEEKPOSITION: " + weekPosition);

        mTrainingWeek = PlanCreationActivity.mTrainingPlan.getTrainingPlanWeeks().get(weekPosition);

        //Create RecyclerView for Displaying Days in Week
        RecyclerView revDay = (RecyclerView) getView().findViewById(R.id.pc_rev_dayholder);
        mWByWRevAdapter = new WeekByWeekRevAdapter();
        revDay.setAdapter(mWByWRevAdapter);
        revDay.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Add Drop Down Menu For Weeks
        weekSpinner = getView().findViewById(R.id.wbyw_spinner_week);
        int weekSize = PlanCreationActivity.mTrainingPlan.getTrainingPlanWeeks().size();
        weeks = new ArrayList<String>();
        for(int i = 0; i < weekSize; i++){
            weeks.add("Week " + (i+1));
        }
        weeks.add("Add Week");
        newWeekIndex = weekSize;
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, weeks);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setOnItemSelectedListener(this);
        weekSpinner.setAdapter(spinnerAdapter);
        weekSpinner.setSelection(weekPosition);

        //Add Listener To Delete Week
        Button deleteWeekButton = getView().findViewById(R.id.wbyw_button_delete);
        deleteWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currSelected = weekSpinner.getSelectedItemPosition();
                weeks.remove(currSelected);
                newWeekIndex--;

                PlanCreationActivity.mTrainingPlan.getTrainingPlanWeeks().remove(currSelected);

                for(int i = currSelected; i < newWeekIndex; i++){
                    weeks.set(i, "Week " + (i+1));
                }

                spinnerAdapter.notifyDataSetChanged();
                if(currSelected == newWeekIndex){
                    weekSpinner.setSelection(newWeekIndex - 1);
                }
            }
        });
    }

    //Listener for selection of week spinner
    //Currently only spinner addition when add week selected implemented
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //i is the position of the spinner, not sure what l is
        if(i == newWeekIndex){
            weeks.add(newWeekIndex, "Week " + (newWeekIndex+1));
            spinnerAdapter.notifyDataSetChanged();
            newWeekIndex++;

            PlanCreationActivity.mTrainingPlan.getTrainingPlanWeeks().add(new ListCreation().createEmptyTrainingWeek());
        }
        mTrainingWeek = PlanCreationActivity.mTrainingPlan.getTrainingPlanWeeks().get(i);
        weekPosition = i;

        System.out.println("THIS IS WEEK POSITION: " + weekPosition);

        try {
            mWByWRevAdapter.notifyDataSetChanged();
            WeekByWeekRevAdapter.mRunAdapter.notifyDataSetChanged();
        } catch (NullPointerException nullPointerException){
            System.out.println("DID NOT UPDATE ADAPTERS");
        }

        new WByWDataHandler().printFilledPlan(PlanCreationActivity.mTrainingPlan);
        new WByWDataHandler().printFilledWeek(WByWFragment.mTrainingWeek);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
