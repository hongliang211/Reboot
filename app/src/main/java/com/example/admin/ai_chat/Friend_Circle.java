package com.example.admin.ai_chat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import Adapter.Friend_Circle_Adapter;
import Been.Friend_Circle_Info;
import Been.Msg_Info;
import DB.db_friend_circle;
import ItemDecoration.Item_divide_Decoration;
import de.hdodenhof.circleimageview.CircleImageView;


/*
朋友圈图片显示：
存在bug：当向顶部插入数据会记录倒数第二次的图片数据，调试发现朋友圈选择图片界面无bug，
朋友圈显示的recyclerview的适配器中打印出的信息也正常（当我不选择图片的时候，打印的图片列表的数量为0）
面前判断是Glide的问题，Glide的缓存机制可能记住了之前的图片，当你在第0位再次插入，可能显示之前在Glide缓存的
图片数据
 */

public class Friend_Circle extends AppCompatActivity  {
    RecyclerView recyclerView;
    Friend_Circle_Adapter adapter;
    List<Friend_Circle_Info>friend_circle_infoList;
    ArrayList<String>list;
    Friend_Circle_Info friend_circle_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setExitTransition(new Fade().setDuration(500));
        setContentView(R.layout.activity_friend__circle);

        /*
part_immersion
 */
        LitePal.getDatabase();

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        RefreshLayout smartRefreshLayout = (RefreshLayout)findViewById(R.id.smartrefresh);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
             //   init();
                adapter.notifyDataSetChanged();
                refreshLayout.finishRefresh(500);//传入false表示刷新失败


            }
        });

        list = new ArrayList<>();
        friend_circle_infoList = new ArrayList<>();


        recyclerView = (RecyclerView)findViewById(R.id.rec_friend_circle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new Friend_Circle_Adapter(friend_circle_infoList);
        adapter.setonclickListener(new Friend_Circle_Adapter.Onitemonclick() {
            @Override
            public void onclick(View  v , int position ,ArrayList<String> list) {
                Intent intent = new Intent(Friend_Circle.this,ViewPage_PhotoView.class);
                intent.putStringArrayListExtra("img_list",list);
                intent.putExtra("flag",position+"");
                /*
                共享元素的动画，适合图片的转场
                 */
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        Friend_Circle.this,
                        //通过键值对的形式，确定本Activity的专场时的起点view对象
                        new Pair<View, String>(v,
                                ViewPage_PhotoView.VIEW_NAME_HEADER_IMAGE));
                ActivityCompat.startActivity(Friend_Circle.this, intent, activityOptions.toBundle());
            }
        });
        recyclerView.addItemDecoration(new Item_divide_Decoration(this,LinearLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        setHeaderView(recyclerView);

        init();


    }

    public void init(){
      //  int i  =  0;
       // friend_circle_infoList.clear();
        List<db_friend_circle> db = DataSupport.findAll(db_friend_circle.class);
        for(db_friend_circle db_cha:db){
            Friend_Circle_Info friend_circle_info = new Friend_Circle_Info(db_cha.getName(),db_cha.getText()
            ,db_cha.getPath());
            friend_circle_infoList.add(0,friend_circle_info);
           // Log.i("name",db_cha.getFriend_circle_info().getName());
       //     i++;

        }


        adapter.notifyDataSetChanged();
      //  Log.i("size",String.valueOf(i));

    }

    private void setHeaderView(RecyclerView view){

        View header = LayoutInflater.from(Friend_Circle.this).inflate(R.layout.friend_circle_rec_header,view,false);
        ImageView image = (ImageView)header.findViewById(R.id.img_header);
        CircleImageView circleImageView = (CircleImageView)header.findViewById(R.id.circleImageView);
       // Glide.with(this).load(R.drawable.header).into(image);
        Glide.with(this).load(R.drawable.head).into(circleImageView);
        adapter.setHeaderView(header);

    }


    public void open(View view){
        Intent intent = new Intent(Friend_Circle.this,Friend_Circle_Publish.class);
        startActivityForResult(intent,1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void hui_tui(View view){
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    friend_circle_info =(Friend_Circle_Info)data.getSerializableExtra("friend_circle_info");
                    friend_circle_infoList.add(0,friend_circle_info);

                    //存入数据库
                    db_friend_circle db_friend_circle1 = new db_friend_circle();
                    db_friend_circle1.setName(friend_circle_info.getName());
                    db_friend_circle1.setText(friend_circle_info.getText());
                    db_friend_circle1.setPath(friend_circle_info.getImglist());
                    db_friend_circle1.save();

                    Log.i("img_list.size",String.valueOf(friend_circle_infoList.size()));
                    adapter.notifyItemInserted(1);

                    recyclerView.scrollToPosition(1);



                }
        }
    }

}
