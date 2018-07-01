package cn.edu.nuc.dreamygf.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 51164 on 2018/6/28.
 */

public class ConfigUtil {
    SharedPreferences sp ;//获取数据
    SharedPreferences.Editor editor;//保存数据
    public static  final String USER_KEY = "user_key";
    public String getUserJson() {
        return sp.getString(USER_KEY,"");
    }

    public void setUserJson(String userJson) {
        editor.putString(USER_KEY,userJson);
        editor.commit();
    }

    String userJson;
    public  ConfigUtil(Context context)
    {
        sp = context.getSharedPreferences("my_sp",Context.MODE_PRIVATE);
        editor = sp.edit();
    }

}
