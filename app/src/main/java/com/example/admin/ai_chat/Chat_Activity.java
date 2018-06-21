package com.example.admin.ai_chat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.Chat_Recyclerview_Adapter;
import Been.FileInfo;
import Been.Msg_Info;
import Util.Get_Bitmap_For_Phone;
import Util.HttpUtils;

public class Chat_Activity extends AppCompatActivity {
    private List<Msg_Info>msg_infoList; //所有的消息列表
    ArrayList<String> img_list;//消息中的图片地址列表
    EditText editText; //输入的数据
    String s = ""; //图片的path
    int flag=0;//指定图片放大器先显示的位置
    RecyclerView recyclerView;
    Chat_Recyclerview_Adapter chat_recyclerview_adapter;

    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //activity专场动画所需
     //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setExitTransition(new Fade().setDuration(500));
        setContentView(R.layout.activity_chat_);
/*
part_immersion
 */
/*

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        msg_infoList = new ArrayList<>();
        img_list = new ArrayList<>();
        editText = (EditText)findViewById(R.id.editText);

         recyclerView = (RecyclerView)findViewById(R.id.id_rec_chat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
         chat_recyclerview_adapter = new Chat_Recyclerview_Adapter(msg_infoList);
        //对item里面的图片的点击事件
        chat_recyclerview_adapter.setItem_inside(new Chat_Recyclerview_Adapter.onclick() {
            @Override
            public void onclick(View view,int position) {
                flag = 0;
                for(int i = 0 ; i < position ; i++){
                    if(msg_infoList.get(i).getText_msg() == null)
                        flag++;

                } //遍历到当前位置之前有flag张图片，传给ViewPager告诉它先显示第flag张（也就是当前点击的那张）
                Log.i("flag",String.valueOf(flag));
                Intent intent = new Intent(Chat_Activity.this,ViewPage_PhotoView.class);
                intent.putStringArrayListExtra("img_list",img_list);
                intent.putExtra("flag",flag+"");
                /*
                共享元素的动画，适合图片的转场
                 */
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        Chat_Activity.this,
                        //通过键值对的形式，确定本Activity的专场时的起点view对象
                        new Pair<View, String>(view,
                                ViewPage_PhotoView.VIEW_NAME_HEADER_IMAGE));
                ActivityCompat.startActivity(Chat_Activity.this, intent, activityOptions.toBundle());

            }
        });
        recyclerView.setAdapter(chat_recyclerview_adapter);



    }


public void choose_img(View v){
  //startActivity(new Intent(this,Choose_Photo.class));
    //overridePendingTransition(R.anim.translate,0);  //动画跳转1


    Intent intent = new Intent(this,Choose_Photo.class);
    startActivityForResult(intent,1);
    overridePendingTransition(R.anim.translate2,0);



//android L 的元素共享动画
//Intent intent = new Intent(this,ViewPage_PhotoView.class);
//    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            Chat_Activity.this,
//            new Pair<View, String>(findViewById(R.id.button2),
//                    ViewPage_PhotoView.VIEW_NAME_HEADER_IMAGE));
//    ActivityCompat.startActivity(Chat_Activity.this, intent, activityOptions.toBundle());


    //动画跳转2  android5.0以上

            //startActivity(new Intent(this,Friend_Circle.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

}

public void hui_tui(View view){
    onBackPressed();
}
public void send(View view){
    String text = editText.getText().toString();
    if(text.equals("")){

    }else {

        final Msg_Info msg_info = new Msg_Info(Msg_Info.TYPE_SENT, text,s);
        msg_infoList.add(msg_info);
        chat_recyclerview_adapter.notifyItemInserted(msg_infoList.size() - 1);
        recyclerView.scrollToPosition(msg_infoList.size() - 1);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Msg_Info msg_info1 = HttpUtils.sendMessage(msg_info.getText_msg());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        msg_infoList.add(msg_info1);
                        chat_recyclerview_adapter.notifyItemInserted(msg_infoList.size() - 1);
                        recyclerView.scrollToPosition(msg_infoList.size() - 1);

                    }
                });

            }
        }).start();

        editText.setText("");
    }
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


                    Msg_Info msg_info = new Msg_Info(Msg_Info.TYPE_SENT,null,s);
                    msg_infoList.add(msg_info);
                    img_list.add(msg_info.getImg_msg());
                    s="";
                    chat_recyclerview_adapter.notifyItemInserted(msg_infoList.size() - 1);
                    recyclerView.scrollToPosition(msg_infoList.size() - 1);

                    Msg_Info msg_info1 = new Msg_Info(Msg_Info.TYPE_RECEIVED,"暂时还看不懂图片呢！",s);
                    msg_infoList.add(msg_info1);
                    chat_recyclerview_adapter.notifyItemInserted(msg_infoList.size() - 1);
                    recyclerView.scrollToPosition(msg_infoList.size() - 1);


                }
            }
    }
}



public void edit_(View view){
    recyclerView.scrollToPosition(msg_infoList.size() -  1);

}

/*
点击EditText之外的地方隐藏输入法
 */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)){

                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                        + v.getWidth()+1000;
                if (event.getX() > left && event.getX() < right
                        && event.getY() > top && event.getY() < bottom) {
                    // 点击EditText的事件，忽略它。
                    return false;
                } else {
                    return true;

            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
