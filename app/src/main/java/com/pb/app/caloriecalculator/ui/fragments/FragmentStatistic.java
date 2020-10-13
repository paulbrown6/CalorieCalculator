package com.pb.app.caloriecalculator.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.api.DateResponse;
import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.entity.StatisticEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentStatistic extends Fragment {

    private FragmentTransaction ftransaction;
    private FragmentStatisticFull fstatFull;
    private FragmentStatisticNull fstatNull;
    private static LifecycleOwner lifecycleOwner;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    public FragmentStatistic(LifecycleOwner owner) {
        lifecycleOwner = owner;
    }

    public static FragmentStatistic newInstance(String param1, String param2) {
        FragmentStatistic fragment = new FragmentStatistic(lifecycleOwner);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        fstatNull = new FragmentStatisticNull();
        Spinner spinnerStat = view.findViewById(R.id.statistic_spinner);
        final Button calendarButtonStat = view.findViewById(R.id.statistic_date);
        Button showButtonStat = view.findViewById(R.id.statistic_show);
        calendarButtonStat.setText("C " + DateResponse.getInstance().getDatesStatistic().get(0) + " по " + DateResponse.getInstance().getDatesStatistic().get(1));
        calendarButtonStat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
//                FragmentStatisticCalendar.getInstance().createAlertDialog(getActivity());
                new CalendarDialog(getContext(), new OnDaysSelectionListener() {
                    @Override
                    public void onDaysSelected(List<Day> selectedDays) {
                        if (selectedDays != null) {
                            DateResponse.getInstance().updateDateStatistic(selectedDays.get(0).getCalendar().getTime(),
                                    selectedDays.get(selectedDays.size()-1).getCalendar().getTime());
                        }
                    }
                });
            }
        });
        DateResponse.getInstance().getDateEntityState().observe(lifecycleOwner, new Observer<ArrayList<String>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(ArrayList<String> strings) {
                calendarButtonStat.setText("C " + DateResponse.getInstance().getDatesStatistic().get(0) + " по " + DateResponse.getInstance().getDatesStatistic().get(1));
            }
        });
        showButtonStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitCall.getInstance().statisticLoad();
            }
        });
        ftransaction  = getFragmentManager().beginTransaction();
        ftransaction.replace(R.id.containerStatistic, fstatNull);
        ftransaction.commit();
        RetrofitCall.getInstance().getStatisticEntityState().observe(lifecycleOwner, new Observer<StatisticEntity>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(StatisticEntity statistic) {
                fstatFull = new FragmentStatisticFull(statistic);
                ftransaction  = getFragmentManager().beginTransaction();
                ftransaction.replace(R.id.containerStatistic, fstatFull);
                ftransaction.commit();
            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
