package cn.edu.nuc.dance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by 51164 on 2018/6/29.
 */

public class ExtendActivity extends UnityPlayerActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);

    }
    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)

        {

            sendBroadcast(new Intent("finish"));
//            finish();
            Log.e("key", "key");
        }
// TODO Auto-generated method stub

        return super.onKeyDown(keyCode, event);
    }
}
