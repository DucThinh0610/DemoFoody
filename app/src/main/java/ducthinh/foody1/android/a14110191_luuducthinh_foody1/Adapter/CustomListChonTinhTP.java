package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.City;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

/**
 * Created by Test on 4/3/2017.
 */

public class CustomListChonTinhTP extends BaseAdapter {//listview danh sách các thành phố
    private Context context;
    boolean isSelected;
    List<City> cityList;

    public CustomListChonTinhTP(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=layoutInflater.inflate(R.layout.custom_list_chontp,null);
        }
        else{
            v=convertView;
        }
        TextView textView=(TextView)v.findViewById(R.id.textViewChonTP);
        textView.setText(cityList.get(position).getName());
        TextView textViewIdCity=(TextView)v.findViewById(R.id.idCitySelected);
        textViewIdCity.setVisibility(View.GONE);
        textViewIdCity.setText(String.valueOf(cityList.get(position).getId()));
        return v;
    }
}
