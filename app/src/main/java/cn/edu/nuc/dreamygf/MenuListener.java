package cn.edu.nuc.dreamygf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by 51164 on 2018/6/20.
 */

public class MenuListener extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    public MenuListener(Context context)
    {
        this.context=context;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_chat:
                Intent intent1 = new Intent(context,cn.edu.nuc.dreamygf.chatbot.MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_gobang:
                Intent intent2 = new Intent(context,cn.edu.nuc.dreamygf.gobang.MainActivity.class);
                startActivity(intent2);
                break;


        }
    }
}
