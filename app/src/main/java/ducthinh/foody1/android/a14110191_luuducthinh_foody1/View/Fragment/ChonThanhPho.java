package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.CustomListChonTinhTP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.DBConnect;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.City;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class ChonThanhPho extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CustomListChonTinhTP customListChonTinhTP;
    List<City> cityList;//show ra list thành phố lấy dữ liệu từ database
    DBConnect dbConnect;
    ListView listView;
    TextView textView,textViewIDCity;//hiện thị tên thành phố khi được chọn
    Button btnxong;//button khi chọn xong việc đổi thành phố
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_thanh_pho);
        //tìm các id cần thiết
        textView=(TextView)findViewById(R.id.textViewTPDaChon) ;
        listView=(ListView)findViewById(R.id.listViewChonTinhThanh) ;
        textViewIDCity=(TextView)findViewById(R.id.textViewIDCity);
        textViewIDCity.setVisibility(View.GONE);
        dbConnect=new DBConnect(this);
        cityList=new ArrayList<>();
        cityList=dbConnect.getListCity();//lấy danh sách các thành phố trong database
        customListChonTinhTP=new CustomListChonTinhTP(this,cityList);
        listView.setAdapter(customListChonTinhTP);
        listView.setOnItemClickListener(this);
        Intent intent2=getIntent();//nhận giá trị (tên thành phố+id thành phố đó) từ fragment ăn gì hoặc ở đâu
        Bundle bundle=intent2.getExtras();
        String kqtruocdo="";
        int idcitypre;
        if (bundle!=null){
            kqtruocdo=bundle.getString("TPDaChon");//lấy giá trị được gưi qua
            idcitypre=bundle.getInt("idCity");
            textView.setText(kqtruocdo);//gán giá trị nhận được lên textview
            textViewIDCity.setText(String.valueOf(idcitypre));
        }
        else{
            textView.setText("Chưa chọn TP");
        }
        btnxong=(Button)findViewById(R.id.btnchonxongtp);
        btnxong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //khi mà click xong thì sẽ truyền gói chứa id +tên thành phố trở lại fragment
                Intent intent2=new Intent();
                intent2.putExtra("chonxongtp",textView.getText());
                intent2.putExtra("idcityselected",textViewIDCity.getText());
                setResult(1,intent2);
                finish();//đóng form
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//sự kiện click vào 1 item trong listview thì sẽ show ra textview cho người dùng xem
        TextView textView1=(TextView)view.findViewById(R.id.textViewChonTP);
        TextView textViewid=(TextView)view.findViewById(R.id.idCitySelected);
        textViewIDCity.setText(textViewid.getText());
        textView.setText(textView1.getText());
    }
}
