package com.training.Medianet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.Medianet.R;

/**
 * Created by root on 05/01/17.
 */
public class Grid_adapter extends BaseAdapter {

    private Context context;
    private String[] values;
    private int[] imageId;

    public Grid_adapter(Context context, String[] values, int[] prgmImages) {
        this.context = context;
        this.values = values;
        this.imageId = prgmImages;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid, null);
        holder.tv = (TextView) rowView.findViewById(R.id.grid_label);
        holder.img = (ImageView) rowView.findViewById(R.id.grid_image);

        holder.tv.setText(values[position]);
        holder.img.setImageResource(imageId[position]);


        return rowView;
    }
}
