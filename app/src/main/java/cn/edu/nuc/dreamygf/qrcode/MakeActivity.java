package cn.edu.nuc.dreamygf.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

import cn.edu.nuc.dreamygf.*;
import cn.edu.nuc.dreamygf.utils.ActivityManager;

public class MakeActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_nickname;
    private EditText et_intelligence;
    private EditText et_food;
    private EditText et_sport;
    private EditText et_bf;
    private ImageView iv_qrcode;
    private CheckBox cb_logo;
    private Bitmap bg;
    private Bitmap image_make;
    private LinearLayout layout_make;
    private static final String TAG = "MakeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make);
        ActivityManager.getInstance().addActivity(this);
        et_name=findViewById(R.id.et_name);
        et_nickname=findViewById(R.id.et_nickname);
        et_intelligence=findViewById(R.id.et_intelligence);
        et_food=findViewById(R.id.et_food);
        et_sport=findViewById(R.id.et_sport);
        et_bf=findViewById(R.id.et_bf);
        iv_qrcode=findViewById(R.id.iv_qrcode);
        //image_make = BitmapFactory.decodeResource(getResources(),R.mipmap.ff2);
        iv_qrcode.setImageBitmap(image_make);
        cb_logo=findViewById(R.id.cb_logo);
        bg=BitmapFactory.decodeResource(getResources(),R.drawable.welcome);
        layout_make = findViewById(R.id.layout_make);
        layout_make.setBackgroundDrawable(new BitmapDrawable(bg));
    }
    public void generate(View view)
    {
        StringBuffer inputBuffer = new StringBuffer();
        inputBuffer.append("名字：");
        inputBuffer.append(et_name.getText().toString()) ;
        inputBuffer.append("\n\n");
        inputBuffer.append("昵称：");
        inputBuffer.append(et_nickname.getText().toString()) ;
        inputBuffer.append("\n\n");
        inputBuffer.append("智商：");
        inputBuffer.append(et_intelligence.getText().toString()) ;
        inputBuffer.append("\n\n");
        inputBuffer.append("爱好的食物：");
        inputBuffer.append(et_food.getText().toString()) ;
        inputBuffer.append("\n\n");
        inputBuffer.append("爱好的运动：");
        inputBuffer.append(et_sport.getText().toString()) ;
        inputBuffer.append("\n\n");
        inputBuffer.append("男朋友：");
        inputBuffer.append(et_bf.getText().toString()) ;
        String input = inputBuffer.toString();
        Bitmap bitmap= EncodingUtils.createQRCode(input,500,500,cb_logo.isChecked()? BitmapFactory.decodeResource(getResources(),R.drawable.use7):null);
        iv_qrcode.setImageBitmap(bitmap);
        iv_qrcode.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
//                if(iv_qrcode.getDrawable() instanceof BitmapDrawable){
//                    Toast.makeText(getApplicationContext(),"长按保存图片至相册",Toast.LENGTH_SHORT).show();
//                    File fileDir=new File(getApplication().getCacheDir(),"images");
//                    File file=new File(fileDir.getAbsolutePath()+"/"+"girlfriend.png");
//                    Toast.makeText(MakeActivity.this,file.getAbsolutePath(),Toast.LENGTH_LONG).show();
//                    if(file!=null&&file.length()>0){
//                        CameraRollManager rollManager=new CameraRollManager(MakeActivity.this, Uri.parse(file.getAbsolutePath()));
//                        Log.d(TAG, "onLongClick: "+file.getAbsolutePath());
//                        rollManager.execute();
//                    }
//                }
                saveImage((ImageView)v);
                return true;
            }
        });
    }
    private void saveImage(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);//开启catch，开启之后才能获取ImageView中的bitmap
        Bitmap bitmap = imageView.getDrawingCache();//获取imageview中的图像
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "这是title", "这是description");
        Toast.makeText(MakeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        imageView.setDrawingCacheEnabled(false);//关闭cache

    }

    @Override
    public void onBackPressed() {
        bg.recycle();
        super.onBackPressed();
        Intent intent = new Intent(this,cn.edu.nuc.dreamygf.qrcode.MainActivity.class);
        startActivity(intent);
    }
}
