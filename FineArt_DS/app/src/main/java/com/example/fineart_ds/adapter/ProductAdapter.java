package com.example.fineart_ds.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fineart_ds.R;
import com.example.fineart_ds.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
    Context context;
    ArrayList<Product> arrayListProduct;

    public ProductAdapter(Context context, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.line_new_product, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        Product product = arrayListProduct.get(i);
        itemHolder.txtProductName.setText(product.getProductName());
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.txtProductPrice.setText("Giá: "+product.getProductPrice());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error).into(itemHolder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return arrayListProduct.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgProduct;
        public TextView txtProductName;
        public TextView txtProductPrice;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imageViewNewProduct);
            txtProductName = (TextView) itemView.findViewById(R.id.textViewProductName);
            txtProductPrice = (TextView) itemView.findViewById(R.id.textViewProductPrice);
        }
    }
}
