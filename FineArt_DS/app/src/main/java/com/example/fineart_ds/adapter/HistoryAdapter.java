package com.example.fineart_ds.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fineart_ds.R;
import com.example.fineart_ds.model.ProductHis;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductHis> arrayListProductHis;


    public HistoryAdapter(Context context, ArrayList<ProductHis> arrayListLocBinhPhongThuy) {
        this.context = context;
        this.arrayListProductHis = arrayListLocBinhPhongThuy;
    }

    @Override
    public int getCount() {
        return arrayListProductHis.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProductHis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView mTvProductName, mTvProductPrice, mTvProductDes;
        public ImageView mImgProductImage;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HistoryAdapter.ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new HistoryAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_product_history, null);
            viewHolder.mTvProductName = (TextView) convertView.findViewById(R.id.mTvNameHis);
            viewHolder.mTvProductPrice = (TextView) convertView.findViewById(R.id.mTvPriceHis);
            viewHolder.mTvProductDes = (TextView) convertView.findViewById(R.id.mTvDesHis);
            viewHolder.mImgProductImage =(ImageView) convertView.findViewById(R.id.mImgHis);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (HistoryAdapter.ViewHolder) convertView.getTag();
        }
        ProductHis productHis = (ProductHis) getItem(position);
        viewHolder.mTvProductName.setText(productHis.getNameProduct());
        viewHolder.mTvProductPrice.setText("Giá: "+productHis.getPriceProduct());
        viewHolder.mTvProductDes.setText(productHis.getDesProduct());
        Picasso.with(context).load(productHis.getImageProduct()).placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.mImgProductImage);
        return convertView;
    }
}
