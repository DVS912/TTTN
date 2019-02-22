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

public class CayBonSaiGoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListCayBonSaiGo;

    public CayBonSaiGoAdapter(Context context, ArrayList<Product> arrayListCayBonSaiGo) {
        this.context = context;
        this.arrayListCayBonSaiGo = arrayListCayBonSaiGo;
    }

    @Override
    public int getCount() {
        return arrayListCayBonSaiGo.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListCayBonSaiGo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtTenCayBonSaiGo, txtGiaCayBonSaiGo, txtMoTaCayBonSaiGo;
        public ImageView imgCayBonSaiGo;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_cay_bon_sai_go, null);
            viewHolder.txtTenCayBonSaiGo = (TextView) convertView.findViewById(R.id.textViewCayBonSaiGo);
            viewHolder.txtGiaCayBonSaiGo = (TextView) convertView.findViewById(R.id.textViewGiaCayBonSaiGo);
            viewHolder.txtMoTaCayBonSaiGo = (TextView) convertView.findViewById(R.id.textViewMoTaCayBonSaiGo);
            viewHolder.imgCayBonSaiGo =(ImageView) convertView.findViewById(R.id.imageViewCayBonSaiGo);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.txtTenCayBonSaiGo.setText(product.getProductName());
        viewHolder.txtGiaCayBonSaiGo.setText("Giá: "+product.getProductPrice());
        viewHolder.txtMoTaCayBonSaiGo.setMaxLines(2);
        viewHolder.txtMoTaCayBonSaiGo.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaCayBonSaiGo.setText(product.getProductDescription());
        Picasso.with(context).load(product.getProductImage()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgCayBonSaiGo);
        return convertView;
    }
}
