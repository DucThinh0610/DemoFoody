package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.GetIP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.Model.User;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;

import static ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.User_Activity.lllogin;
import static ducthinh.foody1.android.a14110191_luuducthinh_foody1.View.User_Activity.llnologin;

public class EmailLogin extends AppCompatActivity {//activity login bằng email

    Button login;
    ImageView backtoregister;
    EditText username,password;
    FrameLayout frameLayout;
    public static User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        login=(Button)findViewById(R.id.loginemail);
        frameLayout=(FrameLayout)findViewById(R.id.framelogin); 
        backtoregister=(ImageView) findViewById(R.id.btnbacklogine);
        username=(EditText) findViewById(R.id.edituserlogin) ;
        password=(EditText)findViewById(R.id.editpasslogin) ;
        user=new User();
        backtoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checknotnull()==true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new loginToServer().execute((new GetIP().getIP(1).trim())+"api/User?email="+username.getText().toString().trim()+"&pass="+password.getText().toString().trim()+"");
                        }
                    });
                }
                else {
                    Toast.makeText(EmailLogin.this, "Không được bỏ trống tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checknotnull(){
        if (username.getText().toString().trim().equals("")||password.getText().toString().trim().equals("")){
            return false;
        }
        else {
            return true;
        }
    }
    class loginToServer extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String chuoi=getXmlFromUrl(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            frameLayout.setVisibility(View.GONE);
            //neu ma chuoi co gia tri la 0: chua co tai khoan trong db
            if (s.equals("0")){
                Toast.makeText(EmailLogin.this, "Tài khoản chưa được đăng kí trước!", Toast.LENGTH_SHORT).show();
            }
            //truong hop co user va trung pass
            else if (s.equals("1")){
                user.setEmail(username.getText().toString().trim());
                user.setIslogin(true);
                finish();
                if (EmailLogin.user.islogin()==true){
                    llnologin.setVisibility(View.GONE);
                    lllogin.setVisibility(View.VISIBLE);
                }
                else {
                    llnologin.setVisibility(View.VISIBLE);
                    lllogin.setVisibility(View.GONE);
                }
                Toast.makeText(EmailLogin.this, "chính xác", Toast.LENGTH_SHORT).show();
            }
            //truong co user nhung pass chua dung
            else {
                Toast.makeText(EmailLogin.this, "Mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
            }
            
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
