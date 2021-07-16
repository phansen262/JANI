package com.sticknology.jani.uiCommon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.sticknology.jani.R;

public class TwoNumberPicker {

    private String[] decimalValues = new String[]{".0", ".125", ".25", ".375", ".5", ".625", ".75", ".875"};

    public void distanceDialog(Button buttonObject, View view, Activity activity, Context context, String type){

        buttonObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //build alert dialog
                final AlertDialog.Builder d = new AlertDialog.Builder(context);
                LayoutInflater inflater = activity.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_two_number_picker, null);
                d.setTitle("Set " + type);
                d.setView(dialogView);

                //Type initialization
                int firstPosition = 0;
                int secondPosition = 0;
                //Picker1 max, Picker1 min, Picker2 max, Picker2 min
                int[] pickerBounds = new int[4];
                if(type.equals("Distance")){
                    int[] posArray = getDistancePositions(buttonObject.getText().toString());
                    firstPosition = posArray[0];
                    secondPosition = posArray[1];
                    pickerBounds[0] = 50;
                    pickerBounds[1] = 0;
                    pickerBounds[2] = decimalValues.length-1;
                    pickerBounds[3] = 0;
                }

                //Set behavior for whole number picker
                final NumberPicker picker1 = (NumberPicker) dialogView.findViewById(R.id.d2number_picker1);
                picker1.setMaxValue(pickerBounds[0]);
                picker1.setMinValue(pickerBounds[1]);
                picker1.setWrapSelectorWheel(true);
                //Get current set value
                if(firstPosition <= picker1.getMaxValue() && firstPosition >= picker1.getMinValue()){
                    picker1.setValue(firstPosition);
                }
                picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    }
                });

                //Set behavior for decimal number picker
                final NumberPicker picker2 = (NumberPicker) dialogView.findViewById(R.id.d2number_picker2);
                picker2.setMaxValue(pickerBounds[2]);
                picker2.setMinValue(pickerBounds[3]);
                picker2.setDisplayedValues(decimalValues);
                picker2.setWrapSelectorWheel(true);
                //Get current set value
                if(secondPosition <= picker2.getMaxValue() && secondPosition >= picker2.getMinValue()){
                    picker2.setValue(secondPosition);
                }
                picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                    }
                });

                //Set button behavior
                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(type.equals("Distance")) {
                            buttonObject.setText(String.valueOf(picker1.getValue()) +
                                    decimalValues[picker2.getValue()] + " mi");
                        }
                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                //Final show
                AlertDialog alertDialog = d.create();
                alertDialog.show();
            }
        });
    }

    private int[] getDistancePositions(String input){

        int[] posArray = new int[2];
        //input string of form "#.# mi"
        String prevDistance = input.split(" ")[0];
        if(prevDistance.contains(".")) {
            posArray[0] = Integer.parseInt(prevDistance.split("\\.")[0]);
            String decimalDistance = "." + prevDistance.split("\\.")[1];
            for(int i = 0; i < decimalValues.length; i++){
                if(decimalDistance.equals(decimalValues[i])){
                    posArray[1] = i;
                    break;
                }
            }
        }
        return posArray;
    }
}
