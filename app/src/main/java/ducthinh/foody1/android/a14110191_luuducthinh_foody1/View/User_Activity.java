package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class User_Activity extends AppCompatActivity {//màn hình user
    public static LinearLayout llnologin,lllogin;
    //các hình ảnh và text để hiện thị lên gridview
    private int[] img={R.drawable.griddatchouudai,R.drawable.griddatgiaohang,R.drawable.gridcoupon,R.drawable.gridecard,R.drawable.foody_pos,
            R.drawable.ic_info_contact,R.drawable.ic_e_coupon_buy_credits_black,R.drawable.ic_more_payment,R.drawable.ic_more_setting,
            R.drawable.activities_social,R.drawable.ic_share_gmail_2,R.drawable.ic_more_setting,R.drawable.ic_logout};
    private String[] text={"Lịch sử đặt chỗ","Lịch sử đặt giao hàng","Lịch sử Coupon","Sử dụng Ecard","Lịch sử Eat-in/Take away",
            "Thông tin liên hệ","Tiền thưởng","Thanh toán","Thiết lập tài khoản","Mời bạn bè","Góp ý","Cài đặt chung","Đăng xuất"};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_);

        listView=(ListView)findViewById(R.id.listViewUser);
        llnologin=(LinearLayout)findViewById(R.id.llnologin);
        lllogin=(LinearLayout)findViewById(R.id.lllogin);
        //sự kiện click login sẽ chuyển màn hình
        llnologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        //hiện thị các mục trong listview user
        listView.setAdapter(new CustomListUser(img,text,this));
    }
    private class CustomListUser extends BaseAdapter {
        private int[] img;
        private String[] text;
        private LayoutInflater inflater;
        Context context;

        public CustomListUser(int[] img, String[] text, Context context) {
            this.img = img;
            this.text = text;
            this.context = context;
            inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
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
                v=inflater.inflate(R.layout.user_list_custom,null);
            }
            else {
                v=convertView;
            }
            TextView textView= (TextView) v.findViewById(R.id.textView_user_list);
            ImageView imageView= (ImageView) v.findViewById(R.id.imageView_user_list);
            textView.setText(text[position]);
            imageView.setImageResource(img[position]);
            return v;
        }
    }
}
