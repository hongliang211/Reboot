package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.ai_chat.R;

import java.util.List;

import Been.Msg_Info;

/**
 * Created by admin on 2018/5/21.
 */

public class Chat_Recyclerview_Adapter extends RecyclerView.Adapter<Chat_Recyclerview_Adapter.ViewHolder> {
    private List<Msg_Info>msg_infoList;
    Context context;

    public interface onclick{
        void onclick(View view,int position);
    }
    onclick onclick;
    public void setItem_inside(onclick onclick){
        this.onclick = onclick;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView_left_head,imageView_left_msg,imageView_right_head,imageView_right_msg;
        TextView textView_left_msg,textView_right_msg;
        public ViewHolder(View view){
            super(view);
            imageView_left_head = (ImageView)view.findViewById(R.id.left_head);
            imageView_right_head = (ImageView)view.findViewById(R.id.right_head);
            imageView_left_msg = (ImageView)view.findViewById(R.id.left_lmageview);
            imageView_right_msg = (ImageView)view.findViewById(R.id.right_lmageview);
            textView_left_msg = (TextView)view.findViewById(R.id.left_textview);
            textView_right_msg = (TextView)view.findViewById(R.id.right_textview);

        }

    }

    public Chat_Recyclerview_Adapter(List<Msg_Info> msg_infoList){
        this.msg_infoList = msg_infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int ViewType){
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item,viewGroup,false);
         ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        Msg_Info msg_info = msg_infoList.get(position);
        //接收信息
        if(msg_info.getMsgtype() == Msg_Info.TYPE_RECEIVED){
            Glide.with(context).load(R.drawable.head_2).into(holder.imageView_left_head);
            if(!(msg_info.getText_msg() == null)) {
                holder.textView_left_msg.setText(msg_info.getText_msg());
                //Glide.with(context).load(msg_info.getImg_msg()).into(holder.imageView_left_msg);
                holder.imageView_left_msg.setVisibility(View.GONE);
            }else{

                Glide.with(context).load(msg_info.getImg_msg()).into(holder.imageView_left_msg);
                holder.textView_left_msg.setVisibility(View.GONE);
            }
            holder.imageView_right_head.setVisibility(View.GONE);
            holder.imageView_right_msg.setVisibility(View.GONE);
            holder.textView_right_msg.setVisibility(View.GONE);
        }
        //发送信息
        else if(msg_info.getMsgtype() == Msg_Info.TYPE_SENT){

            Glide.with(context).load(R.drawable.head).into(holder.imageView_right_head);
            if(!(msg_info.getText_msg() == null)) {
                holder.textView_right_msg.setText(msg_info.getText_msg());
                //Glide.with(context).load(msg_info.getImg_msg()).into(holder.imageView_left_msg);
                holder.imageView_right_msg.setVisibility(View.GONE);
            }else{

                Glide.with(context).load(msg_info.getImg_msg()).into(holder.imageView_right_msg);
                holder.textView_right_msg.setVisibility(View.GONE);
            }
            holder.imageView_left_head.setVisibility(View.GONE);
            holder.imageView_left_msg.setVisibility(View.GONE);
            holder.textView_left_msg.setVisibility(View.GONE);


        }

        holder.imageView_right_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onclick(view,position);
            }
        });
    }

    public int getItemCount(){
        return msg_infoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

