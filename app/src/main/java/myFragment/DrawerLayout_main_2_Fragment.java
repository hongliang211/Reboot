package myFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.admin.ai_chat.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.Friend_Recyclerview_Adapter;
import Been.Friend_Info;
import ItemDecoration.friend_itemDecoration;

/**
 * Created by admin on 2018/5/24.
 */

public class DrawerLayout_main_2_Fragment extends Fragment {
    List<Friend_Info>friend_infoList;
    RecyclerView recyclerView;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drawer_layout_main_2,container,false);
        context = view.getContext();
        friend_infoList = new ArrayList<>();

        for (int i = 0 ; i<10; i++){
            Friend_Info friend_info = new Friend_Info("A","小花");
            friend_infoList.add(friend_info);

             friend_info = new Friend_Info("B","小白");
            friend_infoList.add(friend_info);

             friend_info = new Friend_Info("C","小红");
            friend_infoList.add(friend_info);
        }
        recyclerView = (RecyclerView)view.findViewById(R.id.rec_friend);





        return view;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Friend_Recyclerview_Adapter adapter = new Friend_Recyclerview_Adapter(friend_infoList);
        recyclerView.addItemDecoration(new friend_itemDecoration(context,friend_infoList));
        recyclerView.setAdapter(adapter);
    }


}
