package com.fanz.competition.widget.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.model.Reward;
import com.fanz.competition.network.GetData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fanz on 3/16/15.
 */
public class MyGradeFragment extends Fragment {

    private View contentView;
    private ListView myRewardList;
    SimpleAdapter adapter;
    public static int msgWhat = 0x111;

    Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == msgWhat){
                setAdapter();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_mygrade,container,false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRewardList = (ListView)contentView.findViewById(R.id.myreward_list);
        if(!RuntimeData.myRewards.isEmpty()){
            setAdapter();
        }

    }

private void setAdapter(){
    List<HashMap<String,String>> list = new ArrayList<>();
    for(Reward reward : RuntimeData.myRewards){
        HashMap<String,String> map = new HashMap<>();
        map.put("name",reward.getProject_name());
        map.put("rank",reward.getRank());
        list.add(map);
    }

    adapter = new SimpleAdapter(getActivity(),list,R.layout.mygrade_item,
            new String[]{"name","rank"},new int[]{R.id.mygrade_name,R.id.mygrade_rank});
    myRewardList.setAdapter(adapter);
    contentView.invalidate();
}
}
