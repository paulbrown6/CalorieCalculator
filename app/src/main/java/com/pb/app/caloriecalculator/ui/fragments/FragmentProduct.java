package com.pb.app.caloriecalculator.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LifecycleRegistryOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.api.DateResponse;
import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.entity.ProductEntity;
import com.pb.app.caloriecalculator.entity.ProductsEntity;
import com.pb.app.caloriecalculator.ui.adapters.AdapterProduct;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentProduct extends Fragment {

    static LifecycleOwner lifecycleOwner;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private AdapterProduct adapter;
    private TextView allCalorie;
    private TextView allProduct;
    private ArrayList<String> arrDay;
    private ArrayList<String> arrNumDay;
    private ArrayList<TextView> arrTextDay;
    private ArrayList<TextView> arrTextNumDay;
    private ArrayList<LinearLayout> arrLinDay;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @SuppressLint("ValidFragment")
    public FragmentProduct(LifecycleOwner owner) {
        lifecycleOwner = owner;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProduct.
     */

    public static FragmentProduct newInstance(String param1, String param2) {
        FragmentProduct fragment = new FragmentProduct(lifecycleOwner);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product, container, false);
        arrDay = DateResponse.getInstance().getArrDay();
        arrNumDay = DateResponse.getInstance().getArrNumDay();
        arrTextDay = new ArrayList<>();
        arrTextDay.add((TextView) view.findViewById(R.id.day_1));
        arrTextDay.add((TextView) view.findViewById(R.id.day_2));
        arrTextDay.add((TextView) view.findViewById(R.id.day_3));
        arrTextDay.add((TextView) view.findViewById(R.id.day_4));
        arrTextDay.add((TextView) view.findViewById(R.id.day_5));
        arrTextNumDay = new ArrayList<>();
        arrTextNumDay.add((TextView) view.findViewById(R.id.numb_1));
        arrTextNumDay.add((TextView) view.findViewById(R.id.numb_2));
        arrTextNumDay.add((TextView) view.findViewById(R.id.numb_3));
        arrTextNumDay.add((TextView) view.findViewById(R.id.numb_4));
        arrTextNumDay.add((TextView) view.findViewById(R.id.numb_5));
        arrLinDay = new ArrayList<>();
        arrLinDay.add((LinearLayout) view.findViewById(R.id.linear_befyesterday));
        arrLinDay.add((LinearLayout) view.findViewById(R.id.linear_yesterday));
        arrLinDay.add((LinearLayout) view.findViewById(R.id.linear_today));
        for (int i = 0; i < arrDay.size(); i++){
            arrTextDay.get(i).setText(arrDay.get(i));
            arrTextNumDay.get(i).setText(arrNumDay.get(i));
            if (i < 3) {
                arrLinDay.get(i).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View v) {
                        LinearLayout layout = (LinearLayout) v;
                        for (int i = 0; i < arrLinDay.size(); i++){
                            if (arrLinDay.get(i).equals(layout)){
                                arrTextNumDay.get(i).setBackgroundResource(R.drawable.ellip);
                                DateResponse.getInstance().updateDate(DateResponse.Date.values()[i]);
                                RetrofitCall.getInstance().callProducts();
                            } else {
                                arrTextNumDay.get(i).setBackground(v.getBackground());
                            }
                        }
                    }
                });
            }
        }
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Продукт добавлен", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentProductDialog.getInstance().createAlertDialog(getActivity(), true, new ProductEntity());
            }
        });
        allCalorie = view.findViewById(R.id.text_all_ccal);
        allProduct = view.findViewById(R.id.text_all_product);
        RetrofitCall.getInstance().callProducts();
        recyclerView = view.findViewById(R.id.product_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterProduct(new ArrayList<ProductEntity>(), getActivity());
        recyclerView.setAdapter(adapter);
        RetrofitCall.getInstance().getProductsEntityState().observe(lifecycleOwner, new Observer<ProductsEntity>() {
            @Override
            public void onChanged(ProductsEntity productsEntity) {
                recyclerView.setAdapter(new AdapterProduct(productsEntity.getProducts(), getActivity()));
//                adapter.setItems(productsEntity.getProducts());
                if (productsEntity.getProducts() != null) {
                    allProduct.setText(String.valueOf(productsEntity.getProducts().size()));
                    int sum = 0;
                    for (ProductEntity prod: productsEntity.getProducts()) {
                        sum += prod.getCalorieTotal();
                    }
                    allCalorie.setText(String.valueOf(sum));
                } else {
                    allProduct.setText("0");
                    allCalorie.setText("0");
                }

            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
