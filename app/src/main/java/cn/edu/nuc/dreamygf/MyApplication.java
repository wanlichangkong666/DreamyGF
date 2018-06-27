package cn.edu.nuc.dreamygf;

import android.app.Application;
import android.content.Context;

/**
 * Created by 51164 on 2018/6/20.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
