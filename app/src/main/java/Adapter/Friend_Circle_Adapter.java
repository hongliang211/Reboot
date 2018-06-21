package Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.ai_chat.Friend_Circle;
import com.example.admin.ai_chat.R;

import java.util.ArrayList;
import java.util.List;

import Been.Friend_Circle_Info;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/5/28.
 */

/*
可以添加头部和脚部的adapter
 */
public class Friend_Circle_Adapter extends RecyclerView.Adapter<Friend_Circle_Adapter.ViewHolder>{

    private List<Friend_Circle_Info> list;
    private Context context;

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //评论的动画平移
    private int move = 0;
//    private int last_move = 0;
//    private int last_position = -1;
//    private float last_qi_dian;
//    private View last_view;


    private View mHeaderView;
    private View mFooterView;
    public Onitemonclick onitemonclick;


    public interface Onitemonclick{
        void onclick(View v ,int position,ArrayList<String> list);
    }

    public void setonclickListener(Onitemonclick onitemonclick){
        this.onitemonclick = onitemonclick;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textView_name,textView_text;
        ImageView []imageViews = new ImageView[9];
        View view_;
        ImageView imageView_ping_lun;
        View ping_lun_tan_chu;


        public ViewHolder(View view){
            super(view);

            if (view == mHeaderView){

                return;
            }
            if (view == mFooterView){
                return;
            }

             view_ = view;
            circleImageView = (CircleImageView) view.findViewById(R.id.img_head);
            textView_name = (TextView)view.findViewById(R.id.text_name);
            textView_text = (TextView)view.findViewById(R.id.text_main);
            imageViews[0] = (ImageView)view.findViewById(R.id.img_1);
            imageViews[1] = (ImageView)view.findViewById(R.id.img_2);
            imageViews[2] = (ImageView)view.findViewById(R.id.img_3);
            imageViews[3] = (ImageView)view.findViewById(R.id.img_4);
            imageViews[4] = (ImageView)view.findViewById(R.id.img_5);
            imageViews[5] = (ImageView)view.findViewById(R.id.img_6);
            imageViews[6] = (ImageView)view.findViewById(R.id.img_7);
            imageViews[7] = (ImageView)view.findViewById(R.id.img_8);
            imageViews[8] = (ImageView)view.findViewById(R.id.img_9);

            imageView_ping_lun = (ImageView)view.findViewById(R.id.imageView);
            ping_lun_tan_chu = view.findViewById(R.id.ping_lun_tan_chu);


        }
    }

    public Friend_Circle_Adapter(List<Friend_Circle_Info>list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType){

        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }


        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.friend_circle_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder , final int position){







        //头部
        if(getItemViewType(position) == TYPE_HEADER){

            return;
        }
//position 变成 position - 1


        final int position_ = position - 1;
        Glide.with(context).load(R.drawable.head).into(holder.circleImageView);
        holder.textView_name.setText(list.get(position_).getName());
        holder.textView_text.setText(list.get(position_).getText());
       final int size = list.get(position_).getImglist().size();

        //评论
        holder.imageView_ping_lun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                if(last_position != position)
                    if(last_move == 1)
                    {
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(last_view
                                ,"translationX", last_qi_dian
                                , 0);

                        objectAnimator.setDuration(300);
                        objectAnimator.start();
                    }
                    */
                if(move == 0){
                    float qi_dian = holder.ping_lun_tan_chu.getTranslationX();
                    float zhong_dian = - (holder.ping_lun_tan_chu.getWidth());
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.ping_lun_tan_chu
                            ,"translationX", qi_dian
                            , zhong_dian);

                    objectAnimator.setDuration(300);
                    objectAnimator.start();

                    move = 1;


                }
                else{
                    float qi_dian = holder.ping_lun_tan_chu.getTranslationX();
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.ping_lun_tan_chu
                            ,"translationX",qi_dian,0);

                    objectAnimator.setDuration(300);
                    objectAnimator.start();

                    move = 0;
                }

                /*
                last_position = position;
                last_qi_dian = holder.ping_lun_tan_chu.getWidth();
                last_view = holder.ping_lun_tan_chu;
                */

            }
        });

        Log.i("img_list.size", String.valueOf(position_)+"   "
                + String.valueOf(size)
        );

        if(size == 0 || holder == null)
            return;
        for (int i = 0; i < size; i++) {
            final int pos = i;

            Glide.with(context).load(list.get(position_).getImglist().get(i))
                    .into(holder.imageViews[i]);
            holder.imageViews[i].setVisibility(View.VISIBLE);

            //image点击事件
            holder.imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    onitemonclick.onclick(view,pos , (ArrayList)list.get(position_).getImglist());
                }
            });
            //解决 constrainslayout 的链式布局导致一行只有一或者张图片时居中显示

            if (i == size - 1 && i < 2) {
                for (int j = i; j <= 2; j++)
                    holder.imageViews[j].setVisibility(View.VISIBLE);
            } else if (i == size - 1 && i > 2 && i < 5) {
                for (int j = i; j <= 5; j++)
                    holder.imageViews[j].setVisibility(View.VISIBLE);
            } else if (i == size - 1 && i > 5 && i < 8) {
                for (int j = i; j <= 8; j++)
                    holder.imageViews[j].setVisibility(View.VISIBLE);
            }


        }




    }

    @Override
    public void onViewRecycled(ViewHolder holder){
//        if(holder!=null){
//            for(int i = 0; i<9; i++)
//            Glide.clear(holder.imageViews[i]);
//        }
        super.onViewRecycled(holder);

    }

    @Override
    public int getItemCount(){
        if(mHeaderView == null && mFooterView == null){
            return list.size();
        }else if(mHeaderView == null && mFooterView != null){
            return list.size() + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return list.size() + 1;
        }else {
            return list.size() + 2;
        }

    }



    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }


    @Override
    public int getItemViewType(int position){
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return position  + 1;


    }
}
