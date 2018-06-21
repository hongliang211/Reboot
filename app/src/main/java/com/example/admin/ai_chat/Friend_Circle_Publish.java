package com.example.admin.ai_chat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.Friend_Circle_Publish_Recyclerview_Adapter;
import Been.Friend_Circle_Info;
import Been.Msg_Info;

public class Friend_Circle_Publish extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String>img_List;//img's path
    Friend_Circle_Info  friend_circle_info;//返回的类型
    Friend_Circle_Publish_Recyclerview_Adapter adapter;
    EditText editText;
    String s="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //activity专场动画所需
        //此activity进入
        getWindow().setEnterTransition(new Slide().setDuration(500));
//此activity退出
        getWindow().setExitTransition(new Fade().setDuration(200));


        setContentView(R.layout.activity_friend__circle__publish);
/*
part_immersion
 */
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        img_List = new ArrayList<>();

        friend_circle_info = new Friend_Circle_Info();
        editText = (EditText)findViewById(R.id.textview_publish);
        init();

        recyclerView = (RecyclerView)findViewById(R.id.rec_friend_circle_publish_choose);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
         adapter = new Friend_Circle_Publish_Recyclerview_Adapter(img_List , R.drawable.add_img);
        adapter.setOnclick_item_insideListener(new Friend_Circle_Publish_Recyclerview_Adapter.onclickListener() {
            @Override
            public void onclick(View v, int position) {

                if(position == img_List.size()){
                    //打开图片选择界面
                    if(img_List.size() >= 9){
                        Toast.makeText(Friend_Circle_Publish.this,"最多展示九张！",Toast.LENGTH_LONG).show();

                    }
                    else {

                        Intent intent = new Intent(Friend_Circle_Publish.this, Choose_Photo.class);
                        startActivityForResult(intent, 1);
                        overridePendingTransition(R.anim.translate2, 0);
                    }
                }
                else{
                    //打开图片查看界面

                    Intent intent = new Intent(Friend_Circle_Publish.this,ViewPage_PhotoView.class);
                    intent.putStringArrayListExtra("img_list",img_List);
                    intent.putExtra("flag",position+"");
                /*
                共享元素的动画，适合图片的转场
                 */
                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            Friend_Circle_Publish.this,
                            //通过键值对的形式，确定本Activity的专场时的起点view对象
                            new Pair<View, String>(v,
                                    ViewPage_PhotoView.VIEW_NAME_HEADER_IMAGE));
                    ActivityCompat.startActivity(Friend_Circle_Publish.this, intent, activityOptions.toBundle());
                }
            }
            @Override
            public boolean onLongclick(View v, int position) {
                Toast.makeText(Friend_Circle_Publish.this,"sd",Toast.LENGTH_LONG).show();

                return true;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




    }

    public  void init(){
        /*
     img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        img_List.add("http://pic.qjimage.com/culturarm002/high/is09aa5xq.jpg");
        */

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    s = data.getStringExtra("path");
                /*
        选择图片直接发送信息
         */
                    if(!(s.equals(""))){

                        img_List.add(s);
                        s="";

                       // adapter.notifyItemInserted(img_List.size() );
                        adapter.notifyItemChanged(img_List.size() - 1,img_List.size());
                        recyclerView.scrollToPosition(img_List.size() );


                    }
                }
        }
    }

    public void cancle_publish(View view){
        this.onBackPressed();

    }

    public void confirm(View view){
        friend_circle_info.setName("陶建辉");
        friend_circle_info.setText(editText.getText().toString());
        friend_circle_info.setImglist(img_List);
       // Log.i("img_list.size",String.valueOf(img_List.size()));
        Intent intent = new Intent();
        intent.putExtra("friend_circle_info",friend_circle_info);
        setResult(RESULT_OK,intent);
        finish();


    }

}
