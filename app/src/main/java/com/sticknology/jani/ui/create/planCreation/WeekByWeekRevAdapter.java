package com.sticknology.jani.ui.create.planCreation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sticknology.jani.MainActivity;
import com.sticknology.jani.R;
import com.sticknology.jani.data.ListCreation;
import com.sticknology.jani.data.Run;
import com.sticknology.jani.data.TrainingDay;
import com.sticknology.jani.data.TrainingWeek;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeekByWeekRevAdapter extends RecyclerView.Adapter<WeekByWeekRevAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Spinner mDayTypeSpinner;
        public Context mContext;
        public RecyclerView mInternalRecyclerView;
        public TextView mDayName;
        public Button mNewItemButton;

        public ViewHolder(View itemView){

            super(itemView);
            mDayTypeSpinner = itemView.findViewById(R.id.wbyw_rev_spinner_daytype);
            mContext = itemView.getContext();
            mInternalRecyclerView = itemView.findViewById(R.id.wbyw_rev_rev);
            mDayName = itemView.findViewById(R.id.wbyw_rev_text_dayname);
            mNewItemButton = itemView.findViewById(R.id.wbyw_rev_newitem);
        }
    }

    private List<TrainingDay> mTrainingDayList;
    public WeekByWeekRevAdapter(List<TrainingDay> dayList){
        mTrainingDayList = dayList;
    }

    @Override
    public WeekByWeekRevAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View intervalView = inflater.inflate(R.layout.item_day_wbw, parent, false);

        // Return a new holder instance
        WeekByWeekRevAdapter.ViewHolder viewHolder = new WeekByWeekRevAdapter.ViewHolder(intervalView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Spinner dayType = holder.mDayTypeSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.mContext,
                R.array.daytype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayType.setAdapter(adapter);

        //Create RecyclerView for displaying currently added runs/workouts
        RecyclerView revDay = (RecyclerView) holder.mInternalRecyclerView;
        ArrayList<Run> runs = mTrainingDayList.get(position).getTrainingDayRuns();
        WByWRunRevAdapter wByWRunRevAdapter = new WByWRunRevAdapter(runs);
        revDay.setAdapter(wByWRunRevAdapter);
        revDay.setLayoutManager(new LinearLayoutManager(holder.mContext));

        System.out.println("THIS IS POSITION:  " + position);
        TextView dayName = holder.mDayName;

        //Sets the correct day for each rev item
        switch (position){
            case 0:{
                dayName.setText("Monday");
                break;
            } case 1:{
                dayName.setText("Tuesday");
                break;
            } case 2:{
                dayName.setText("Wednesday");
                break;
            } case 3:{
                dayName.setText("Thursday");
                break;
            } case 4:{
                dayName.setText("Friday");
                break;
            } case 5:{
                dayName.setText("Saturday");
                break;
            } case 6:{
                dayName.setText("Sunday");
                break;
            } default:{
                dayName.setText("Something Broke");
            }
        }

        //Sets listener for the button to add a run
        Button newItemButton = holder.mNewItemButton;
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanCreationActivity planCreationActivity = (PlanCreationActivity) holder.mContext;
                PlanCreationActivity.currentTabSet = PlanCreationActivity.TABSET.TEMPLATES;

                PlanCreateInterFragment planCreateInterFragment = new PlanCreateInterFragment();
                planCreationActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_create, planCreateInterFragment, null)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrainingDayList.size();
    }
}