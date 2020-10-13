package com.pb.app.caloriecalculator.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.pb.app.caloriecalculator.R;


public class FragmentStatisticCalendar {

    private static FragmentStatisticCalendar instance;
    private FragmentStatisticCalendar(){}

    private static AlertDialog alert;

    public static FragmentStatisticCalendar getInstance() {
        if (instance == null){
            instance = new FragmentStatisticCalendar();
        }
        return instance;
    }

    public AlertDialog createAlertDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.calendar_dialog, null)).setCancelable(true);
        alert = builder.create();
        alert.show();
//        Calendar calendarView = alert.findViewById(R.id.sly_calendar);
//        calendarView.setCallback(new SlyCalendarDialog.Callback() {
//            @Override
//            public void onCancelled() {
//                alert.dismiss();
//            }
//
//            @Override
//            public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
//                if (firstDate != null && secondDate != null){
//                    DateResponse.getInstance().updateDateStatistic(firstDate.getTime(), secondDate.getTime());
//                } else if (firstDate != null){
//                    DateResponse.getInstance().updateDateStatistic(firstDate.getTime(), firstDate.getTime());
//                }
//                alert.dismiss();
//            }
//        });
        return alert;
    }
}
