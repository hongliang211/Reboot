package myFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.ai_chat.R;

/**
 * Created by admin on 2018/5/23.
 */

public class DrawerLayout_Left_Fragment extends Fragment{
    ImageView imageView_left_background,imageView_left_circle;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drawer_layout_left,container,false);
        imageView_left_background = (ImageView)view.findViewById(R.id.fm_drawerlayout_left_img);
        imageView_left_circle = (ImageView)view.findViewById(R.id.fm_drawerlayout_left_img_circle);
        Glide.with(this).load(R.drawable.drawerlayout_fragment_left_background).into(imageView_left_background);
        Glide.with(this).load(R.drawable.head).into(imageView_left_circle);
        return view;
    }

}
