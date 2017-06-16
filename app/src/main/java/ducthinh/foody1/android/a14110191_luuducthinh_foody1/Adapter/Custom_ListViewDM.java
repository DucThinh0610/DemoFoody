package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

/**
 * Created by Test on 3/31/2017.
 */

public class Custom_ListViewDM extends BaseAdapter {//tạo 1 custom list để hiện thị list danh mục
    private Context mContext;
    private ArrayList<Object> typeListViewImage;//danh sách hình ảnh
    private LayoutInflater inflater;
    //chia làm 2 loại 1 cái là header 1 cái là nội dung
    private static final int TYPE_IMAGE=0;//
    private static final int TPYE_NORMAL=1;

    public Custom_ListViewDM(Activity c, ArrayList<Object> typeListViewImage) {
        mContext=c;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.typeListViewImage = typeListViewImage;
    }

    @Override
    public int getCount() {
        return typeListViewImage.size();
    }

    @Override
    public Object getItem(int position) {
        return typeListViewImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount(){
        return 2;
    }
    @Override
    public int getItemViewType(int position){
        if(getItem(position) instanceof TypeListViewImage){
            return TYPE_IMAGE;
        }
        else return TPYE_NORMAL;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        int type=getItemViewType(position);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (type){
                case TYPE_IMAGE://nếu là nội dung thì hiển thị có hình ảnh kèm theo
                    v=inflater.inflate(R.layout.listdanhmuc, null);
                    break;
                case TPYE_NORMAL://ngược lại chỉ hiển thị chữ
                    v=inflater.inflate(R.layout.headerdanhmuc, null);
                    break;
            }
        }
        else {
            v=convertView;
        }
        switch (type){
            case TYPE_IMAGE://ánh xạ cho các thẻ imageview hoặc textview
                TypeListViewImage typeListViewImage=(TypeListViewImage) getItem(position);
                TextView textViewName=(TextView)v.findViewById(R.id.textviewofDM);
                ImageView imageView=(ImageView)v.findViewById(R.id.imgDanhMuc);
                textViewName.setText(typeListViewImage.getName());
                imageView.setImageResource(typeListViewImage.getImage());
                break;
            case TPYE_NORMAL:
                //ánh xạ cho textview
                TextView header=(TextView)v.findViewById(R.id.textviewofheader);
                header.setText((String)getItem(position));
                break;
                
        }
        return v;
    }
}
