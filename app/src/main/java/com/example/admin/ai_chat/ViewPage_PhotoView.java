package com.example.admin.ai_chat;

import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ViewPage_PhotoView extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    ViewPager viewPager;
    View mview;
    int flag_click=0;
    TextView textView_chang,textView_total;

    public static String VIEW_NAME_HEADER_IMAGE = "123";
    List<PhotoView> photoViewList;
    ArrayList<String> img_list;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //此activity进入
        getWindow().setEnterTransition(new Fade().setDuration(500));
//此activity退出
       getWindow().setExitTransition(new Fade().setDuration(200));

        setContentView(R.layout.activity_view_page__photo_view);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        photoViewList = new ArrayList<>();
        img_list = new ArrayList<>();
        Intent intent = getIntent();
        img_list = intent.getStringArrayListExtra("img_list");
         String f = intent.getStringExtra("flag");
         flag = Integer.parseInt(f);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        textView_chang = (TextView)findViewById(R.id.textview_change);
        textView_total = (TextView)findViewById(R.id.textview_total);
        mview = viewPager;
        ViewCompat.setTransitionName(mview, VIEW_NAME_HEADER_IMAGE);

        init();//加载图片

        //ViewPager的适配器，至少写一下四个函数
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {//统计数量
                return photoViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;//是否是当前的View
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(photoViewList.get(position));
                //删除view

            }

            @Override
            public Object instantiateItem(ViewGroup container, int pisition) {
                container.addView(photoViewList.get(pisition));
                return photoViewList.get(pisition);
                //添加View
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                mview = (View)object;
                ViewCompat.setTransitionName(mview, VIEW_NAME_HEADER_IMAGE);
            }

            public View getPrimaryItem() {
                return mview;
            }

        };

        viewPager.setAdapter(pagerAdapter);//绑定适配器
        viewPager.setCurrentItem(flag);//指定显示第几张图片
        viewPager.setOnPageChangeListener(ViewPage_PhotoView.this);//添加 PageChangeListener监听事件


        }


        public void init(){
            int size = img_list.size();
            textView_total.setText(String.valueOf(size));
            textView_chang.setText(String.valueOf(flag + 1));


            for(int i = 0 ; i<size;i++) {
                PhotoView photoView = new PhotoView(this);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewPage_PhotoView.this.onBackPressed();
                    }
                });
                Glide.with(this).load(img_list.get(i)).into(photoView);
                photoViewList.add(photoView);


            }
        }

    /*
PageChangListener需要重写的方法
*/
    @Override
    public void onPageSelected(int position) {
        //当前选中的位置
        textView_chang.setText(String.valueOf(position + 1));



    }

    /**
     * 当页面正在滚动时 position 当前选中的是哪个页面 positionOffset 比例 positionOffsetPixels 偏移像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }


    /**
     * 当页面滚动状态改变
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }




    @Override  //沉浸式，去掉状态栏，去掉
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
