package cn.edu.nuc.dreamygf.qrcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.nuc.dreamygf.R;

public class ScanActivity extends AppCompatActivity {

    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);
        tv_result=findViewById(R.id.tv_result);
        tv_result.setText(getIntent().getStringExtra("result"));
    }
}
