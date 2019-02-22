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

public class TranhGoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListTranhGo;

    public TranhGoAdapter(Context context, ArrayList<Product> arrayListTranhGo) {
        this.context = context;
        this.arrayListTranhGo = arrayListTranhGo;
    }

    @Override
    public int getCount() {
        return arrayListTranhGo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTranhGo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenTranhGo, txtGiaTranhGo, txtMoTaTranhGo;
        public ImageView imgTranhGo;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_tranh_go, null);
            viewHolder.txtTenTranhGo = (TextView) convertView.findViewById(R.id.textViewTranhGo);
            viewHolder.txtGiaTranhGo = (TextView) convertView.findViewById(R.id.textViewGiaTranhGo);
            viewHolder.txtMoTaTranhGo = (TextView) convertView.findViewById(R.id.textViewMoTaTranhGo);
            viewHolder.imgTranhGo =(ImageView) convertView.findViewById(R.id.imageViewTranhGo);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenTranhGo.setText(product.getProductName());
        viewHolder.txtGiaTranhGo.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaTranhGo.setMaxLines(2);
        viewHolder.txtMoTaTranhGo.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaTranhGo.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgTranhGo);
        return convertView;
    }
}
