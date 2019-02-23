package com.example.fineart_ds.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fineart_ds.R;
import com.example.fineart_ds.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterSanPham extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<Product> productArrayList;

    public CustomAdapterSanPham(Context context, int layout, ArrayList<Product> productArrayList) {
        this.context = context;
        this.layout = layout;
        this.productArrayList = productArrayList;
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder{
        TextView txtTenSP;
        TextView txtGiaSP;
        ImageView ImgSP;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);
            holder.txtTenSP = convertView.findViewById(R.id.textViewProductName);
            holder.txtGiaSP = convertView.findViewById(R.id.textViewProductPrice);
            holder.ImgSP = convertView.findViewById(R.id.imageViewNewProduct);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        Product product =  productArrayList.get(position);
        holder.txtTenSP.setText(product.getProductName());
        holder.txtGiaSP.setText(product.getProductPrice());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error).into(holder.ImgSP);

        return convertView;
    }
}
