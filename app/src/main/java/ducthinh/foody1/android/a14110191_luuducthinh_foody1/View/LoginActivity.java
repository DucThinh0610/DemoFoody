package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

public class LoginActivity extends AppCompatActivity {//activity login
    Button loginphone,loginemail,register;
    ImageView backtomenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginphone=(Button)findViewById(R.id.btnloginphone) ;
        loginemail=(Button)findViewById(R.id.btnloginemail) ;
        register=(Button)findViewById(R.id.buttonregister);
        backtomenu=(ImageView)findViewById(R.id.imageViewbacktomenu);
        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//khi mà click vào đăng kí thì đóng activity này và mở form đăng kí
                Intent intent2=new Intent(getApplicationContext(),Register.class);
                startActivity(intent2);
                finish();
            }
        });
        loginphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EmailLogin.class);
                startActivity(intent);
                finish();
            }
        });
        loginemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginphone.performClick();
            }
        });

    }

    @Override
    public void onBackPressed() {//sự kiện thoát bằng nút back
        super.onBackPressed();
        finish();
    }
}
