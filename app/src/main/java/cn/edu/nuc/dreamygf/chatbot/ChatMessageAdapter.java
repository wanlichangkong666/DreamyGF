package cn.edu.nuc.dreamygf.chatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.nuc.dreamygf.R;
import cn.edu.nuc.dreamygf.bean.ChatMessage;

/**
 * Created by 51164 on 2018/5/31.
 */

public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ChatMessage> data;
    public ChatMessageAdapter(Context context,List<ChatMessage> data)
    {
        inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = data.get(position);
        if(chatMessage.getType()==ChatMessage.Type.INCOMING)
        {
            return 0;
        }
        return 1;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage=data.get(i);
        ViewHolder viewHolder = null;
        if(view == null)
        {
            //通过itemType设置不同的布局（发送、接收）
            if(getItemViewType(i)==0) {
                view = inflater.inflate(R.layout.item_from_msg, viewGroup, false);
                viewHolder=new ViewHolder();
                viewHolder.date = (TextView) view.findViewById(R.id.tv_from_msg_date);
                viewHolder.msg = (TextView) view.findViewById(R.id.tv_from_msg_info);
            }
            else {
                view = inflater.inflate(R.layout.item_to_msg, viewGroup, false);
                viewHolder=new ViewHolder();
                viewHolder.date = (TextView) view.findViewById(R.id.tv_to_msg_date);
                viewHolder.msg = (TextView) view.findViewById(R.id.tv_to_msg_info);
            }
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }
        //设置数据
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.date.setText(df.format(chatMessage.getDate()));
        viewHolder.msg.setText(chatMessage.getMsg());
        return view;
    }
    private final class ViewHolder
    {
        TextView date;
        TextView msg;
    }
}
