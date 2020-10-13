package com.pb.app.caloriecalculator.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.api.DateResponse;
import com.pb.app.caloriecalculator.entity.StatisticEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentStatisticFull#newInstance} factory method to
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class FragmentStatisticFull extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static StatisticEntity statistic;

    private String mParam1;
    private String mParam2;


    @SuppressLint("ValidFragment")
    public FragmentStatisticFull(StatisticEntity statistic) {
        this.statistic = statistic;
    }

    public static FragmentStatisticFull newInstance(String param1, String param2) {
        FragmentStatisticFull fragment = new FragmentStatisticFull(statistic);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic_full, container, false);
        GraphView graphView = view.findViewById(R.id.statistic_graph);
        DataPoint[] points = new DataPoint[statistic.getData().getData().size()];
        for (int i = 0; i < statistic.getData().getData().size(); i++){
            points[i] = new DataPoint(DateResponse.getInstance().stringToDate(statistic.getData().getData().get(i)), statistic.getData().getCalories().get(i));
        }
        graphView.addSeries(new LineGraphSeries<DataPoint>(points));
        TextView allProducts = view.findViewById(R.id.statistic_all_products);
        allProducts.setText(String.valueOf(statistic.getCountProducts()));
        TextView allCalories = view.findViewById(R.id.statistic_all_calories);
        allCalories.setText(String.valueOf(statistic.getCalorieSum()));
        TextView sumProductsNum = view.findViewById(R.id.statistic_product_num);
        sumProductsNum.setText(String.valueOf(statistic.getProductNumSum()));
        TextView caloriesDay = view.findViewById(R.id.statistic_calories_day);
        caloriesDay.setText(String.valueOf(statistic.getAvgCalories()));
        TextView biggestProduct = view.findViewById(R.id.statistic_biggest_product);
        biggestProduct.setText(statistic.getBiggestProduct().getName());
        TextView biggestProductCcal = view.findViewById(R.id.statistic_biggest_ccal);
        biggestProductCcal.setText(statistic.getBiggestProduct().getCalorieTotal() + " ккал");
        TextView smallestProduct = view.findViewById(R.id.statistic_smallest_product);
        smallestProduct.setText(statistic.getSmallestProduct().getName());
        TextView smallestProductCcal = view.findViewById(R.id.statistic_smallest_ccal);
        smallestProductCcal.setText(statistic.getSmallestProduct().getCalorieTotal() + " ккал");
        return view;
    }
}