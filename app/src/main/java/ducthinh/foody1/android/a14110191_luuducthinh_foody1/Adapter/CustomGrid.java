package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

/**
 * Created by Test on 3/27/2017.
 */

public class CustomGrid extends BaseAdapter {//tạo 1 custom grid để hiện thi listviwe mới nhất. extend từ baseapdater
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    public CustomGrid(Activity c, String[] web, int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }
    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.grid_layout_custom, null);
        }else{
            v = convertView;
        }
        TextView tv = (TextView)v.findViewById(R.id.grid_text);
        tv.setText(web[position]);
        ImageView iv = (ImageView)v.findViewById(R.id.grid_image);
        iv.setImageResource(Imageid[position]);

        return v;
    }
}
