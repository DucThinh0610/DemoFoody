package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.CustomAdapter;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.CustomGrid;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.CustomGridAnGiHolder;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.CustomListODauWithHolder;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.Custom_ListViewDM;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.ExpandableListAdapter;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.TypeListViewImage;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Custom.CustomScroll;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Custom.ExpandableHeightGridView;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Custom.ScrollViewListener;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.DBConnect;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.GetIP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.getURL;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.HomeActivity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.AddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.District;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Restaurant;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.Street;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Presentation.PreAddressOfFood;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.MainActivity;

/**
 * Created by Test on 4/10/2017.
 */

public class FragmentAnGi extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String BASE_URL="";
    private static String URL ="" ;
    //kế thừa phương thức OnRefesh để load lại dữ liệu ban đầu
    //tao 1 SwipeRefresh
    private SwipeRefreshLayout swipeRefresh;
    //tao 1 framelayout khi laod du lieu sẽ có màn hình tải
    FrameLayout frameLayout;
    PreAddressOfFood preAddressOfFood;
    //tạo scrollview
    CustomScroll scrollViewGrid;
    //khoi tao so luong load du liệu ban đầu
    int prePos=0;
    //khai bao adapter cho gridview
    CustomGridAnGiHolder customGridAnGiHolder;
    //khai bao mang du lieu do vao gridview
    List<Restaurant> addressOfFoods;
    //ket noi csdl
    DBConnect dbConnect;
    ListView listViewMoiNhatag,listViewDanhMucag;//listview mới nhất và listviwe danh mục
    LinearLayout linearLayoutag,lldoiTPAG;// các linear layout chứa các nội dung cần thiết, bao gồm liear nội dung và linaer chứa nút đổi thành phố
    ExpandableHeightGridView gridViewag,gridViewDataAG;//sử dụng ExpandableHeightGridView đế scroll 2 gridview cùng 1 lúc
    public static int sttTab=0;
    ExpandableListAdapter listAdapterag;//apdapter cho listview thành phố
    ExpandableListView listTPAG;//listview có dạng expandable
    //các list để sử dụng trong việc tạo axpandablelist thành phố
    List<District> listDataHeaderag;
    List<Integer> listSoDuongag;
    HashMap<District, List<Street>> listDataChildag;//end expandable
    Button btnMoiNhatag, btnDanhMucag, btnThanhPhoag,btnDoiThanhPho;//các nút để show ra listview tượng ứng
    public static Button btnHuyMoiag,btnHuyDMag,btnHuyTPag;//các nút hủy để chế độ public đê qua bên fragment ODau gọi lại tránh bị bug giao diện
    Boolean showlistag=false;//tao 1 biến nhớ để show các list ra
    TextView textViewTenTP;
    //show ra error khi không load được dữ liệu
    ImageView imgError;
    private static int[] imgGird={R.drawable.gridgantoi,R.drawable.gridcoupon,R.drawable.griddatchouudai,R.drawable.griddatgiaohang
            ,R.drawable.gridecard,R.drawable.gridgame,R.drawable.gridbinhluan,R.drawable.gridblog,R.drawable.gridtopthanhvien,R.drawable.gridvideo};
    private static String[] nameGrid={"Gần tôi","Coupon", "Đặt chỗ ưu đãi", "Đặt giao hàng","E-card", "Game & Fun","Bình luận",
            "Blogs", "Top thành viên", "Video"};
    private static int[] imgODau={R.drawable.moinhat,R.drawable.ganday,R.drawable.moinhat,R.drawable.dukhach};
    private static int[] imgODauSelected={R.drawable.actmoinhat,R.drawable.actganday,R.drawable.actmoinhat,R.drawable.actdukhach};
    private static String[] nameList={"Mới nhất","Gần tôi","Xem nhiều", "Du khách"};
    //
    private boolean firstLoad=true,loadmore=true;
    //tạo ra các biến lọc dữ liệu

    private boolean LOAD_BY_NEW = true;//load mới dữ liệu
    private boolean LOAD_BY_CITY=false;//lọc theo thành phố
    private boolean LOAD_BY_STREET=false;//lọc theo đường
    private boolean LOAD_BY_DISTRICT=false;//lọc theo quận
    private boolean LOAD_BY_CATEGORY=false;//lọc theo danh mục
    private int idCity=1,idDistrict=1,idCategory=0,idStreet=1;
    //sự kiện nhận giá trị trả về từ form đổi thành phố
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data!=null){//khi mà giá trị nhận về khác null
            //set giá trị nhận về cho idCity và textview hiện thị trong listthah phố
            textViewTenTP.setText(data.getStringExtra("chonxongtp"));
            idCity=Integer.parseInt(data.getStringExtra("idcityselected"));
            prepareListData();//load lại dữ liệu cho list thành phố
            listAdapterag = new ExpandableListAdapter(getActivity(), listDataHeaderag, listDataChildag,listSoDuongag);
            listTPAG.setAdapter(listAdapterag);
            //end list thành phố
            btnThanhPhoag.performClick();
            //khơi tộ lại các biến lọc
            LOAD_BY_CITY=true;
            LOAD_BY_STREET=false;
            LOAD_BY_DISTRICT=false;
            btnThanhPhoag.setText(data.getStringExtra("chonxongtp"));
            //load lại dữ liệu
            onRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragmentangi,container,false);
        //tìm các id cần thiết
        listViewMoiNhatag=(ListView)v.findViewById(R.id.listMoiNhatAnGi);
        listViewMoiNhatag.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewDanhMucag=(ListView)v.findViewById(R.id.listDanhMucAnGi);
        listViewDanhMucag.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        linearLayoutag=(LinearLayout)v.findViewById(R.id.gridviewAnGi);
        gridViewag=(ExpandableHeightGridView)v.findViewById(R.id.gridAnGi);
        gridViewDataAG=(ExpandableHeightGridView)v.findViewById(R.id.gridDataAG);
        scrollViewGrid=(CustomScroll)v.findViewById(R.id.scrollViewaAG);
        frameLayout=(FrameLayout)v.findViewById(R.id.frameprogressag);
        swipeRefresh=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshAG);
        btnDanhMucag=(Button)v.findViewById(R.id.btnDanhMucAnGi);
        btnMoiNhatag=(Button)v.findViewById(R.id.btnMoiNhatAnGi);
        btnThanhPhoag=(Button)v.findViewById(R.id.btnThanhPhoAnGi);
        btnHuyMoiag=(Button)v.findViewById(R.id.btnhuyMNAnGi);
        btnHuyDMag=(Button)v.findViewById(R.id.btnhuylistDMAnGi);
        btnHuyTPag=(Button)v.findViewById(R.id.btnhuylistTPAnGi);
        listTPAG=(ExpandableListView)v.findViewById(R.id.listThanhPhoAnGi);
        listTPAG.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        textViewTenTP=(TextView)v.findViewById(R.id.textViewTenTPAG);
        //địa chỉ của URL
        BASE_URL=new GetIP().getIP(1);
        //show ra loi load dữ liệu
        imgError=(ImageView)v.findViewById(R.id.errroLoad);
        //set su kien khi click doi thanh pho
        btnDoiThanhPho=(Button)v.findViewById(R.id.btnDoiThanhPhoAG);
        btnDoiThanhPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//khi click vao btn đổi thành phố sẽ chuyển qua activity mới kèm các giá trị cần truyền
                Intent intent1=new Intent(getActivity(),ChonThanhPho.class);
                intent1.putExtra("TPDaChon",textViewTenTP.getText().toString().trim());
                intent1.putExtra("idCity",idCity);
                startActivityForResult(intent1,1);//truyền dữ liệu và nhận kq trả về
                //Toast.makeText(getContext(), "clied", Toast.LENGTH_SHORT).show();
            }
        });
        //them nut doi thanh pho vao trong listview thanh pho
        lldoiTPAG=(LinearLayout)v.findViewById(R.id.lineardoithanhphoAG);
        lldoiTPAG.setVisibility(View.GONE);
        //sự kiện kéo lên trên cùng đê load lại trang
        swipeRefresh.setOnRefreshListener(this);
        gridViewag.setExpanded(true);//setExpanded để scroll 2 gridview cùng lúc
        //tạo text cho các nút
        btnMoiNhatag.setText("Mới nhất");
        btnMoiNhatag.setTextColor(Color.parseColor("#ED0210"));
        btnDanhMucag.setText("Danh mục");
        btnThanhPhoag.setText(textViewTenTP.getText().toString().trim());
        //hiện thị dữ liệu lên listview mới nhất
        final CustomGrid customGrid=new CustomGrid(getActivity(),nameGrid,imgGird);
        gridViewag.setAdapter(customGrid);
        //khởi tạo dữ liệu cho list thành phố
        prepareListData();
        //khởi tạo dữ liệu cho list danh mục
        addListDanhMuc();
        LOAD_BY_CITY=true;
        LOAD_BY_NEW=true;
        //load dữ liệu từ sqlite
        dbConnect=new DBConnect(this.getContext());
        //khởi tạo dữ liệu gridview
        addressOfFoods=new ArrayList<Restaurant>();

        loaddata();//load dữ liệu cớ bản lên trước


        gridViewDataAG.setExpanded(true);//setExpand để có thể scroll đc 2 grid view cùng lúc
        //set dữ liệu cho listview thành phố
        listAdapterag=new ExpandableListAdapter(getActivity(), listDataHeaderag, listDataChildag,listSoDuongag);
        listTPAG.setAdapter(listAdapterag);
        //tạo dữ liệu cho listview mới nhất
        listViewMoiNhatag.setAdapter(new CustomAdapter((MainActivity) getActivity(),nameList,imgODau));

        //load du lieu

        listViewMoiNhatag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//sự kiện khi click vào btn mới nhất

                String temp=parent.getItemAtPosition(position).toString();
                btnMoiNhatag.setText(nameList[Integer.parseInt(temp)]);//lấy giá trị của textview gán cho btn mới nhất
                TextView textView=(TextView)view.findViewById(R.id.textView1);
                textView.setTextColor(Color.RED);
                showlistag=true;
                clickbtnMoiNhat();
            }
        });
        listViewDanhMucag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//sự kiện click vào btn danh mục
                TextView txttemp=(TextView)view.findViewById(R.id.textviewofheader);
                TextView txttemp2=(TextView)view.findViewById(R.id.textviewofDM);
                String strtemp="";
                if (position==0){//nếu click vào chữ danh mục thì text của button=danh mục
                    strtemp=txttemp.getText().toString();
                    btnDanhMucag.setText(strtemp);
                    btnDanhMucag.setTextColor(Color.parseColor("#797979"));
                }
                else {//set text btn cho nút danh mục
                    strtemp=txttemp2.getText().toString();
                    btnDanhMucag.setText(strtemp);
                    btnDanhMucag.setTextColor(Color.parseColor("#ED0210"));
                }
                if (position==idCategory){//nếu mà click vào item cũ thì không load lại dữ liệu

                }
                else {//load lại dữ liệu
                    if (position==0){//nếu click vào danh mục thì biến lọc theo danh mục ==false chỉ lọc theo thành phố
                        idCategory=0;
                        LOAD_BY_CATEGORY=false;
                        onRefresh();
                    }
                    else {
                        idCategory=position;
                        LOAD_BY_CATEGORY=true;//lọc dữ liệu theo danh mục
                        onRefresh();
                    }
                }
                btnDanhMucClick();
            }
        });
        btnDanhMucag.setOnClickListener(new View.OnClickListener() {//khi click vào sẽ show ra listview danh mục
            @Override
            public void onClick(View v) {
                btnDanhMucClick();

            }
        });
        btnMoiNhatag.setOnClickListener(new View.OnClickListener() {//khi click show ra list mơi nhất
            @Override
            public void onClick(View v) {
                clickbtnMoiNhat();

            }
        });
        btnThanhPhoag.setOnClickListener(new View.OnClickListener() {//click show ra list thành phố
            @Override
            public void onClick(View v) {
                clickbtnThanhpho();

            }
        });
        btnHuyMoiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMoiNhatag.performClick();
                huyShowlistAll();
            }
        });
        listTPAG.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {//click vào để tìm kiếm theo đường
                TextView textView=(TextView)v.findViewById(R.id.lblListItem);
                TextView ids=(TextView)v.findViewById(R.id.idPhuong) ;
                clickbtnThanhpho();
                btnThanhPhoag.setText(textView.getText().toString());
                LOAD_BY_DISTRICT=false;LOAD_BY_STREET=true;LOAD_BY_CITY=false;//khởi tạo lại các biến lọc
                Toast.makeText(getContext(),ids.getText().toString() , Toast.LENGTH_SHORT).show();
                idStreet=Integer.parseInt(ids.getText().toString());//lấy idStreet để lọc
                onRefresh();//load lại dữ liệu
                return false;
            }
        });
        listTPAG.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {//khi click vào group item để load dữ liệu theo quận
                TextView txtIDStret=(TextView)v.findViewById(R.id.idDistricTP) ;
                TextView lblListHeader = (TextView) v.findViewById(R.id.textviewofTP);
                Toast.makeText(getContext(), "ID district"+String.valueOf(txtIDStret.getText().toString()), Toast.LENGTH_SHORT).show();
                //tạo các n=biến lọc dữ liệu
                LOAD_BY_DISTRICT=true;
                LOAD_BY_CITY=false;
                LOAD_BY_STREET=false;
                btnThanhPhoag.performClick();//hủy việc show list
                btnThanhPhoag.setText(lblListHeader.getText().toString());
                idDistrict=Integer.parseInt(txtIDStret.getText().toString().trim());//lấy idDistrict
                //lọc lại dữ liệu theo idDistrict đã lấy được ở trên
                onRefresh();
                return true;
            }
        });
        btnHuyDMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//nút hủy list
                btnDanhMucag.performClick();
                huyShowlistAll();
            }
        });
        btnHuyTPag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//nút hủy list
                btnThanhPhoag.performClick();
                huyShowlistAll();
            }
        });
        scrollViewGrid.setSmoothScrollingEnabled(true);
        scrollViewGrid.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(CustomScroll scrollView, int x, int y, int oldx, int oldy) {//sự kiện scroll tới cuối cùng thì sẽ load thêm dữ liệu
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
                if (diff == 0){//load dữ liệu khi scroll tới cuối cùng
                    loadmore();
                    //Toast.makeText(getContext(), "load duoc"+String.valueOf(customGridAnGiHolder.getCount()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void setheightLayout(int isClickbtnList){//đặt lại chiều cao cho các linear tránh việc ẩn tabhost chứa các nút menu, user bị nhảy giao diện xuống
        if (isClickbtnList==1){
            MainActivity.llChung.setWeightSum(100f);//chiều cao của toàn bộ màn hình là 100
            LinearLayout.LayoutParams lltab= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lltab.weight=93.0f;//chia màn hình cho tab là 93
            LinearLayout.LayoutParams llpager= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            llpager.weight=7.0f;//chiều cao cho viewpager là 7
            HomeActivity.llmenuhome.setVisibility(View.GONE);//ẩn đi tabhost
            MainActivity.llTab.setLayoutParams(lltab);//set lại chiều cao
            MainActivity.llPager.setLayoutParams(llpager);
            linearLayoutag.setVisibility(View.INVISIBLE);
        }
        else {//khi mà ẩn các listview đi thì sẽ hiện menu trở lại
            MainActivity.llChung.setWeightSum(93f);
            LinearLayout.LayoutParams lltab= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lltab.weight=86.0f;
            LinearLayout.LayoutParams llpager= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            llpager.weight=7.0f;
            MainActivity.llTab.setLayoutParams(lltab);
            MainActivity.llPager.setLayoutParams(llpager);
            HomeActivity.llmenuhome.setVisibility(View.VISIBLE);
            linearLayoutag.setVisibility(View.VISIBLE);
        }
    }
    private void setBackgroundimage(int btn){//tạo màu cho btn tương ứng khi mà listview tương ứng được show lên
        switch (btn){
            case 1:
                btnDanhMucag.setBackgroundResource(R.drawable.btnshowdown);//button màu trắng
                btnThanhPhoag.setBackgroundResource(R.drawable.btnshowdown);
                btnMoiNhatag.setBackgroundResource(R.drawable.btnshowdownclicked);//button màu nâu
                break;
            case 2:
                btnMoiNhatag.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPhoag.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMucag.setBackgroundResource(R.drawable.btnshowdownclicked);
                break;
            case 3:
                btnMoiNhatag.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMucag.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPhoag.setBackgroundResource(R.drawable.btnshowdownclicked);
                break;
            default:
                btnMoiNhatag.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMucag.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPhoag.setBackgroundResource(R.drawable.btnshowdown);
                break;
        }
    }
    private void clickbtnMoiNhat(){//sự kiện click vào nút mới nhất
        if (showlistag==false||sttTab==0||(showlistag=true&&(sttTab==3||sttTab==2))){//nếu chưa có listnao được show ra hoặc có nhưng không phải list mới nhất thì show listmoiw nhat
            lldoiTPAG.setVisibility(View.GONE);//ẩn đi linear chứa nút đổi thành phố
            listViewMoiNhatag.setVisibility(View.VISIBLE);//show ra list mới nhất
            listViewDanhMucag.setVisibility(View.INVISIBLE);
            setheightLayout(1);//set lại chiều cao cho các linear
            btnHuyMoiag.setVisibility(View.VISIBLE);
            listTPAG.setVisibility(View.INVISIBLE);//ẩn đi các listview không cần thiết
            showlistag=true;
            setBackgroundimage(1);
            sttTab=1;
        }
        else{//ẩn đi listview này nếu listview này đã xuất hiện rồi
            lldoiTPAG.setVisibility(View.GONE);
            btnHuyMoiag.setVisibility(View.INVISIBLE);
            listViewMoiNhatag.setVisibility(View.INVISIBLE);
            listViewDanhMucag.setVisibility(View.INVISIBLE);
            listTPAG.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlistag=false;
            sttTab=0;
        }
    }
    private void btnDanhMucClick(){//sự kiện click vào btn danh mục tương tự như click vào btn mới nhất ở trên
        if (showlistag==false||sttTab==0||(showlistag=true&&(sttTab==1||sttTab==3))){
            lldoiTPAG.setVisibility(View.GONE);
            listViewMoiNhatag.setVisibility(View.INVISIBLE);
            listViewDanhMucag.setVisibility(View.VISIBLE);
            listTPAG.setVisibility(View.INVISIBLE);
            btnHuyDMag.setVisibility(View.VISIBLE);
            setheightLayout(1);
            setBackgroundimage(2);
            showlistag=true;sttTab=2;
        }
        else {
            lldoiTPAG.setVisibility(View.GONE);
            listViewMoiNhatag.setVisibility(View.INVISIBLE);
            listViewDanhMucag.setVisibility(View.INVISIBLE);
            btnHuyDMag.setVisibility(View.INVISIBLE);
            listTPAG.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlistag=false;
            sttTab=0;
        }
    }
    private void clickbtnThanhpho(){//sự kiện click vào btn thành phó thì sẽ show ra list thành phố gồm quận, đường
        if (showlistag==false||sttTab==0||(showlistag=true&&(sttTab==1||sttTab==2))){
            lldoiTPAG.setVisibility(View.VISIBLE);
            listViewMoiNhatag.setVisibility(View.INVISIBLE);
            listTPAG.setVisibility(View.VISIBLE);
            listViewDanhMucag.setVisibility(View.INVISIBLE);
            setheightLayout(1);
            btnHuyTPag.setVisibility(View.VISIBLE);
            showlistag=true;
            setBackgroundimage(3);
            sttTab=3;
        }
        else{
            lldoiTPAG.setVisibility(View.GONE);
            btnHuyTPag.setVisibility(View.INVISIBLE);
            listViewMoiNhatag.setVisibility(View.INVISIBLE);
            listTPAG.setVisibility(View.INVISIBLE);
            listViewDanhMucag.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlistag=false;
            sttTab=0;
        }
    }
    private void addListDanhMuc(){//tạo dữ liệu cho listview danh mục
        TypeListViewImage vietnam=new TypeListViewImage("Việt Nam",R.drawable.danhmuc2);
        TypeListViewImage chaumay=new TypeListViewImage("Châu Mỹ",R.drawable.trunghoa);
        TypeListViewImage my=new TypeListViewImage("Mỹ",R.drawable.danhmuc3);
        TypeListViewImage tayau=new TypeListViewImage("Tây Âu",R.drawable.danhmuc4);
        TypeListViewImage y=new TypeListViewImage("Ý",R.drawable.danhmuc5);
        TypeListViewImage phap=new TypeListViewImage("Pháp",R.drawable.danhmuc6);
        TypeListViewImage duc=new TypeListViewImage("Đức",R.drawable.danhmuc9);
        TypeListViewImage taybannha=new TypeListViewImage("Tây Ban Nha",R.drawable.danhmuc8);
        TypeListViewImage trunghoa=new TypeListViewImage("Trung Hoa",R.drawable.danhmuc10);
        TypeListViewImage ando=new TypeListViewImage("Ấn Độ",R.drawable.danhmuc11);
        TypeListViewImage thailan=new TypeListViewImage("Thái Lan",R.drawable.danhmuc12);
        TypeListViewImage nhatban=new TypeListViewImage("Nhật Bản",R.drawable.danhmuc13);
        TypeListViewImage hanquoc=new TypeListViewImage("Hàn Quốc",R.drawable.danhmuc14);
        TypeListViewImage malaysia=new TypeListViewImage("Malaysia",R.drawable.danhmuc15);
        TypeListViewImage quocte=new TypeListViewImage("Quốc tế",R.drawable.danhmuc16);
        TypeListViewImage sangtrong=new TypeListViewImage("Sang trọng",R.drawable.danhmuc17);
        TypeListViewImage buffet=new TypeListViewImage("Buffet",R.drawable.danhmuc18);
        TypeListViewImage nhanhang=new TypeListViewImage("Nhà Hàng",R.drawable.danhmuc19);
        TypeListViewImage anvatviahe=new TypeListViewImage("Ăn vặt/vỉa hè",R.drawable.danhmuc20);
        TypeListViewImage anchay=new TypeListViewImage("Ăn chay",R.drawable.danhmuc21);
        TypeListViewImage cafe=new TypeListViewImage("Café/Dessert",R.drawable.danhmuc22);
        TypeListViewImage quanan=new TypeListViewImage("Quán ăn",R.drawable.danhmuc23);
        TypeListViewImage bar=new TypeListViewImage("Bar/Pub",R.drawable.danhmuc24);
        TypeListViewImage quannhau=new TypeListViewImage("Quán nhậu",R.drawable.danhmuc25);
        TypeListViewImage bearclub=new TypeListViewImage("Bear club",R.drawable.danhmuc26);
        TypeListViewImage tiembanh=new TypeListViewImage("Tiệm bánh",R.drawable.danhmuc27);
        TypeListViewImage tiectannoi=new TypeListViewImage("Tiệc tận nơi",R.drawable.danhmuc28);
        TypeListViewImage shoponline=new TypeListViewImage("Shop Online",R.drawable.danhmuc29);
        TypeListViewImage giaocomvanphong=new TypeListViewImage("Giao cơm văn phòng",R.drawable.miennam);
        TypeListViewImage khuamthuc=new TypeListViewImage("Khu ẩm thực",R.drawable.sangtrong);
        ArrayList<Object> typeList=new ArrayList<>();
        typeList.add("Danh mục");
        typeList.add(vietnam);
        typeList.add(chaumay);
        typeList.add(my);
        typeList.add(tayau);
        typeList.add(y);
        typeList.add(phap);
        typeList.add(duc);
        typeList.add(taybannha);
        typeList.add(trunghoa);
        typeList.add(ando);
        typeList.add(thailan);
        typeList.add(nhatban);
        typeList.add(hanquoc);
        typeList.add(malaysia);
        typeList.add(quocte);
        typeList.add(sangtrong);
        typeList.add(buffet);
        typeList.add(nhanhang);
        typeList.add(anvatviahe);
        typeList.add(anchay);
        typeList.add(cafe);
        typeList.add(quanan);
        typeList.add(bearclub);
        typeList.add(quannhau);
        typeList.add(bar);
        typeList.add(tiembanh);
        typeList.add(tiectannoi);
        typeList.add(shoponline);
        typeList.add(giaocomvanphong);
        typeList.add(khuamthuc);
        listViewDanhMucag.setAdapter(new Custom_ListViewDM(getActivity(),typeList));//đổ dữ liệu vào listview danh mục
    }
    private void huyShowlistAll(){//hủy tất các các listview ở cả fragment ở đâu và ăn gì
        switch (FragmentODau.sttTab){
            case 1:
                FragmentODau.btnHuyMoi.performClick();

                break;
            case 2:
                FragmentODau.btnHuyDM.performClick();
                break;
            case 3:
                FragmentODau.btnHuyTP.performClick();
                break;
        }
    }
    public void prepareListData() {//tạo dữ liệu cho listview thành phố
        listDataHeaderag = new ArrayList<District>();//chứa dữ liệu các quận
        listDataChildag = new HashMap<District, List<Street>>();//chứa dữ liệu các đường trong quận
        listSoDuongag=new ArrayList<Integer>();//số đường trong 1 quận
        // Adding child data
        dbConnect=new DBConnect(this.getContext());//khai báo cssdl
        int tongsoquan=dbConnect.TongSoQuan(idCity);//lấy ra tổng số quận trong 1 thành phố
        List<District> districtList=dbConnect.getListDistrist(idCity);
        for (int i=0;i<tongsoquan;i++){
            District district=new District();
            district.setName(districtList.get(i).getName());
            district.setId(districtList.get(i).getId());
            district.setIdTP(districtList.get(i).getIdTP());
            listDataHeaderag.add(district);//add các quận vào trong list header để hiện thị danh sách các quận
            List<Street>streets=dbConnect.getListStreets(districtList.get(i).getId());//lấy danh sách các đường trong 1 quận
            int tongsoduong=dbConnect.getCountStreet(districtList.get(i).getId());
            for (int j=0;j<tongsoduong;j++){//vòng lặp lấy ra tất các các đường trong 1 quận và luu vào 1 list
                Street street=new Street();
                street.setIdDistrict(streets.get(j).getIdDistrict());
                street.setIdStreet(streets.get(j).getIdStreet());
                street.setNameStreet(streets.get(j).getNameStreet());
            }
            listSoDuongag.add(streets.size());//số đường trong 1 quận chình là kích thước của list child
            listDataChildag.put(district,streets);
        }
    }
    private String getURL(){//trả về url để lọc dữ liệu
        getURL getU=new getURL();

        if (LOAD_BY_NEW==true){
            if (LOAD_BY_CITY==true) {
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,idCity,-1,-1,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(prePos,idCity,-1,-1,-1,true);
                }
            }
            else if (LOAD_BY_STREET==true){
                if(LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,-1,-1,idStreet,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(prePos,-1,-1,idStreet,-1,true);
                }
            }
            else if (LOAD_BY_DISTRICT==true){
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,-1,idDistrict,-1,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(prePos,-1,idDistrict,-1,-1,true);
                }
            }
        }
        else {
            if (LOAD_BY_CITY==true) {
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,idCity,-1,-1,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(prePos,idCity,-1,-1,-1,false);
                }
            }
            else if (LOAD_BY_STREET==true){
                if(LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,-1,-1,idStreet,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(prePos,-1,-1,idStreet,-1,false);
                }
            }
            else if (LOAD_BY_DISTRICT==true){
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(prePos,-1,idDistrict,-1,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(prePos,-1,idDistrict,-1,-1,false);
                }
            }
        }
        return URL;
    }
    private void loadmore(){//hàm này sẽ được gọi lại khi mà có sự kiện scroll tới cuối cùng của scroll bar
        getURL();
        firstLoad=false;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONAG().execute(BASE_URL.trim() + URL.trim());
            }
        });
    }

    @Override
    public void onRefresh() {//load lại dữ liệu
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollViewGrid.fullScroll(View.FOCUS_UP);//đẩy scroll lên trên cùng
                swipeRefresh.setRefreshing(false);
                prePos=0;
                loaddata();
            }
        },200);
    }
    private void loaddata(){
        addressOfFoods.clear();//xóa tất cả các dữ liệu trước đó
        //load du lieu co ban len truoc mặc định sẽ load dữ liệu mới nhất
        getURL();
        firstLoad=true;
        Log.d("url",URL);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONAG().execute(BASE_URL.trim()+URL.trim());
            }
        });
        Log.d("Loadmore URL",BASE_URL+URL);
    }
    class ReadJSONAG extends AsyncTask<String, Integer,List<Restaurant>> {//sự dụng asyntask để gửi và nhận dữ liệu

        @Override
        protected List<Restaurant> doInBackground(String... params) {//gửi các tham số vào URL và truyền đi trả về list quán ăn
            String chuoi = getXmlFromUrl(params[0]);//lấy toàn bộ giá trị trong response
            List<Restaurant> restaurants = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(chuoi);//convert sang JSONArray
                Log.d("Prepost", String.valueOf(prePos));
                prePos += 10;//load thêm 10 quán ăn nếu còn
                Log.d("Curpost", String.valueOf(prePos));
                if (chuoi.equals("[]")) {
                    loadmore = false;
                } else {
                    loadmore = true;
                    //lấy toàn bộ giá trị trong mảng JSONArray
                    for (int i = 0; i < jsonArray.length(); i++) {
                        restaurants.add(new Restaurant(jsonArray.getJSONObject(i)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return restaurants;//trả về list quán ăn
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {//sư dụng list quán ăn vừa trả về để hiện thị dữ liệu
            super.onPostExecute(restaurants);
            frameLayout.setVisibility(View.INVISIBLE);
            if (restaurants.size() == 0) {//nếu rỗng thì hiện thị lỗi hết dữ liệu và tắt hiển thị gridview
                if (firstLoad==true){
                    gridViewDataAG.setVisibility(View.GONE);
                    imgError.setVisibility(View.VISIBLE);
                }
            } else {//ngược lại
                imgError.setVisibility(View.INVISIBLE);
                gridViewDataAG.setVisibility(View.VISIBLE);
                if (firstLoad == true) {//nếu là lần đầu load thì thêm mới vào adapter
                    customGridAnGiHolder = new CustomGridAnGiHolder(getContext(),restaurants);
                    gridViewDataAG.setAdapter(customGridAnGiHolder);
                } else {//thêm vào adapter và lưu thay đổi
                    if (loadmore == true) {
                        customGridAnGiHolder.addListItemToAdapter(restaurants);
                    } else {
                        Log.d("Stop", "Het du lieu");
                    }
                }
            }

        }
        @Override
        protected void onPreExecute() {//hiện thị progress bar
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);
        }
    }
    private  String getXmlFromUrl(String urlString){
        String xml=null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPost = new HttpGet(urlString);
            //the following line causes the problem
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }
}
