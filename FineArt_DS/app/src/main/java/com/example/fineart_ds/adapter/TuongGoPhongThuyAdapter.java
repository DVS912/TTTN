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

public class TuongGoPhongThuyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListTuongGoPhongThuy;

    public TuongGoPhongThuyAdapter(Context context, ArrayList<Product> arrayListTuongGoPhongThuy) {
        this.context = context;
        this.arrayListTuongGoPhongThuy = arrayListTuongGoPhongThuy;
    }

    @Override
    public int getCount() {
        return arrayListTuongGoPhongThuy.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTuongGoPhongThuy.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenTuongGo, txtGiaTuongGo, txtMoTaTuongGo;
        public ImageView imgTuongGo;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_tuong_go_phong_thuy, null);
            viewHolder.txtTenTuongGo = (TextView) convertView.findViewById(R.id.textViewTuongGoPhongThuy);
            viewHolder.txtGiaTuongGo = (TextView) convertView.findViewById(R.id.textViewGiaTuongGo);
            viewHolder.txtMoTaTuongGo = (TextView) convertView.findViewById(R.id.textViewMoTaTuongGo);
            viewHolder.imgTuongGo =(ImageView) convertView.findViewById(R.id.imageViewTuongGoPhongThuy);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenTuongGo.setText(product.getProductName());
        viewHolder.txtGiaTuongGo.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaTuongGo.setMaxLines(2);
        viewHolder.txtMoTaTuongGo.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaTuongGo.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgTuongGo);
        return convertView;
    }
}
