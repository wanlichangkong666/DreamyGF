package cn.edu.nuc.dreamygf.face;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.megvii.cloud.http.CommonOperate;
import com.megvii.cloud.http.FaceSetOperate;
import com.megvii.cloud.http.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import cn.edu.nuc.dreamygf.R;

public class MainActivity extends AppCompatActivity {
    private TextView tv_score;
    private Button btn_upload;
    private Button btn_evaluate;
    private ImageView iv_photo;
    private String currentPhotoStr;
    private Bitmap photoImg = null;
    private final int PICK_CODE = 0x001;
    String key = "jfqGHX7XUVb4KP6GS-YQ4IGbpN5G92KY";//api_key
    String secret = "1qr0jsHMaA7xG7WpFmmjJfdk-4mziOhm";//api_secret

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face);
        tv_score = (TextView) findViewById(R.id.tv_score);
        btn_upload = findViewById(R.id.btn_upload);
        btn_evaluate = findViewById(R.id.btn_evaluate);
        iv_photo = findViewById(R.id.iv_photo);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_CODE);
            }
        });
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoImg == null)
                {
                    Toast.makeText(MainActivity.this,"请上传照片",Toast.LENGTH_SHORT).show();
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CommonOperate commonOperate = new CommonOperate(key, secret, false);
                            try {
                                Bitmap bm = Bitmap.createBitmap(photoImg, 0, 0, photoImg.getWidth(), photoImg.getHeight());
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] arrays = stream.toByteArray();
                                Response response = commonOperate.detectByte(arrays, 0, "beauty");
                                String result = new String(response.getContent());
                                JSONObject jsonObject0 = new JSONObject(result);
                                JSONArray jsonArray = jsonObject0.getJSONArray("faces");
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                JSONObject jsonObject2 = jsonObject1.getJSONObject("attributes");
                                JSONObject jsonObject3 = jsonObject2.getJSONObject("beauty");
                                double female = jsonObject3.getDouble("female_score");
                                double male = jsonObject3.getDouble("male_score");
                                final String show = "女生眼中的你：" + female + "分\n男生眼中的你：" + male + "分";
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_score.setText(show);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CODE) {
            if (intent != null) {
                Uri uri = intent.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                currentPhotoStr = cursor.getString(idx);
                cursor.close();
                resizePhoto();
                iv_photo.setImageBitmap(photoImg);

            }
        }
    }

    //压缩图片，sdk要求上传的二进制数据不能大于3M
    private void resizePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoStr, options);
        double ratio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        options.inSampleSize = (int) Math.ceil(ratio);
        options.inJustDecodeBounds = false;
        photoImg = BitmapFactory.decodeFile(currentPhotoStr, options);

    }

}