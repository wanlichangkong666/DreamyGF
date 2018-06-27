package cn.edu.nuc.dreamygf.qrcode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import cn.edu.nuc.dreamygf.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
    }
    public void scan(View view)
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
        }
        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),0);
    }

    public void make(View view)
    {
        Intent intent = new Intent(this,MakeActivity.class);
        startActivity(intent);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            Bundle bundle=data.getExtras();
            String result = bundle.getString("result");
            //添加
            Intent intent = new Intent(this,ScanActivity.class);
            intent.putExtra("result",result);
            startActivity(intent);
            //tv_result.setText(result);
        }

    }

}
