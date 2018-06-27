package cn.edu.nuc.dreamygf.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

import cn.edu.nuc.dreamygf.R;

public class MakeActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_nickname;
    private EditText et_intelligence;
    private EditText et_food;
    private EditText et_sport;
    private EditText et_bf;
    private ImageView iv_qrcode;
    private CheckBox cb_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make);
        et_name=findViewById(R.id.et_name);
        et_nickname=findViewById(R.id.et_nickname);
        et_intelligence=findViewById(R.id.et_intelligence);
        et_food=findViewById(R.id.et_food);
        et_sport=findViewById(R.id.et_sport);
        et_bf=findViewById(R.id.et_bf);
        iv_qrcode=findViewById(R.id.iv_qrcode);
        cb_logo=findViewById(R.id.cb_logo);
    }
    public void generate(View view)
    {
        StringBuffer inputBuffer = new StringBuffer();
        inputBuffer.append("名字：");
        inputBuffer.append(et_name.getText().toString()) ;
        inputBuffer.append("\n");
        inputBuffer.append("昵称：");
        inputBuffer.append(et_nickname.getText().toString()) ;
        inputBuffer.append("\n");
        inputBuffer.append("智商：");
        inputBuffer.append(et_intelligence.getText().toString()) ;
        inputBuffer.append("\n");
        inputBuffer.append("爱好的食物：");
        inputBuffer.append(et_food.getText().toString()) ;
        inputBuffer.append("\n");
        inputBuffer.append("爱好的运动：");
        inputBuffer.append(et_sport.getText().toString()) ;
        inputBuffer.append("\n");
        inputBuffer.append("男朋友：");
        inputBuffer.append(et_bf.getText().toString()) ;
        String input = inputBuffer.toString();
        Bitmap bitmap= EncodingUtils.createQRCode(input,500,500,cb_logo.isChecked()? BitmapFactory.decodeResource(getResources(),R.drawable.love):null);
        iv_qrcode.setImageBitmap(bitmap);
    }
}
