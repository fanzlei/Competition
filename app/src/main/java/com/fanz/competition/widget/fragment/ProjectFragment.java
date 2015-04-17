package com.fanz.competition.widget.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.activity.ProjectDetail;
import com.fanz.competition.model.Project;
import com.fanz.competition.network.GetData;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.logging.LogRecord;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import in.srain.cube.mints.base.TitleBaseFragment;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Fanz on 3/16/15.
 */
public class ProjectFragment extends Fragment {

    PtrClassicFrameLayout ptrClassicFrameLayout;
    TextView textView;
    View contentView;
    int text=1;
    View view;
    PullToRefreshView pullToRefreshView;
    ListView listView;
    public static final int msgWhat =  1111;

    public Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgWhat:
                    setAdapter();
                    break;
            }


            super.handleMessage(msg);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView)view.findViewById(R.id.refresh_project_list);
        if(RuntimeData.projects.isEmpty() || RuntimeData.projects == null){
            GetData.getProjectData(handler);
        }else{setAdapter();}
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProjectDetail.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        pullToRefreshView = (PullToRefreshView)view.findViewById(R.id.refresh_project);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                },1000);
                //刷新信息
                GetData.getProjectData(handler);
            }
        });
    }

    public  void setAdapter(){
        List<HashMap<String,Object>> list = new ArrayList<>();
        for(Project project : RuntimeData.projects){
            HashMap<String,Object> map = new HashMap<>();
            map.put("name",project.getName());
            map.put("sponsor",project.getSponsor());
            map.put("start_data",project.getStart_date().toString());
            map.put("end_data",project.getEnd_date().toString());
            map.put("project_type",String.valueOf(project.getProject_type()));
            map.put("profile",project.getProfile());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list,R.layout.project_item,
                new String[]{"name","profile","start_data","end_data"},
                new int[]{R.id.project_item_title,R.id.project_item_profile,R.id.project_item_start,R.id.project_item_end});
        listView.setAdapter(adapter);
        listView.invalidate();
    }

}
