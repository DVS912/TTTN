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

public class BanGheAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListBanGhe;

    public BanGheAdapter(Context context, ArrayList<Product> arrayListBanGhe) {
        this.context = context;
        this.arrayListBanGhe = arrayListBanGhe;
    }

    @Override
    public int getCount() {
        return arrayListBanGhe.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListBanGhe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenBanGhe, txtGiaBanGhe, txtMoTaBanGhe;
        public ImageView imgBanGhe;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_ban_ghe, null);
            viewHolder.txtTenBanGhe = (TextView) convertView.findViewById(R.id.textViewBanGhe);
            viewHolder.txtGiaBanGhe = (TextView) convertView.findViewById(R.id.textViewGiaBanGhe);
            viewHolder.txtMoTaBanGhe = (TextView) convertView.findViewById(R.id.textViewMoTaBanGhe);
            viewHolder.imgBanGhe =(ImageView) convertView.findViewById(R.id.imageViewBanGhe);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenBanGhe.setText(product.getProductName());
        viewHolder.txtGiaBanGhe.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaBanGhe.setMaxLines(2);
        viewHolder.txtMoTaBanGhe.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaBanGhe.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgBanGhe);
        return convertView;
    }
}
