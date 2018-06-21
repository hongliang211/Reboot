package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.ai_chat.R;

import java.util.List;

/**
 * Created by admin on 2018/5/27.
 */

public class Friend_Circle_Publish_Recyclerview_Adapter extends RecyclerView.Adapter<Friend_Circle_Publish_Recyclerview_Adapter.ViewHolder> {

    private List<String>img_List;
    private int resourse;
    private Context context;
    public onclickListener onclickListener;


    public interface onclickListener{
        void onclick(View v ,int position);
        boolean onLongclick(View v , int position);
    }

    public void setOnclick_item_insideListener(onclickListener onclick_item_insideListener){
        this.onclickListener = onclick_item_insideListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.img_friend_circle_publish_choose);

        }

    }



    public Friend_Circle_Publish_Recyclerview_Adapter(List<String>img_List , int resourse){
        this.img_List = img_List;
        this.resourse = resourse;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType){
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(
                R.layout.friend_circle_publish_choose_rec_item,viewGroup,false
        );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder , final int position){



        if(position == img_List.size() )
        Glide.with(context).load(resourse).into(viewHolder.imageView);

        else
            Glide.with(context).load(img_List.get(position)).into(viewHolder.imageView);
        Log.i("chuxian",String.valueOf(position));

//for(int i = 0 ;i <= position ; i++){
//    if (i < img_List.size())
//        Glide.with(context).load(img_List.get(i)).into(viewHolder.imageView);
//    else
//        Glide.with(context).load(resourse).into(viewHolder.imageView);
//
//
//}
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onclick(view,position);
            }
        });//单点

        viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onclickListener.onLongclick(view , position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        return img_List.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
