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

import Been.Friend_Info;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/5/24.
 */

public class Friend_Recyclerview_Adapter extends RecyclerView.Adapter<Friend_Recyclerview_Adapter.ViewHolder> {

    private List<Friend_Info>friend_infoList;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView_friend_head;
        TextView textView_friend_name;
        public ViewHolder(View view){
            super(view);
            imageView_friend_head = (ImageView) view.findViewById(R.id.friend_head);
            textView_friend_name = (TextView)view.findViewById(R.id.friend_name);
        }
    }

    public Friend_Recyclerview_Adapter(List<Friend_Info>friend_infoList){
        this.friend_infoList = friend_infoList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int ViewType){
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder , int position){
       Glide.with(context).load(R.drawable.head).into(viewHolder.imageView_friend_head);
        Friend_Info friend_info = friend_infoList.get(position);
        viewHolder.textView_friend_name.setText(friend_info.getName());

    }

    @Override
    public int getItemCount(){
        return friend_infoList.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }


}
