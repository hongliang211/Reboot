package Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.ai_chat.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Been.Choose_Photo_Info;

/**
 * Created by admin on 2018/5/20.
 */

public class Choose_Photo_Recyclerview_Adapter extends RecyclerView.Adapter<Choose_Photo_Recyclerview_Adapter.ViewHolder>{

    private List<Choose_Photo_Info> choose_photo_infoList;
    public Context context;
    private ImageView oldImg=null; //上次点击的打勾图片

    public interface OnItemClickListener{
        void OnItemClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView,imageViewyes;

        public ViewHolder(View view){
            super(view);
            imageView = (ImageView) view.findViewById(R.id.id_choose_photo_img1);
            imageViewyes = (ImageView) view.findViewById(R.id.id_choose_photo_img2);
            imageViewyes.setAlpha(0);

        }
    }

    public Choose_Photo_Recyclerview_Adapter(List<Choose_Photo_Info> choose_photo_infoList){
        this.choose_photo_infoList = choose_photo_infoList;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType){
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.choose_photo_item_xml,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , int position){

        Choose_Photo_Info choose_photo_info = choose_photo_infoList.get(position);
/*

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        choose_photo_info.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context).load(bytes).into(holder.imageView);

*/
        Glide.with(context).load(R.drawable.a0p).into(holder.imageViewyes);
        holder.imageView.setImageBitmap(choose_photo_info.getBitmap());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                if(onItemClickListener != null) {
                    onItemClickListener.OnItemClick(holder.itemView, pos);
                }
                if(oldImg != null){
                    oldImg.setAlpha(0);
                }
                oldImg = holder.imageViewyes;
                holder.imageViewyes.setAlpha(255);


            }
        });

    }

    @Override
    public int getItemCount(){
        return choose_photo_infoList.size();
    }

}
