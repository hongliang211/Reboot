package com.example.admin.ai_chat;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import myFragment.DrawerLayout_main_2_Fragment;

public class Home_Page extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ImageView imageView_head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.hide();
            }
        }

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        imageView_head = (ImageView)findViewById(R.id.head);


        Glide.with(this).load(R.drawable.head).into(imageView_head);






    }

    public void tou_xiang(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frgament,fragment);
        fragmentTransaction.commit();
    }

    public void friend(View view){
        replaceFragment(new DrawerLayout_main_2_Fragment());

    }

    public void firend_circle(View view){
        startActivity(new Intent(this,Friend_Circle.class),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

    }

    public void chat(View view){
        startActivity(new Intent(this,Chat_Activity.class),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
