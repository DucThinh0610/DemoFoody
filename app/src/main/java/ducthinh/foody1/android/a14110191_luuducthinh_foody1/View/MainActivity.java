package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter.MyFragmentPagerAdapters;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Fragment.FragmentAnGi;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Fragment.FragmentODau;

public class MainActivity extends AppCompatActivity {//activity chứa 2 fragment là ăn gì và ở đâu
    ViewPager viewPager;//2 fragment sẽ được để trong viewpager
    Button btnFoody,btnAdd;//2 button ở đâu và ăn gì
    RadioGroup rdGroup;//sử dụng radio group để bao 2 nút trên
    RadioButton btnAnGi,btnODau;//nút nă gì ở đâu
    public static LinearLayout llChung,llTab,llPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //khởi tạo các mục cần thiết và ánh xạ cho chúng
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        rdGroup =(RadioGroup) findViewById(R.id.radioGroup);
        llChung=(LinearLayout)findViewById(R.id.linearChung);
        llTab=(LinearLayout)findViewById(R.id.linearTab);
        llPager=(LinearLayout)findViewById(R.id.linearPager);
        btnAnGi=(RadioButton) findViewById(R.id.btnangi);
        btnODau=(RadioButton)findViewById(R.id.btnodau);
        btnFoody=(Button)findViewById(R.id.btnFoody);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện click vào btn thêm quán ăn mới
                Toast.makeText(MainActivity.this, "Click btn add", Toast.LENGTH_SHORT).show();
            }
        });
        btnFoody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//sự kiện click vào btn F
                Intent intent=new Intent(MainActivity.this,FoodyCategory.class);
                startActivity(intent);
            }
        });
        btnODau.setChecked(true);
        //sự kiện khi thay đổi click vào btn ở đâu và ăn gì
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (btnODau.isChecked()==true){
                    viewPager.setCurrentItem(0);
                    btnAnGi.setTextColor(Color.parseColor("#FFFFFF"));
                    btnODau.setTextColor(Color.parseColor("#575656"));
                }
                else {
                    viewPager.setCurrentItem(1);
                    btnODau.setTextColor(Color.parseColor("#FFFFFF"));
                    btnAnGi.setTextColor(Color.parseColor("#575656"));
                }
            }
        });
        //sự kiện scroll ngang màn hình để đổi giữa 2 fragment
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    btnODau.setChecked(true);
                }
                else {
                    btnAnGi.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setFragment();
    }
    //khởi tạo fragment
    private void setFragment(){
        List<Fragment> listFragments=new ArrayList<Fragment>();
        listFragments.add(new FragmentODau());
        listFragments.add(new FragmentAnGi());
        MyFragmentPagerAdapters myFragmentPagerAdapters=new MyFragmentPagerAdapters(getSupportFragmentManager(),listFragments);
        viewPager.setAdapter(myFragmentPagerAdapters);
    }
}
