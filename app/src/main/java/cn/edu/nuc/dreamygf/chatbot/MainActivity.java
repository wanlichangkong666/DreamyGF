package cn.edu.nuc.dreamygf.chatbot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.nuc.dreamygf.R;
import cn.edu.nuc.dreamygf.bean.ChatMessage;
import cn.edu.nuc.dreamygf.utils.HttpUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView msg;
    private ChatMessageAdapter adapter;
    private List<ChatMessage> data;
    private EditText et_input_msg;
    private Button btn_send_msg;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //等待接收子线程返回的数据
            ChatMessage fromMessage = (ChatMessage)msg.obj;
            data.add(fromMessage);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat);
        //初始化布局
        initView();
        //初始化数据
        initData();
        //初始化事件
        initListener();

    }

    private void initListener() {
        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String toMsg = et_input_msg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(MainActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                ChatMessage toMessage = new ChatMessage();
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                toMessage.setDate(new Date());
                data.add(toMessage);
                adapter.notifyDataSetChanged();
                et_input_msg.setText("");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        //data.add(fromMessage);
                        //adapter.notifyDataSetChanged();
                        handler.sendMessage(m);
                    }
                }).start();


            }
        });
    }

    private void initData() {
        data = new ArrayList<ChatMessage>();
        data.add(new ChatMessage("亲爱的，我好想你", ChatMessage.Type.INCOMING, new Date()));
        //data.add(new ChatMessage("我是万里长空", ChatMessage.Type.OUTCOMING, new Date()));
        adapter = new ChatMessageAdapter(this, data);
        msg.setAdapter(adapter);
    }
    private void initView() {
        msg = findViewById(R.id.lv_msg);
        et_input_msg = findViewById(R.id.et_input_msg);
        btn_send_msg = findViewById(R.id.btn_send_msg);

    }
}
