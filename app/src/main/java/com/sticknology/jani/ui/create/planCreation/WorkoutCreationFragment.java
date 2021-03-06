package com.sticknology.jani.ui.create.planCreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sticknology.jani.R;
import com.sticknology.jani.data.Workout;
import com.sticknology.jani.dataProcessing.InterpretWorkout;
import com.sticknology.jani.dataProcessing.StandardReadWrite;

public class WorkoutCreationFragment extends Fragment {

    public Spinner workoutTypeSpinner;

    private ArrayAdapter<CharSequence> dataAdapter;
    private int mWeekIndex;
    private int mDayIndex;

    public static WorkoutCreationFragment newInstance(int week, int day) {

        WorkoutCreationFragment f = new WorkoutCreationFragment();
        Bundle b = new Bundle();
        b.putInt("week", week);
        b.putInt("day", day);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        mWeekIndex = bundle.getInt("week");
        mDayIndex = bundle.getInt("day");

        return inflater.inflate(R.layout.fragment_workoutcreation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //Set up spinner for workout type
        workoutTypeSpinner = getView().findViewById(R.id.wc_wc_workouttype);
        dataAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.workouttype_array, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workoutTypeSpinner.setAdapter(dataAdapter);

        Button cancel = getView().findViewById(R.id.wc_wc_buttoncancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlanCreationActivity.currentTabSet = PlanCreationActivity.TABSET.VIEW;
                PlanCreateInterFragment planCreateInterFragment = PlanCreateInterFragment.newInstance(0, 1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_create, planCreateInterFragment, null)
                        .commit();
            }
        });

        Button saveWorkout = getView().findViewById(R.id.wc_wc_buttonsave);
        saveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("SAVING NEW WORKOUT");

                Switch templateSwitch = getView().findViewById(R.id.wc_wc_switchtemplate);
                TextView workoutName = getView().findViewById(R.id.wc_wc_workoutname);
                TextView workoutDescript = getView().findViewById(R.id.wc_wc_workoutdescript);

                String name = workoutName.getText().toString();
                String type = workoutTypeSpinner.getSelectedItem().toString();
                String description = workoutDescript.getText().toString();
                if(name.equals("")){
                    name = " ";
                }
                if(description.equals("")){
                    description = " ";
                }
                Workout workout = new Workout(name, type, description);

                if(templateSwitch.isChecked()) {
                    StandardReadWrite standardReadWrite = new StandardReadWrite();
                    InterpretWorkout interpretWorkout = new InterpretWorkout();
                    String workoutString = interpretWorkout.getStringWorkout(workout);
                    standardReadWrite.appendText(workoutString,"workout_templates.txt", getContext(), true);
                }

                //Add Workout to Training Plan
                PlanCreationActivity.mTrainingPlan.getTrainingDay(mWeekIndex,mDayIndex).addWorkout(workout);
                if(PlanCreationActivity.mTrainingPlan.getTrainingDay(mWeekIndex, mDayIndex).getTrainingDayWorkouts()
                        .get(0).getWorkoutName().equals(":;:")){

                    PlanCreationActivity.mTrainingPlan.getTrainingDay(mWeekIndex,mDayIndex).removeWorkout(0);
                }

                //Navigation back to overview
                PlanCreationActivity.currentTabSet = PlanCreationActivity.TABSET.VIEW;
                PlanCreateInterFragment planCreateInterFragment = PlanCreateInterFragment.newInstance(0, 1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_create, planCreateInterFragment, null)
                        .commit();
            }
        });
    }


}
