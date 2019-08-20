package com.example.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<SanPham> {

    private Context context;
    private int resource;
    private List<SanPham> arrSanPham;

    public CustomAdapter(Context context, int resource, ArrayList<SanPham> arrSanPham) {
        super(context, resource, arrSanPham);
        this.context = context;
        this.resource = resource;
        this.arrSanPham = arrSanPham;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageNews = (ImageView) convertView.findViewById(R.id.image_news);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.category);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = arrSanPham.get(position);
        viewHolder.imageNews.setImageBitmap(sanPham.getHinhanh());
        viewHolder.tvTitle.setText(sanPham.getTensp());
        viewHolder.tvCategory.setText(sanPham.getLoai());
        return convertView;
    }

    public class ViewHolder {
        ImageView imageNews;
        TextView tvTitle,tvCategory;

    }
    public void refresh(List<SanPham> newsList){
        this.arrSanPham = newsList;
        notifyDataSetChanged();
    }
}
