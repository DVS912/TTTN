package com.example.fineart_ds.adapter;

import android.content.Context;
import android.text.TextUtils;
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

public class LocBinhPhongThuyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListLocBinhPhongThuy;

    public LocBinhPhongThuyAdapter(Context context, ArrayList<Product> arrayListLocBinhPhongThuy) {
        this.context = context;
        this.arrayListLocBinhPhongThuy = arrayListLocBinhPhongThuy;
    }

    @Override
    public int getCount() {
        return arrayListLocBinhPhongThuy.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLocBinhPhongThuy.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenLocBinhPhongThuy, txtGiaLocBinhPhongThuy, txtMoTaLocBinhPhongThuy;
        public ImageView imgLocBinhPhongThuy;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_loc_binh_phong_thuy, null);
            viewHolder.txtTenLocBinhPhongThuy = (TextView) convertView.findViewById(R.id.textViewLocBinhPhongThuy);
            viewHolder.txtGiaLocBinhPhongThuy = (TextView) convertView.findViewById(R.id.textViewGiaLocBinhPhongThuy);
            viewHolder.txtMoTaLocBinhPhongThuy = (TextView) convertView.findViewById(R.id.textViewMoTaLocBinhPhongThuy);
            viewHolder.imgLocBinhPhongThuy =(ImageView) convertView.findViewById(R.id.imageViewLocBinhPhongThuy);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenLocBinhPhongThuy.setText(product.getProductName());
        viewHolder.txtGiaLocBinhPhongThuy.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaLocBinhPhongThuy.setMaxLines(2);
        viewHolder.txtMoTaLocBinhPhongThuy.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaLocBinhPhongThuy.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgLocBinhPhongThuy);
        return convertView;
    }
}
