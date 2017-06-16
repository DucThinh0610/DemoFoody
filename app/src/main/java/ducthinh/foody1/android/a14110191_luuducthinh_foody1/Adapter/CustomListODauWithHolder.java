package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.GetIP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Restaurant;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

/**
 * Created by Test on 4/8/2017.
 */

public class CustomListODauWithHolder extends BaseAdapter {//tạo custom listview để hiện thị quán ăn ở đâu
    private List<Restaurant> addressOfFoods;
    Context context;
    LayoutInflater inflater;
    public CustomListODauWithHolder(List<Restaurant> addressOfFoods, Context context) {
        this.addressOfFoods = addressOfFoods;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return addressOfFoods.size();
    }

    @Override
    public Object getItem(int position) {
        return addressOfFoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.list_home_odau,null);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imageViewODau);
            viewHolder.buttonRate=(Button)convertView.findViewById(R.id.btnRate);
            viewHolder.textViewAddress=(TextView)convertView.findViewById(R.id.textViewTenDuong);
            viewHolder.textViewTenMonAn=(TextView)convertView.findViewById(R.id.textViewFoodODau);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        Picasso.with(context).load(new GetIP().getIP(2) + this.addressOfFoods.get(position).getPhoto().trim() + ".png")
                .placeholder(R.drawable.dn_ic_view_error).into(viewHolder.imageView);
        //Log.d("Link anh","http://foodyfinal.somee.com/img/" + this.addressOfFoods.get(position).getPhoto().trim() + ".png");
        String a=String.valueOf(this.addressOfFoods.get(position).getAvg_rating());
        viewHolder.buttonRate.setText(String.valueOf(df.format(Double.parseDouble(a))));
        viewHolder.textViewAddress.setText(this.addressOfFoods.get(position).getAddress());
        viewHolder.textViewTenMonAn.setText(this.addressOfFoods.get(position).getName());
        viewHolder.textViewTenMonAn.setTypeface(null, Typeface.BOLD);
        return convertView;
    }

    public void addListItemToAdapter(List<Restaurant> lst) {
        addressOfFoods.addAll(lst);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        public ImageView imageView;//=(ImageView)v.findViewById(R.id.imageViewODau);
        public Button buttonRate;//=(Button)v.findViewById(R.id.btnRate);
        public TextView textViewTenMonAn;//=(TextView)v.findViewById(R.id.textViewFoodODau);
        public TextView textViewAddress;//=(TextView)v.findViewById(R.id.textViewTenDuong);
    }

}
