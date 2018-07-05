package cn.edu.nuc.dreamygf;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.nuc.dreamygf.utils.ActivityManager;
import cn.edu.nuc.dreamygf.utils.ConfigUtil;
import cn.edu.nuc.dreamygf.utils.HttpUtils;
import cn.edu.nuc.dreamygf.utils.PublicFunction;


public class WelcomeActivity extends AppCompatActivity{

    private static final String TAG = "WelcomeActivity";
    private EditText et_username;
    private EditText et_password;
    private Button btn_register;
    private Button btn_login;
    private Button btn_quit_app;
    private ConfigUtil configUtil;
    //private String loginUrl="http://10.130.148.60:8080/DreamyGFServer/LoginServlet";
    //private String registerUrl="http://10.130.148.60:8080/DreamyGFServer/RegisterServlet";
    //private String loginUrl="http://10.0.2.2:8080/DreamyGFServer/LoginServlet";
    //private String registerUrl="http://10.0.2.2:8080/DreamyGFServer/RegisterServlet";
    //手机开启USB网络共享
    private String loginUrl="http://192.168.42.169:8080/DreamyGFServer/LoginServlet";
    private String registerUrl="http://192.168.42.169:8080/DreamyGFServer/RegisterServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ActivityManager.getInstance().addActivity(this);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        btn_quit_app = findViewById(R.id.btn_quit_app);
        configUtil = new ConfigUtil(this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate(et_username.getText().toString(),et_password.getText().toString()))
                //实现注册
                register();

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if(validate(et_username.getText().toString(),et_password.getText().toString()))
                //实现登录
                login();

            }
        });
        btn_quit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityManager.getInstance().exit();
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);

            }
        });
        et_password.setOnFocusChangeListener(PublicFunction.onFocusAutoClearHintListener);
        et_username.setOnFocusChangeListener(PublicFunction.onFocusAutoClearHintListener);

    }

    class LoginTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            //System.out.println(url);
            String result = doLogin(url);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //保存数据
            if(result!=null&&result.equals("-1"))
            {
                Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_LONG).show();
            }
           else
            {
                Log.d(TAG, "onPostExecute: "+result);
                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                //保存在客户端
                configUtil.setUserJson(result);
                //跳转
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        }
    }
    class RegisterTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            //System.out.println(url);
            String result = doLogin(url);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute: "+result);
            //保存数据
            if(result!=null&&result.equals("-1"))
            {
                Toast.makeText(getApplicationContext(),"用户已存在",Toast.LENGTH_LONG).show();
            }
            else
            {

                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();

            }
            et_username.setText("");
            et_password.setText("");
        }
    }
    public void login()
    {
        //使用AS自带模拟器
        //String url="http://10.0.2.2:8080/DreamyGFServer/LoginServlet";
        //Genymotion看作真机

        new LoginTask().execute(loginUrl);
    }
    String doLogin(String url)
    {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String result = HttpUtils.doPost(url,username,password);
        return result;

    }

    public void register()
    {
        new RegisterTask().execute(registerUrl);
    }
    String doRegister(String url)
    {

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String result = HttpUtils.doPost(url,username,password);
        return result;

    }
    boolean validate(String username,String password)
    {
        if(username.length()<3||password.length()<6)
        {
            Toast.makeText(this,"用户名长度不能小于3位，密码长度不能小于6位",Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            return true;
        }
    }
}
