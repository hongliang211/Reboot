package myFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ai_chat.R;

/**
 * Created by admin on 2018/5/24.
 */

public class DrawerLayout_main_1_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drawer_layout_main_1,container,false);
        return view;
    }
}
