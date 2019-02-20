package com.example.fineart_devpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fineart_devpro.R;
import com.example.fineart_devpro.model.ProductType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductTypeAdapter extends BaseAdapter {
    ArrayList<ProductType> arrayListProductType;
    Context context;

    public ProductTypeAdapter(ArrayList<ProductType> arrayListProductType, Context context) {
        this.arrayListProductType = arrayListProductType;
        this.context = context;
    }

    public ProductTypeAdapter() {
        this.arrayListProductType = arrayListProductType;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListProductType.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProductType.get(position);
    }

    @Override
    public long getItemId(int posotion) {
        return posotion;
    }

    public class ViewHolder{
        ImageView imgProductType;
        TextView txtProducTypeName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater  = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.line_listview_product_type,null);
            viewHolder.txtProducTypeName = (TextView)convertView.findViewById(R.id.textViewProductType);
            viewHolder.imgProductType = (ImageView)convertView.findViewById(R.id.imageViewProductType);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        ProductType productType = (ProductType)getItem(position);
        viewHolder.txtProducTypeName.setText(productType.getProductTypeName());
        Picasso.with(context).load(productType.getProductTypeImage()).placeholder(R.drawable.loading).error(R.drawable.error)
                .into(viewHolder.imgProductType);

        return convertView;
    }
}
