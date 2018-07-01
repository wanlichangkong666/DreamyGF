package cn.edu.nuc.dreamygf;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import cn.edu.nuc.dance.UnityPlayerActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_chat = null;
    private Button btn_gobang = null;
    private Button btn_dance = null;
    private Button btn_face = null;
    private Button btn_alarm = null;
    private Button btn_code = null;
    private Button btn_share = null;
    private Button btn_quit = null;
    private Tencent mTencent;
    private final String APP_ID = "101486938";
    private MediaPlayer musicPlayer = null;

    private class BaseUiListener implements IUiListener {


        @Override
        public void onComplete(Object o) {

        }

        @Override

        public void onError(UiError e) {


        }

        @Override

        public void onCancel() {


        }

    }

    private class BaseApiListener implements IRequestListener {


        @Override
        public void onComplete(JSONObject jsonObject) {

        }

        @Override
        public void onIOException(IOException e) {

        }

        @Override
        public void onMalformedURLException(MalformedURLException e) {

        }

        @Override
        public void onJSONException(JSONException e) {

        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException e) {

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException e) {

        }

        @Override
        public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

        }

        @Override
        public void onHttpStatusException(HttpUtils.HttpStatusException e) {

        }

        @Override
        public void onUnknowException(Exception e) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.getInstance().addActivity(this);
        btn_chat = findViewById(R.id.btn_chat);
        btn_gobang = findViewById(R.id.btn_gobang);
        btn_dance = findViewById(R.id.btn_dance);
        btn_face = findViewById(R.id.btn_face);
        btn_alarm = findViewById(R.id.btn_alarm);
        btn_code = findViewById(R.id.btn_code);
        btn_share = findViewById(R.id.btn_share);
        btn_quit = findViewById(R.id.btn_quit);
        mTencent = Tencent.createInstance(APP_ID, this);
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
        btn_quit.setOnClickListener(this);

       /* btn_chat.setOnClickListener(menuListener);
        btn_gobang.setOnClickListener(menuListener);*/
        musicPlayer=MediaPlayer.create(this,R.raw.tassel);
        musicPlayer.start();
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer arg0) {
                musicPlayer.start();
                musicPlayer.setLooping(true);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_chat:
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_gobang:
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.gobang.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dance:
                musicPlayer.stop();
                intent = new Intent(MainActivity.this, UnityPlayerActivity.class);
                startActivityForResult(intent,4);
                break;
            case R.id.btn_code:
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.qrcode.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_alarm:
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.alarm.activity.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_face:
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.face.MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share:
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, "快来玩幻想女友");
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "一款非常好玩的APP！");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://pic.58pic.com/58pic/14/26/51/56458PICRsN_1024.jpg");
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "幻想女友");
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN
                );
                mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
                break;
            case R.id.btn_quit:
                musicPlayer.stop();
                intent = new Intent(MainActivity.this, cn.edu.nuc.dreamygf.WelcomeActivity.class);
                startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //modifyBeginning
        if(requestCode == 4 && resultCode == 4)
        {
            ActivityManager.getInstance().addActivity((Activity)data.getExtras().getSerializable("dance"));
        }
        //modifyEnd
        else if (null != mTencent)
            mTencent.onActivityResult(requestCode, resultCode, data);
    }
}
