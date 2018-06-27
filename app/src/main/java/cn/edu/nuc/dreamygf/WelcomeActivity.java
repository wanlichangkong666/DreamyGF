package cn.edu.nuc.dreamygf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.nuc.dance.UnityPlayerActivity;


public class WelcomeActivity extends AppCompatActivity{

    private EditText et_username;
    private EditText et_password;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
