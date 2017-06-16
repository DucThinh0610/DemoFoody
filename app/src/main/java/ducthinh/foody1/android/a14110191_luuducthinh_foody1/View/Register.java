package ducthinh.foody1.android.a14110191_luuducthinh_foody1.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ducthinh.foody1.android.a14110191_luuducthinh_foody1.DBConnect.GetIP;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.HomeActivity;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.R;
import ducthinh.foody1.android.a14110191_luuducthinh_foody1.WaitingScreen;

public class Register extends AppCompatActivity {//màn hình đăng kí
    //khởi tạo các mục cần thiết
    Button register,btnlogin;
    ImageView backtomenu;
    EditText email,password,password2,disname;
    FrameLayout frameLayout;//khi load sẽ show ra progressbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //ánh xạ
        register=(Button)findViewById(R.id.buttonreg);
        btnlogin=(Button)findViewById(R.id.btnloginfromreg);
        email=(EditText)findViewById(R.id.editemailreg);
        password=(EditText)findViewById(R.id.editpassreg);
        password2=(EditText)findViewById(R.id.editpass2reg);
        disname=(EditText)findViewById(R.id.editdisnamereg);
        backtomenu=(ImageView)findViewById(R.id.imageViewRegister);
        frameLayout=(FrameLayout)findViewById(R.id.frameregister);
        //sự kiện click vào nút xác nhận đăng kí
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkisnull()==true){//kiểm tra tính đúng đắn của dữ liệu
                    Toast.makeText(Register.this, "Vui lòng không bỏ các trường!", Toast.LENGTH_SHORT).show();
                }
                else if(checkisEqual()==false){
                    Toast.makeText(Register.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //xác nhận đăng kí bằng cách gửi request lên server bằng 1 đường link chỉ cần truyền các tham số cần thiết
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new registerToServer().execute((new GetIP().getIP(1).trim())+"api/User?email="+email.getText().toString().trim()+"" +
                                    "&pass="+password2.getText().toString().trim()+"&disName="+disname.getText().toString().trim()+"");
                        }
                    });
                }
            }
        });
    }
    private boolean checkisEqual(){//kiểm tra 2 pass có giống nhau không
        if (password2.getText().toString().trim().equals(password.getText().toString().trim())){
            return true;
        }
        else {
            return false;
        }
    }
    private boolean checkisnull(){//kiểm tra có trường nào null không
        if (email.getText().toString().trim().equals("")||
                password2.getText().toString().trim().equals("")||
                password.getText().toString().trim().equals("")||
                disname.getText().toString().trim().equals("")){
            return true;
        }
        else {
            return false;
        }
    }
    //gửi request lên sv bằng cách dùng asyntask
    class registerToServer extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {//trước khi thực hiện doInBackground thì show ra progressbar
            super.onPreExecute();
            frameLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {//thực hiện ngầm trả về chuỗi có giá trị là toàn bộ giá trị trong URL
            String chuoi=getXmlFromUrl(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            frameLayout.setVisibility(View.GONE);
            Log.d("have s",s);
            if (s.trim().equals("true")){
                //nếu mà server trả về là true, cho phép đăng kí và chuyển màn hình sang màn hình đăng nhập
                Toast.makeText(Register.this, "Đăng kí thành công, bạn có thể đăng nhập", Toast.LENGTH_SHORT).show();
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        }
                        catch (Exception e){

                        }
                        finally {
                            //chuyển màn hình
                            Intent intent=new Intent(getApplicationContext(),EmailLogin.class);
                            intent.putExtra("email",email.getText().toString().trim());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                thread.start();
            }
            else {
                Toast.makeText(Register.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  String getXmlFromUrl(String urlString){//hàm này lấy dữ liệu từ webservies
        String xml=null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);
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
    public void onBackPressed() {//thoát bằng nút back cứng
        super.onBackPressed();
        finish();
    }
}
