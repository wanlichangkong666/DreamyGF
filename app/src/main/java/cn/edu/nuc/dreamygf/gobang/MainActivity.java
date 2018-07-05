package cn.edu.nuc.dreamygf.gobang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.edu.nuc.dreamygf.utils.ActivityManager;
import cn.edu.nuc.dreamygf.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ChessBoardView view;
    //private Toolbar toolbar;

    private Button btn_black;
    private Button btn_white;
    private Button btn_regret;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        // 全屏显示
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //modifyBeginning
        setContentView(R.layout.gobang);
        btn_black = findViewById(R.id.btn_black);
        btn_white = findViewById(R.id.btn_white);
        btn_regret = findViewById(R.id.btn_regret);
        //toolbar = findViewById(R.id.toolbar_gobang);


        //toolbar.setTitle("幻想女友陪你下五子棋");
        //设置toolbar
        //setSupportActionBar(toolbar);


        //modifyEnding
        ActivityManager.getInstance().addActivity(this);
        view = (ChessBoardView) findViewById(R.id.chessView);
        btn_black.setOnClickListener(this);
        btn_white.setOnClickListener(this);
        btn_regret.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        super.onResume();

    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void exit() {
//        if (!isExit) {
//            isExit = true;
//            Toast.makeText(getApplicationContext(), "再按一次退出程序",
//                    Toast.LENGTH_SHORT).show();
//            // 利用handler延迟发送更改状态信息
//            mHandler.sendEmptyMessageDelayed(0, 2000);
//        } else {
//            finish();
//            System.exit(0);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gobang, menu);
        menu.add(Menu.NONE, Menu.FIRST, 0, "开局(黑棋)");
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "开局(白棋)");
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "悔棋");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  Menu.FIRST:
                view.startBlack();return true;
            case Menu.FIRST+1:
                view.startWhite();return true;
            case Menu.FIRST+2:
                view.back();return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,cn.edu.nuc.dreamygf.MainActivity.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_black:
                view.startBlack();
                Toast.makeText(this,"成功开局（你是黑子）",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_white:
                view.startWhite();
                Toast.makeText(this,"成功开局（你是白子）",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_regret:
                view.back();
                Toast.makeText(this,"悔棋成功",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
