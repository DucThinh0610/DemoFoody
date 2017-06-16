package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.EmptyClass;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class Search_Activity extends TabActivity {
    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");
        TabMenu1.setIndicator("Xem gần đây");
        TabMenu2.setIndicator("Đặt gần đây");
        TabMenu3.setIndicator("Đã tìm");
        TabMenu3.setContent(new Intent(this,EmptyClass.class));
        TabMenu2.setContent(new Intent(this,EmptyClass.class));
        TabMenu1.setContent(new Intent(this,EmptyClass.class));
        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);
    }
}
