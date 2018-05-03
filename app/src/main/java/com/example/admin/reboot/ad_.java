package com.example.admin.reboot;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ad_ extends AppCompatActivity implements Animation.AnimationListener{
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_);

        imageView = (ImageView)findViewById(R.id.ad);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha);
        animation.setAnimationListener(this);
        imageView.startAnimation(animation);

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });
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

    @Override
    public void onAnimationStart(Animation animation) {

    }//动画开始时的动作

        @Override
        public void onAnimationEnd(Animation animation) {
            //当动画结束的时候跳转
            startActivity(new Intent(ad_.this, activity_chat.class));
            finish();
        }//动画结束时的动作

        @Override
        public void onAnimationRepeat(Animation animation) {

        }//动画重复时的动作
}
