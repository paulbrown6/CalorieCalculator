package com.pb.app.caloriecalculator.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.pb.app.caloriecalculator.api.DateResponse;
import java.util.List;

public class FragmentStatisticCalendar {

    private static FragmentStatisticCalendar instance;
    private FragmentStatisticCalendar(){}

    private static CalendarDialog dialog;

    public static FragmentStatisticCalendar getInstance() {
        if (instance == null){
            instance = new FragmentStatisticCalendar();
        }
        return instance;
    }

    public CalendarDialog createCalendarDialog(Context context) {
        dialog = new CalendarDialog(context, new OnDaysSelectionListener() {
            @Override
            public void onDaysSelected(List<Day> selectedDays) {
                if (selectedDays != null && selectedDays.size() > 0) {
                    DateResponse.getInstance().updateDateStatistic(selectedDays.get(0).getCalendar().getTime(),
                            selectedDays.get(selectedDays.size()-1).getCalendar().getTime());
                }
            }
        });
        dialog.create();
        dialog.setSelectionType(SelectionType.RANGE);
        dialog.setCalendarBackgroundColor(Color.parseColor("#FFE5E5"));
        dialog.setSelectedDayBackgroundColor(Color.parseColor("#C4E087"));
        dialog.setShowDaysOfWeek(false);
        dialog.show();
        return dialog;
    }
}