package ducthinh.foody1.android.a14110191_luuducthinh_foody1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class WaitingScreen extends AppCompatActivity {
    //đây là màn hình load ban đầu
    ProgressBar progressBar;//tạo 1 progress bar khi mà màn hình bắt đầu xuất hiện
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);
        progressBar=(ProgressBar) findViewById(R.id.pg_flash_screen);
        //khởi tạo thread, thread này sẽ kết thúc khi mà load được mà hình chính
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){

                }
                finally {
                    Intent intent=new Intent(WaitingScreen.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();//bắt đầu thread
    }
}
