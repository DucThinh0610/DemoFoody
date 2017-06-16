package ducthinh.foody1.android.a14110191_luuducthinh_foody1;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TabHost;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Alarm_Activity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Library_Activity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.MainActivity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.Search_Activity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.User_Activity;

public class HomeActivity extends TabActivity {
    TabHost TabHostWindow;
    public static LinearLayout llmenuhome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);
        llmenuhome=(LinearLayout) findViewById(R.id.linearmenu);
        //tạo các tabmenu
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");
        TabHost.TabSpec TabMenu4 = TabHostWindow.newTabSpec("Fourth Tab");
        TabHost.TabSpec TabMenu5 = TabHostWindow.newTabSpec("Fifth Tab");
        //Setting up tab 1 name.
        TabMenu1.setIndicator("",getResources().getDrawable(R.drawable.designbtnhome));//gán hình cho tabmenu bằng 1 drawable đã vẽ trước
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this,MainActivity.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("",getResources().getDrawable(R.drawable.designbuttonlibrary));
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this,Library_Activity.class));

        //Setting up tab 2 name.
        TabMenu3.setIndicator("",getResources().getDrawable(R.drawable.designbuttonlsearch));
        //Set tab 3 activity to tab 3 menu.
        TabMenu3.setContent(new Intent(this,Search_Activity.class));
        TabMenu4.setIndicator("",getResources().getDrawable(R.drawable.designbuttonalarm));
        //Set tab 3 activity to tab 3 menu.
        TabMenu4.setContent(new Intent(this,Alarm_Activity.class));
        TabMenu5.setIndicator("",getResources().getDrawable(R.drawable.designbuttonuser));
        //Set tab 3 activity to tab 3 menu.
        TabMenu5.setContent(new Intent(this,User_Activity.class));
        //Adding tab1, tab2, tab3 to tabhost view.
        //add các tab đã khởi tạo ở trên vào để hiện thị
        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);
        TabHostWindow.addTab(TabMenu4);
        TabHostWindow.addTab(TabMenu5);




    }
}
