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

public class TuongLinhVatAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListTuongLinhVat;

    public TuongLinhVatAdapter(Context context, ArrayList<Product> arrayListTuongLinhVat) {
        this.context = context;
        this.arrayListTuongLinhVat = arrayListTuongLinhVat;
    }

    @Override
    public int getCount() {
        return arrayListTuongLinhVat.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTuongLinhVat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenTuongLinhVat, txtGiaTuongLinhVat, txtMoTaTuongLinhVat;
        public ImageView imgTuongLinhVat;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_tuong_go_phong_thuy, null);
            viewHolder.txtTenTuongLinhVat = (TextView) convertView.findViewById(R.id.textViewTuongGoPhongThuy);
            viewHolder.txtGiaTuongLinhVat = (TextView) convertView.findViewById(R.id.textViewGiaTuongGo);
            viewHolder.txtMoTaTuongLinhVat = (TextView) convertView.findViewById(R.id.textViewMoTaTuongGo);
            viewHolder.imgTuongLinhVat =(ImageView) convertView.findViewById(R.id.imageViewTuongGoPhongThuy);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenTuongLinhVat.setText(product.getProductName());
        viewHolder.txtGiaTuongLinhVat.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaTuongLinhVat.setMaxLines(2);
        viewHolder.txtMoTaTuongLinhVat.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaTuongLinhVat.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgTuongLinhVat);
        return convertView;
    }
}
