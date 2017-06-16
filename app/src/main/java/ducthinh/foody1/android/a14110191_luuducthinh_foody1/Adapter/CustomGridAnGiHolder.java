package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.GetIP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Restaurant;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

/**
 * Created by Test on 4/5/2017.
 */

public class CustomGridAnGiHolder extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Restaurant> addressOfFoods;
    //private LruCache<String, Bitmap> mMemoryCache;
    public CustomGridAnGiHolder(Context context, List<Restaurant> addressOfFoods) {
        this.context = context;
        this.addressOfFoods = addressOfFoods;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
//        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//
//        // Use 1/8th of the available memory for this memory cache.
//        final int cacheSize = maxMemory / 8;
//
//        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
//
//            protected int sizeOf(String key, Bitmap bitmap) {
//                // The cache size will be measured in bytes rather than number
//                // of items.
//                return bitmap.getByteCount();
//            }
//
//        };
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.customgridangi,null);
            viewHolder.getTextViewTenQuanAn=(TextView)convertView.findViewById(R.id.textViewTenQuanAG);
            viewHolder.textViewDiaChi=(TextView)convertView.findViewById(R.id.textViewDiaChiAG);
            viewHolder.textViewTenMonAn=(TextView)convertView.findViewById(R.id.textViewTenMonAnAG);
            viewHolder.imageViewGridAG=(ImageView)convertView.findViewById(R.id.imageViewGridAG);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //sử dụng thư việc có sẵn để load hình ảnh
        Picasso.with(context).load(new GetIP().getIP(2) + this.addressOfFoods.get(position).getPhoto().trim() + ".png")
                .placeholder(R.drawable.dn_ic_view_error).into(viewHolder.imageViewGridAG);
        viewHolder.getTextViewTenQuanAn.setText(this.addressOfFoods.get(position).getName());//setText cho textview tương tự
        viewHolder.getTextViewTenQuanAn.setTypeface(null, Typeface.BOLD);
        viewHolder.textViewTenMonAn.setText(this.addressOfFoods.get(position).getTenmonan());
        viewHolder.textViewDiaChi.setText(this.addressOfFoods.get(position).getAddress());
        return convertView;
    }
    private static class ViewHolder{//tạo lớp chứa các mục hiển thị ra màn hình
        public TextView textViewTenMonAn;
        public ImageView imageViewGridAG;
        public TextView getTextViewTenQuanAn;
        public TextView textViewDiaChi;
    }
    public void addListItemToAdapter(List<Restaurant> lst){
        addressOfFoods.addAll(lst);
        notifyDataSetChanged();
    }

}
