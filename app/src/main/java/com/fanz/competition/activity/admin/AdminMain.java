package com.fanz.competition.activity.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.model.Project;
import com.fanz.competition.network.GetData;
import com.fanz.competition.widget.fragment.ProjectFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fanz on 4/17/15.
 */
public class AdminMain extends Activity {

    private ListView projectList;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == ProjectFragment.msgWhat){
                setAdapter();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
        projectList = (ListView)findViewById(R.id.project_list_admin);
        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminMain.this,ProjectManager.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        GetData.getProjectData(handler);
    }

    private void setAdapter(){
        List<HashMap<String,Object>> list = new ArrayList<>();
        for(Project project : RuntimeData.projects){
            HashMap<String,Object> map = new HashMap<>();
            map.put("name",project.getName());
            map.put("sponsor",project.getSponsor());
            map.put("start_data","开始："+project.getStart_date().toString());
            map.put("end_data","结束"+project.getEnd_date().toString());
            map.put("project_type",String.valueOf(project.getProject_type()));
            map.put("profile",project.getProfile());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.project_item,
                new String[]{"name","profile","start_data","end_data"},
                new int[]{R.id.project_item_title,R.id.project_item_profile,R.id.project_item_start,R.id.project_item_end});
        projectList.setAdapter(adapter);
        projectList.invalidate();
    }

    public void addProject(View view){

        startActivity(new Intent(this,AddProject.class));
    }
}
