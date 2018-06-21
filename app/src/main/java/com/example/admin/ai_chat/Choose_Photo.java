package com.example.admin.ai_chat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.Choose_Photo_Recyclerview_Adapter;
import Been.Choose_Photo_Info;
import Been.FileInfo;
import Util.Get_Bitmap_For_Phone;

public class Choose_Photo extends AppCompatActivity {
    List<Choose_Photo_Info>choose_photo_infoList = new ArrayList<>();;

    String path;

    TextView textView;

    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //此activity进入
        getWindow().setEnterTransition(new Fade().setDuration(500));
//此activity退出
        getWindow().setExitTransition(new Fade().setDuration(200));
*/
        setContentView(R.layout.activity_choose__photo);



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

        textView = (TextView)findViewById(R.id.textView4);


        init();





    }


    private void init(){


        Get_Bitmap_For_Phone.get_bitmap(this, new Get_Bitmap_For_Phone.GetBitmapListener() {
            @Override
            public void onSucceed(List<FileInfo> fileInfoList) {
                showUi(fileInfoList);

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();

            }
        });


    }

    public void showUi(final List<FileInfo> fileInfoList){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(Choose_Photo.this,String.valueOf(fileInfoList.size()),Toast.LENGTH_LONG).show();
                int size = fileInfoList.size();
                for(int i = 0 ; i< size ; i++){

                        Choose_Photo_Info choose_photo_info = new Choose_Photo_Info(fileInfoList.get(i).getBitmap());

                        choose_photo_infoList.add(choose_photo_info);

                }

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recy_choose_photo);
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                        (4,StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                Choose_Photo_Recyclerview_Adapter choose_photo_recyclerview_adapter
                        = new Choose_Photo_Recyclerview_Adapter(choose_photo_infoList);
                recyclerView.setAdapter(choose_photo_recyclerview_adapter);

                choose_photo_recyclerview_adapter.setOnItemClickListener(new Choose_Photo_Recyclerview_Adapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int position) {
                         path = fileInfoList.get(position).getPath();
                        textView.setTextColor(Color.GREEN);
                        flag = 1;


                    }
                });
            }
        });

    }

    public void cancle(View V){
        Intent intent = new Intent();
        intent.putExtra("path","");
        setResult(RESULT_OK,intent);
        finish();
        overridePendingTransition(0,R.anim.translate);
    }

    public void click_true(View v){
        if(flag == 1) {
            Intent intent = new Intent(this, Chat_Activity.class);
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(0, R.anim.translate);
        }
    }
}
