package com.pb.app.caloriecalculator.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.pb.app.caloriecalculator.R;
import com.pb.app.caloriecalculator.api.DateResponse;
import com.pb.app.caloriecalculator.api.RetrofitCall;
import com.pb.app.caloriecalculator.entity.ProductEntity;
import com.pb.app.caloriecalculator.ui.fragments.FragmentProductDialog;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private List<ProductEntity> products;
    private Activity activity;

    public AdapterProduct(List<ProductEntity> products, Activity activity) {
        this.products = products;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductEntity post = products.get(position);
        holder.product = post;
        holder.activity = activity;
        holder.name.setText(post.getName());
        holder.ccal.setText(post.getCalorieTotal().toString());
    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }

    public void setItems(List<ProductEntity> prod) {
        products.clear();
        products.addAll(prod);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linear;
        TextView name;
        TextView ccal;
        Button edit;
        Button delete;
        ProductEntity product;
        Activity activity;

        public ViewHolder(View itemView) {
            super(itemView);
            linear = (LinearLayout) itemView.findViewById(R.id.product_adapter);
            name = (TextView) itemView.findViewById(R.id.adapter_product_name);
            ccal = (TextView) itemView.findViewById(R.id.adapter_ccal);
            edit = (Button) itemView.findViewById(R.id.adapter_product_edit);
            delete = (Button) itemView.findViewById(R.id.adapter_product_delete);
            if (DateResponse.getInstance().isDateToday()) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentProductDialog.getInstance().createAlertDialog(activity, false, product);
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitCall.getInstance().deleteProduct(product.getId());
                    }
                });
            } else {
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
            }
        }
    }
}
