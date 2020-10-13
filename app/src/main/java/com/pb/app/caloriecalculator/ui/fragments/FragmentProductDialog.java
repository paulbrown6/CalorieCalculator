package com.pb.app.caloriecalculator.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.entity.ProductEntity;

public class FragmentProductDialog {

    private static FragmentProductDialog instance;
    private FragmentProductDialog(){}

    private static AlertDialog alert;
    private EditText nameProduct;
    private EditText numberGram;
    private EditText numberCcal;
    private Spinner spinnerCcal;
    private Button buttonProduct;
    private ProductEntity productEntity;

    public static FragmentProductDialog getInstance() {
        if (instance == null){
            instance = new FragmentProductDialog();
        }
        return instance;
    }

    public AlertDialog createAlertDialog(Activity activity, Boolean type, final ProductEntity product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_product_dialog, null)).setCancelable(true);
        alert = builder.create();
        alert.show();
        productEntity = product;
        nameProduct = (EditText) alert.findViewById(R.id.name_product);
        numberGram = (EditText) alert.findViewById(R.id.number_gram);
        numberCcal = (EditText) alert.findViewById(R.id.number_ccal);
        spinnerCcal = (Spinner) alert.findViewById(R.id.ccal_spinner);
        buttonProduct = (Button) alert.findViewById(R.id.button_add_product);
        if (type) {
            buttonProduct.setText("Добавить");
            buttonProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productEntity = new ProductEntity(nameProduct.getText().toString(), Integer.parseInt(numberGram.getText().toString()), Integer.parseInt(numberCcal.getText().toString()), spinnerCcal.getSelectedItemPosition());
                    RetrofitCall.getInstance().addProduct(productEntity);
                    alert.dismiss();
                }
            });
        } else {
            buttonProduct.setText("Изменить");
            nameProduct.setText(product.getName());
            numberGram.setText(product.getProductNum().toString());
            numberCcal.setText(product.getCalorieNum().toString());
            spinnerCcal.setSelection(product.getCountingType());
            buttonProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productEntity.setName(nameProduct.getText().toString());
                    productEntity.setProductNum(Integer.parseInt(numberGram.getText().toString()));
                    productEntity.setCalorieNum(Integer.parseInt(numberCcal.getText().toString()));
                    productEntity.setCountingType(spinnerCcal.getSelectedItemPosition());
                    RetrofitCall.getInstance().editProduct(productEntity);
                    alert.dismiss();
                }
            });
        }
        return alert;
    }
}
