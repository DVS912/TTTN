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

public class TuKeTiviAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListTuKeTivi;

    public TuKeTiviAdapter(Context context, ArrayList<Product> arrayListTuKeTivi) {
        this.context = context;
        this.arrayListTuKeTivi = arrayListTuKeTivi;
    }

    @Override
    public int getCount() {
        return arrayListTuKeTivi.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTuKeTivi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenTuKeTivi, txtGiaTuKeTivi, txtMoTaTuKeTivi;
        public ImageView imgTuKeTivi;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_tu_ke_tivi, null);
            viewHolder.txtTenTuKeTivi = (TextView) convertView.findViewById(R.id.textViewTuKeTivi);
            viewHolder.txtGiaTuKeTivi = (TextView) convertView.findViewById(R.id.textViewGiaTuKeTivi);
            viewHolder.txtMoTaTuKeTivi = (TextView) convertView.findViewById(R.id.textViewMoTaTuKeTivi);
            viewHolder.imgTuKeTivi =(ImageView) convertView.findViewById(R.id.imageViewTuKeTivi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenTuKeTivi.setText(product.getProductName());
        viewHolder.txtGiaTuKeTivi.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaTuKeTivi.setMaxLines(2);
        viewHolder.txtMoTaTuKeTivi.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaTuKeTivi.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgTuKeTivi);
        return convertView;
    }
}
