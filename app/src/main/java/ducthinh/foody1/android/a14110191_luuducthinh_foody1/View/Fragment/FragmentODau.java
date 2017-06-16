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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.protocol.HTTP;
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

import static java.net.Proxy.Type.HTTP;

/**
 * Created by Test on 4/10/2017.
 */

public class FragmentODau extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //Khai bao cac mang de tao listview
    ImageView imgError;//image nayf có chức năng thông báo khi mà không load được dữ liệu
    CustomScroll scrollViewGrid;//custom scroll để croll 2 gridview
    private SwipeRefreshLayout swipeRefresh;//load lại dữ liệu khi kéo lên đầu trang
    private List<Restaurant> addressOfFoods;//mảng chứa data
    CustomListODauWithHolder mAdapter;//adapter chứa dữ liệu
    private int currPos = 0;//giá trị khởi tạo khi mới đầu load dữ liệu sẽ load được từ 0-10 dữ liệu ban đầu
    private DBConnect dbConnect;
    FrameLayout frameLayout;//show màn hình chờ khi đang load dữ liệu
    ListView listViewMoiNhat,listViewDanhMuc;
    LinearLayout linearLayoutODau,linealayoutDoiTP,llEmpty;
    ExpandableHeightGridView gridViewODau2,gridViewODau;
    public static int sttTab=0;
    ExpandableListAdapter listAdapter;
    ExpandableListView listTPODau;
    List<District> listDataHeader;
    List<Integer> listSoDuong;
    HashMap<District, List<Street>> listDataChild;
    TextView textViewTenTP;
    Button btnMoiNhat,btnThanhPho, btnDanhMuc,btnDoiThanhPho;
    public static Button btnHuyMoi,btnHuyDM,btnHuyTP;
    //biến kiểm tra việc có listview nào hiện ra hay chưa
    public static Boolean showlist=false;
    private String BASE_URL="";
    private String URL="";

    //Biến kiểm tra dữ liệu đc load lần đầu hay không
    private boolean firstLoad=true,loadmore=true;
    //các biến lọc dữ liệu
    private boolean LOAD_BY_CITY=false;
    private boolean LOAD_BY_STREET=false;
    private boolean LOAD_BY_DISTRICT=false;
    private boolean LOAD_BY_CATEGORY=false;
    private boolean LOAD_BY_NEW=false;
    private int idCity=1,idDistrict=1,idCategory=0,idStreet=-1;//các biến lọc theo id
    //các mảng chứa các giá trị cần đổ vào listview mới nhất và listview danh mục
    private static int[] imgGird={R.drawable.gridgantoi, R.drawable.gridcoupon,R.drawable.griddatchouudai,R.drawable.griddatgiaohang
            ,R.drawable.gridecard,R.drawable.gridgame,R.drawable.gridbinhluan,R.drawable.gridblog,R.drawable.gridtopthanhvien,R.drawable.gridvideo};
    private static String[] nameGrid={"Gần tôi","Coupon", "Đặt chỗ ưu đãi", "Đặt giao hàng","E-card", "Game & Fun","Bình luận",
            "Blogs", "Top thành viên", "Video"};
    private static int[] imgODau={R.drawable.moinhat,R.drawable.ganday,R.drawable.phobien
            ,R.drawable.dukhach,R.drawable.uudaiecard,R.drawable.datcho,R.drawable.uudaithe,R.drawable.datgiaohang};
    private static int[] imgODauSelected={R.drawable.actmoinhat,R.drawable.actganday,R.drawable.actphobien
            ,R.drawable.actdukhach,R.drawable.actuudaiecard,R.drawable.actdatcho,R.drawable.actuudaithe,R.drawable.actdatgiaohang,};

    private static String[] nameList={"Mới nhất","Gần tôi", "Phổ biến", "Du khách", "Ưu đãi E-card", "Đặt chỗ", "Ưu đãi thẻ", "Đặt giao hàng"};
    //khi mà nhận giá trị trả về từ việc đổi thành phố
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data!=null){//nếu kết quả trả về khác null
            //nhận các kết quả trả về và add vào các biến lọc
            textViewTenTP.setText(data.getStringExtra("chonxongtp"));
            idCity=Integer.parseInt(data.getStringExtra("idcityselected"));
            prepareListData();
            listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,listSoDuong);
            listTPODau.setAdapter(listAdapter);
            btnThanhPho.performClick();
            //đổi kiểu lọc. lọc theo thành phố
            LOAD_BY_CITY=true;
            LOAD_BY_STREET=false;
            LOAD_BY_DISTRICT=false;
            btnThanhPho.setText(data.getStringExtra("chonxongtp"));//set text cho button thành phố là tên thành phố đã đổi
            //load lại dữ liệu
            onRefresh();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v=inflater.inflate(R.layout.fragmentodau,container,false);

        //khai bao cac id cho listview,button, linearlayout ....
        listViewMoiNhat=(ListView)v.findViewById(R.id.listMoiNhatODau);
        listViewMoiNhat.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewDanhMuc=(ListView)v.findViewById(R.id.listDanhMucODAu);
        listViewDanhMuc.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        linearLayoutODau=(LinearLayout)v.findViewById(R.id.gridviewODau);
        linealayoutDoiTP=(LinearLayout)v.findViewById(R.id.lineardoithanhpho);
        linealayoutDoiTP.setVisibility(View.INVISIBLE);
        textViewTenTP=(TextView)v.findViewById(R.id.textViewTenTP);
        btnDoiThanhPho=(Button)v.findViewById(R.id.btnDoiThanhPho);
        frameLayout=(FrameLayout)v.findViewById(R.id.frameprogressODAU) ;
        swipeRefresh=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshODau);
        gridViewODau2=(ExpandableHeightGridView) v.findViewById(R.id.gridDataODau);
        gridViewODau=(ExpandableHeightGridView)v.findViewById(R.id.gridODau) ;
        gridViewODau2.setExpanded(true);
        gridViewODau.setExpanded(true);
        imgError=(ImageView)v.findViewById(R.id.errroLoadODau) ;
        scrollViewGrid=(CustomScroll)v.findViewById(R.id.scrollViewaODau) ;
        btnDanhMuc=(Button)v.findViewById(R.id.btnDanhMucODau);
        btnMoiNhat=(Button)v.findViewById(R.id.btnMoiNhatODau);
        btnThanhPho=(Button)v.findViewById(R.id.btnThanhPhoODau);
        btnHuyMoi=(Button)v.findViewById(R.id.btnhuylistmoinhat);
        btnHuyDM=(Button)v.findViewById(R.id.btnhuylistDanhmuc);
        btnHuyTP=(Button)v.findViewById(R.id.btnhuylistthanhpho);
        //địa chỉ URL mặc định
        BASE_URL=new GetIP().getIP(1);
        //set từ cho 2 btn mới nhất và btn danh mục
        btnMoiNhat.setText("Mới nhất");
        btnMoiNhat.setTextColor(Color.parseColor("#ED0210"));
        btnDanhMuc.setText("Danh mục");
        // tạo 1 adpater để chứa dữ liệu của gridview trong mục hiển thị đầu tiên
        final CustomGrid customGrid=new CustomGrid(getActivity(),nameGrid,imgGird);
        gridViewODau.setAdapter(customGrid);//đổ dữ liệu vào gridview


        //sử dụng expanded list view để tạo listview thành phố
        listTPODau =(ExpandableListView)v.findViewById(R.id.listThanhPhoODau);
        listTPODau.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
        //đổ dữ liệu lên listview thành phố
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareListData();
            }
        }).run();
        //khởi tạo dữ liệu cho listview thành phố
        //tạo dữ liệu cho listview mới nhất
        new Thread(new Runnable() {
            @Override
            public void run() {
                addListDanhMuc();
            }
        }).run();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,listSoDuong);
        listTPODau.setAdapter(listAdapter);//đổ dữ liệu vào listview thành phố


        //đổ dữ liệu lên list mới nhất
        listViewMoiNhat.setAdapter(new CustomAdapter((MainActivity) getActivity(),nameList,imgODau));

        //tạo dữ liệu cho listview chứa data
        dbConnect=new DBConnect(this.getContext());
        addressOfFoods=new ArrayList<Restaurant>();
        //khi khởi tạo activity sẽ load dữ liệu của thành phố lên trước(Hà Nội) lên trước
        LOAD_BY_CITY=true;
        LOAD_BY_NEW=true;
        laydulieubandau();
        //btn thành phố sẽ được lấy từ dữ liệu được đẩy lên trong textview khi onstart hoặc refresh
        textViewTenTP.setText(dbConnect.getNameCity(idCity));
        btnThanhPho.setText(textViewTenTP.getText().toString().trim());
        swipeRefresh.setOnRefreshListener(this);//sự kiện kéo refresh lại dữ liệu
        //set su kien cho btndoi thanh pho
        btnDoiThanhPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(),ChonThanhPho.class);
                intent1.putExtra("TPDaChon",textViewTenTP.getText().toString().trim());//truyền biến chứa giá trị cần truyền đi
                startActivityForResult(intent1,1);//truyền dữ liệu và nhận kq trả về
                Toast.makeText(getContext(), "clied", Toast.LENGTH_SHORT).show();
            }
        });
        //sự kiện click 1 item trong listview mới nhất
        listViewMoiNhat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//sự kiện khi click vào 1 item trong list mới nhất

                String temp=parent.getItemAtPosition(position).toString();
                btnMoiNhat.setText(nameList[Integer.parseInt(temp)]);//set text cho btn là text trong item
                TextView textView=(TextView)view.findViewById(R.id.textView1);
                textView.setTextColor(Color.RED);
                showlist=true;
                clickbtnMoiNhat();//hàm xử lí sự kiện click vào btn mới nhất
            }
        });
        //sự kiện click 1 item trong listview danh mục
        listViewDanhMuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//sự kiện click item trong list danh mục
                TextView txttemp=(TextView)view.findViewById(R.id.textviewofheader);
                TextView txttemp2=(TextView)view.findViewById(R.id.textviewofDM);
                String strtemp="";
                if (position==0){
                    strtemp=txttemp.getText().toString();
                    btnDanhMuc.setTextColor(Color.parseColor("#797979"));
                    btnDanhMuc.setText(strtemp);
                }
                else {
                    strtemp=txttemp2.getText().toString();
                    btnDanhMuc.setText(strtemp);
                    btnDanhMuc.setTextColor(Color.parseColor("#ED0210"));
                }
                //khi click vào position mà đã chọn trước đó thì không lọc lại dữ liệu
                if (position==idCategory){

                }
                else {//lọc lại dữ liệu
                    if (position==0){//nếu chọn danh mục thì sẽ load toàn bộ dữ liệu lên
                        idCategory=0;
                        LOAD_BY_CATEGORY=false;
                        onRefresh();
                    }
                    else {//load theo biến lọc danh mục
                        idCategory=position;
                        LOAD_BY_CATEGORY=true;
                        onRefresh();
                    }
                }
                btnDanhMucClick();
            }
        });
        btnDanhMuc.setOnClickListener(new View.OnClickListener() {//sự kiện click vào nút để show ra list danh mục
            @Override
            public void onClick(View v) {
                btnDanhMucClick();
            }
        });
        btnMoiNhat.setOnClickListener(new View.OnClickListener() {//sự kiện click vào nút để show ra list mới nhất
            @Override
            public void onClick(View v) {
                clickbtnMoiNhat();
            }
        });
        btnThanhPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickbtnThanhpho();
            }
        });
        //sự kiện click vào địa chỉ của thành phố sẽ load lại dữ liệu
        listTPODau.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {//load dữ liệu theo phường

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                TextView textView=(TextView)v.findViewById(R.id.lblListItem);
                clickbtnThanhpho();
                btnThanhPho.setText(textView.getText().toString());
                LOAD_BY_DISTRICT=false;LOAD_BY_STREET=true;LOAD_BY_CITY=false;//tạo lại các biến nhớ để load dữ liệu theo đúng yêu cầu
                TextView ids=(TextView)v.findViewById(R.id.idPhuong) ;
                idStreet=Integer.parseInt(ids.getText().toString());
                onRefresh();//load lại dữ liệu
                return false;
            }
        });
        listTPODau.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {//sự kiện khi click lọc dữ liệu theo quận
                TextView txtIDStret=(TextView)v.findViewById(R.id.idDistricTP) ;//get text cho key search
                TextView lblListHeader = (TextView) v.findViewById(R.id.textviewofTP);
                Toast.makeText(getContext(), "ID district"+String.valueOf(txtIDStret.getText().toString()), Toast.LENGTH_SHORT).show();
                LOAD_BY_DISTRICT=true;//khởi tạo lại biến lọc
                LOAD_BY_CITY=false;
                LOAD_BY_STREET=false;
                btnThanhPho.performClick();//đóng listview
                btnThanhPho.setText(lblListHeader.getText().toString());
                idDistrict=Integer.parseInt(txtIDStret.getText().toString().trim());
                onRefresh();//lọc lại dữ liệu
                return true;
            }
        });
        scrollViewGrid.setScrollViewListener(new ScrollViewListener() {
            @Override
            public void onScrollChanged(CustomScroll scrollView, int x, int y, int oldx, int oldy) {
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                //sự kiện khi mà scroll tới cuối danh sách thì load thêm dữ liệu
                if (diff == 0) {//giới hạn số lượng load được..
                    loadmore();
                    //Toast.makeText(getContext(), "load duoc"+String.valueOf(mAdapter.getCount()), Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuyMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện hủy form list mới nhất khi click vào nút hủy trong listmoi nhat
                btnMoiNhat.performClick();
                huyShowlistAll();//hủy tất các các list đã show ở cả fragment ở đâu và ăn gì tránh trường hợp kéo qua fragment khác bị lỗi giao diện
            }
        });
        btnHuyDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện hủy form danh mục
                btnDanhMuc.performClick();
                huyShowlistAll();
            }
        });
        btnHuyTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện hủy list thành phố
                btnThanhPho.performClick();
                huyShowlistAll();
            }
        });
        return v;
    }
    //hàm này có tác dụng ẩn tabwwidget chứa các nút home, library...
    private void setheightLayout(int isClickbtnList){
        if (isClickbtnList==1){
            MainActivity.llChung.setWeightSum(100f);//tông chiều cao cho cái layout là 100
            LinearLayout.LayoutParams lltab= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lltab.weight=93.0f;//layout chứa tab là 93
            LinearLayout.LayoutParams llpager= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            llpager.weight=7.0f;//layout chứa viewpager là 7
            HomeActivity.llmenuhome.setVisibility(View.GONE);
            MainActivity.llTab.setLayoutParams(lltab);
            MainActivity.llPager.setLayoutParams(llpager);
            linearLayoutODau.setVisibility(View.INVISIBLE);
        }
        else {
            MainActivity.llChung.setWeightSum(93f);//đặt lại chiều cao chung
            LinearLayout.LayoutParams lltab= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lltab.weight=86.0f;
            LinearLayout.LayoutParams llpager= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            llpager.weight=7.0f;
            MainActivity.llTab.setLayoutParams(lltab);//set lại chiều cao cho các tab tránh việc linear chứa (ăn gì, ở đâu) bị lỗi giao diện
            MainActivity.llPager.setLayoutParams(llpager);
            //hiện lên các nút trong tabwidget, show menu
            HomeActivity.llmenuhome.setVisibility(View.VISIBLE);
            linearLayoutODau.setVisibility(View.VISIBLE);
        }
    }
    private void clickbtnMoiNhat(){//sự kiện click vào btn để show list mới nhất
        if (showlist==false||sttTab==0||(showlist=true&&(sttTab==3||sttTab==2))){
            //ẩn các tab không cần thiết
            linealayoutDoiTP.setVisibility(View.INVISIBLE);
            listViewMoiNhat.setVisibility(View.VISIBLE);//hiện listview mới nhất
            listViewDanhMuc.setVisibility(View.INVISIBLE);
            setheightLayout(1);//đặt lại chiều cao
            btnHuyMoi.setVisibility(View.VISIBLE);//hiện lên nút hủy listview
            listTPODau.setVisibility(View.INVISIBLE);
            showlist=true;//đặt lại biến nhớ
            setBackgroundimage(1);//đặt lại màu cho btn theo listview được show ra
            sttTab=1;
        }
        else{
            linealayoutDoiTP.setVisibility(View.INVISIBLE);//ẩn đi linear chưa layout đổi thành phố
            btnHuyMoi.setVisibility(View.INVISIBLE);//ẩn đi nút hủy listview
            //ẩn đi các layout không cần thiết
            listViewMoiNhat.setVisibility(View.INVISIBLE);
            listViewDanhMuc.setVisibility(View.INVISIBLE);
            listTPODau.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlist=false;
            sttTab=0;
        }
    }
    private void btnDanhMucClick(){//sự kiện show list danh mục
        if (showlist==false||sttTab==0||(showlist=true&&(sttTab==1||sttTab==3))){//show list nếu chưa có list nào được show hoăc có nhứng không phải list danh muc;
            listViewMoiNhat.setVisibility(View.INVISIBLE);
            listViewDanhMuc.setVisibility(View.VISIBLE);//show ra list danh mục và ẩn đi các linear layout không cần thiét

            listTPODau.setVisibility(View.INVISIBLE);
            btnHuyDM.setVisibility(View.VISIBLE);
            setheightLayout(1);//đặt lại chiều cao
            linealayoutDoiTP.setVisibility(View.INVISIBLE);
            setBackgroundimage(2);
            showlist=true;sttTab=2;
        }
        else {
            //ẩn tất cả các listview
            linealayoutDoiTP.setVisibility(View.INVISIBLE);
            listViewMoiNhat.setVisibility(View.INVISIBLE);
            listViewDanhMuc.setVisibility(View.INVISIBLE);
            btnHuyDM.setVisibility(View.INVISIBLE);
            listTPODau.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlist=false;
            sttTab=0;
        }
    }
    private void clickbtnThanhpho(){//sự kiện show list thành phố
        if (showlist==false||sttTab==0||(showlist=true&&(sttTab==1||sttTab==2))){
            listViewMoiNhat.setVisibility(View.INVISIBLE);
            listTPODau.setVisibility(View.VISIBLE);
            listViewDanhMuc.setVisibility(View.INVISIBLE);
            setheightLayout(1);
            btnHuyTP.setVisibility(View.VISIBLE);
            linealayoutDoiTP.setVisibility(View.VISIBLE);
            showlist=true;
            setBackgroundimage(3);
            sttTab=3;
        }
        else{
            linealayoutDoiTP.setVisibility(View.INVISIBLE);
            btnHuyTP.setVisibility(View.INVISIBLE);
            listViewMoiNhat.setVisibility(View.INVISIBLE);
            listTPODau.setVisibility(View.INVISIBLE);
            listViewDanhMuc.setVisibility(View.INVISIBLE);
            setheightLayout(0);
            setBackgroundimage(0);
            showlist=false;
            sttTab=0;
        }
    }
    private void addListDanhMuc(){//tạo listview danh mục với header
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
        listViewDanhMuc.setAdapter(new Custom_ListViewDM(getActivity(),typeList));//đổ dữ liệu vào listdanh mục và show ra
    }
    public void prepareListData() {//đổ dữ liệu lên list thành phố
        listDataHeader = new ArrayList<District>();//list chưa dữ liệu của các quận trong thành phố
        listDataChild = new HashMap<District, List<Street>>();//chứa dữ liệu các đường trong quận
        listSoDuong=new ArrayList<Integer>();//số đường trong 1 quận
        // Adding child data
        dbConnect=new DBConnect(this.getContext());
        int tongsoquan=dbConnect.TongSoQuan(idCity);
        List<District> districtList=dbConnect.getListDistrist(idCity);//lấy các đường trong 1 thành phố đổ vào list header
        for (int i=0;i<tongsoquan;i++){
            District district=new District();
            district.setName(districtList.get(i).getName());
            district.setId(districtList.get(i).getId());
            district.setIdTP(districtList.get(i).getIdTP());
            listDataHeader.add(district);//add từng quận vào trong listheader
            List<Street>streets=dbConnect.getListStreets(districtList.get(i).getId());//lấy danh sách các đường trong 1 quận
            int tongsoduong=dbConnect.getCountStreet(districtList.get(i).getId());
            for (int j=0;j<tongsoduong;j++){//đổ tất cả các đường vào trong 1 listchild
                Street street=new Street();
                street.setIdDistrict(streets.get(j).getIdDistrict());
                street.setIdStreet(streets.get(j).getIdStreet());
                street.setNameStreet(streets.get(j).getNameStreet());
            }
            listSoDuong.add(streets.size());
            listDataChild.put(district,streets);//show ra listview
        }
    }
    private void setBackgroundimage(int btn){//đổi màu cho nút khi show ra list tương ứng
        switch (btn){
            case 1:
                btnDanhMuc.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPho.setBackgroundResource(R.drawable.btnshowdown);
                btnMoiNhat.setBackgroundResource(R.drawable.btnshowdownclicked);
                break;
            case 2:
                btnMoiNhat.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPho.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMuc.setBackgroundResource(R.drawable.btnshowdownclicked);
                break;
            case 3:
                btnMoiNhat.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMuc.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPho.setBackgroundResource(R.drawable.btnshowdownclicked);
                break;
            default:
                btnMoiNhat.setBackgroundResource(R.drawable.btnshowdown);
                btnDanhMuc.setBackgroundResource(R.drawable.btnshowdown);
                btnThanhPho.setBackgroundResource(R.drawable.btnshowdown);
                break;
        }
    }
    private void huyShowlistAll(){//hủy tất cả các listview được show ra ở cả fragment ăn gì và ở đâu
        switch (FragmentAnGi.sttTab){
            case 1:
                FragmentAnGi.btnHuyMoiag.performClick();

                break;
            case 2:
                FragmentAnGi.btnHuyDMag.performClick();
                break;
            case 3:
                FragmentAnGi.btnHuyTPag.performClick();
                break;
        }
    }
    //khởi tạo dữ liệu ban đầu
    public void laydulieubandau(){
        loaddata();
    }
    @Override
    public void onRefresh() {//sự kiện refresh lại trang khi scroll lên trên cùng
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollViewGrid.fullScroll(View.FOCUS_UP);
                swipeRefresh.setRefreshing(false);
                currPos=0;
                loaddata();
            }
        },200);
    }

    private void loadmore(){//xư lí khi mà scroll tới cuối cùng của scrollbar sẽ load thêm dữ liệu mới
        //tiếp tục lấy dữ liệu đoạn tiếp theo vd: limit 10, 10 nghĩa là lấy được 10 quán ăn từ vị trí thứ 10 tới 20
        getURL();
        firstLoad=false;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(BASE_URL.trim() + URL.trim());
            }
        });
        Log.d("Loadmore URL",BASE_URL+URL);
    }
    private void loaddata(){//load mới dữ liệu cho listview data


        addressOfFoods.clear();//xóa tất cả các dữ liệu trước đó
        //load du lieu co ban len truoc mặc định sẽ load dữ liệu mới nhất
        getURL();
        firstLoad=true;
        Log.d("url",URL);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(BASE_URL.trim()+URL.trim());
            }
        });
        Log.d("Loadmore URL",BASE_URL+URL);
    }
    private String getURL(){//hàm trả về URL để lọc dữ liệu
        getURL getU=new getURL();
        if (LOAD_BY_NEW==true){
            if (LOAD_BY_CITY==true) {
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,idCity,-1,-1,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(currPos,idCity,-1,-1,-1,true);
                }
            }
            else if (LOAD_BY_STREET==true){
                if(LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,-1,-1,idStreet,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(currPos,-1,-1,idStreet,-1,true);
                }
            }
            else if (LOAD_BY_DISTRICT==true){
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,-1,idDistrict,-1,idCategory,true);
                }
                else {
                    URL=getU.getURLofRes(currPos,-1,idDistrict,-1,-1,true);
                }
            }
        }
        else {
            if (LOAD_BY_CITY==true) {
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,idCity,-1,-1,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(currPos,idCity,-1,-1,-1,false);
                }
            }
            else if (LOAD_BY_STREET==true){
                if(LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,-1,-1,idStreet,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(currPos,-1,-1,idStreet,-1,false);
                }
            }
            else if (LOAD_BY_DISTRICT==true){
                if (LOAD_BY_CATEGORY==true){
                    URL=getU.getURLofRes(currPos,-1,idDistrict,-1,idCategory,false);
                }
                else {
                    URL=getU.getURLofRes(currPos,-1,idDistrict,-1,-1,false);
                }
            }
        }
        return URL;
    }
    class ReadJSON extends AsyncTask<String, Integer,List<Restaurant>>{//load dữ liệu băng cách dùng asyntask

        @Override
        protected List<Restaurant> doInBackground(String... params) {
            String chuoi=getXmlFromUrl(params[0]);
            List<Restaurant> restaurants=new ArrayList<>();
            try {
                JSONArray jsonArray=new JSONArray(chuoi);//chuyen toan bo chuoi da load duoc sang JSONArray
                Log.d("Prepost",String.valueOf(currPos));
                currPos+=10;//Tiep tuc load 10 quan an tiep theo
                Log.d("Curpost",String.valueOf(currPos));
                if (chuoi.equals("[]")){//truong hop mà chuỗi trả về là rỗng thì không cho load tiếp
                    loadmore=false;
                }
                else {//ngược lại nếu còn dữ liệu thì tiếp tục load
                    loadmore=true;
                    //sẽ tạo vòng lặp lấy từng giá trị theo key tương ứng trong JSONArray vừa lấy được
                    for (int i=0;i<jsonArray.length();i++){
                        restaurants.add(new Restaurant(jsonArray.getJSONObject(i)));
                    }
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            return restaurants;//trả về 1 list quán ăn được sử dụng trong onPostExcute
        }

        @Override
        protected void onPostExecute(List<Restaurant> restaurants) {
            super.onPostExecute(restaurants);
            frameLayout.setVisibility(View.INVISIBLE);
            if (restaurants.size()==0){//nếu mà mảng trả về sau doinBackGround là rỗng thì dừng việc load dữ liệu
                if (firstLoad==true){//nếu mà load lần đầu mà không có dữ liệu thì ẩn listview đi và hiện ảnh error lên
                    gridViewODau2.setVisibility(View.GONE);
                    imgError.setVisibility(View.VISIBLE);
                }

            }

            else {//trường hợp ngược lại thì hiện listview
                imgError.setVisibility(View.INVISIBLE);
                gridViewODau2.setVisibility(View.VISIBLE);
                if(firstLoad==true){//nếu mà load lần đầu thì thêm vào adapter
                    mAdapter=new CustomListODauWithHolder(restaurants,getContext());
                    gridViewODau2.setAdapter(mAdapter);
                }
                else {
                    if (loadmore==true){//nếu là load more thì thêm dữ liệu vào adapter và lưu thay đổi
                        mAdapter.addListItemToAdapter(restaurants);
                    }
                    else {
                        Log.d("Stop","Het du lieu");
                    }
                }
            }

        }

        @Override
        protected void onPreExecute() {//trươc khi thực hiện việc doInBackground
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);//hiện lên progressbar
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
