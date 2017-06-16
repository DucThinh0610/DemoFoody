package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.EmptyClass;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class Alarm_Activity extends TabActivity {//tab thông báo
    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_);
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");
        TabMenu1.setIndicator("Dịch vụ");
        TabMenu2.setIndicator("Của tôi");
        TabMenu3.setIndicator("Tin mới");
        TabMenu3.setContent(new Intent(this,EmptyClass.class));
        TabMenu2.setContent(new Intent(this,EmptyClass.class));
        TabMenu1.setContent(new Intent(this,EmptyClass.class));
        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);
    }
}
