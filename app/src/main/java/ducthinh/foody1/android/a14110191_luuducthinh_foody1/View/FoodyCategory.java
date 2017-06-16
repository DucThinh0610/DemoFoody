package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class FoodyCategory extends AppCompatActivity {//activity khi click vào F bên góc trái màn hình
    //tạo các mảng chứa drawable và text để đưa và gridview và show ra màn hình
    private static int[] img={R.drawable.gridgantoi,R.drawable.gridcoupon,R.drawable.griddatchouudai,R.drawable.griddatgiaohang
            ,R.drawable.gridecard,R.drawable.gridgame,R.drawable.gridbinhluan,R.drawable.gridblog};
    private static String[] text={"Ăn uống","Du lịch","Cưới hỏi","Đẹp khỏe","Giải trí","Mua sắm","Giáo dục","Dịch vụ"};
    ImageButton btnBack;
    CustomGrid customGrid;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foody_category);
        btnBack= (ImageButton) findViewById(R.id.btnBackFC);
        gridView= (GridView) findViewById(R.id.gridFoodyCategory);
        gridView.setAdapter(new CustomGrid(text,this,img));
        //sự kiện click thoát thì form sẽ đóng
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private class CustomGrid extends BaseAdapter {//custom 1 adapter
        String [] result;
        Context context;
        int [] imageId;
        private LayoutInflater inflater;
        public CustomGrid(String[] result, Context context, int[] imageId) {
            this.result = result;
            this.context = context;
            this.imageId = imageId;
            inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return result.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView==null){
                v=inflater.inflate(R.layout.foodycategory_grid,null);
            }
            else {
                v=convertView;
            }
            TextView textView=(TextView)v.findViewById(R.id.textView_foody);
            ImageView imageView= (ImageView) v.findViewById(R.id.grid_image_foody);
            textView.setText(result[position]);
            imageView.setImageResource(imageId[position]);
            return v;
        }

    }
}
