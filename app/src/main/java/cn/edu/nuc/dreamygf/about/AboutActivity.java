package cn.edu.nuc.dreamygf.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.edu.nuc.dreamygf.utils.ActivityManager;
import cn.edu.nuc.dreamygf.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ActivityManager.getInstance().addActivity(this);
    }
}
