package cn.edu.nuc.dreamygf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.edu.nuc.dance.UnityPlayerActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_chat = null;
    private Button btn_gobang = null;
    private Button btn_dance = null;
    private Button btn_face = null;
    private Button btn_alarm = null;
    private Button btn_code = null;
    private Button btn_share = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_chat=findViewById(R.id.btn_chat);
        btn_gobang=findViewById(R.id.btn_gobang);
        btn_dance=findViewById(R.id.btn_dance);
        btn_face=findViewById(R.id.btn_face);
        btn_alarm=findViewById(R.id.btn_alarm);
        btn_code=findViewById(R.id.btn_code);
        btn_share=findViewById(R.id.btn_share);
//        MenuListener menuListener = new MenuListener(MainActivity.this);
//        btn_chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_gobang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.gobang.MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_dance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,UnityPlayerActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.qrcode.MainActivity.class);
//                startActivity(intent);
//            }
//        });
        btn_code.setOnClickListener(this);
        btn_alarm.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        btn_dance.setOnClickListener(this);
        btn_face.setOnClickListener(this);
        btn_gobang.setOnClickListener(this);
        btn_share.setOnClickListener(this);

       /* btn_chat.setOnClickListener(menuListener);
        btn_gobang.setOnClickListener(menuListener);*/
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.btn_chat:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_gobang:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.gobang.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dance:
                intent = new Intent(MainActivity.this,UnityPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_code:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.qrcode.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_alarm:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_face:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.face.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share:
                intent = new Intent(MainActivity.this,cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
