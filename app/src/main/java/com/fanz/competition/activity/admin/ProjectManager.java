package com.fanz.competition.activity.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.model.Apply;
import com.fanz.competition.model.Project;
import com.fanz.competition.model.Reward;
import com.fanz.competition.network.GetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Fanz on 4/20/15.
 */
public class ProjectManager extends Activity {

    public static int msgWhat = 0x33332;
    private int position = -1;
    private TextView name,profile,date;
    private Button rewards;
    private ListView applyList;
    private Project project;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == msgWhat){
                setAdapter();

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_manager);
        Log.d("fanz","ProjectManager onCreate");
        position = getIntent().getIntExtra("position",-1);
        name = (TextView)findViewById(R.id.project_manager_title);
        profile = (TextView)findViewById(R.id.project_manager_profile);
        date = (TextView)findViewById(R.id.project_manager_date);
        rewards = (Button)findViewById(R.id.project_manager_rewards);
        applyList = (ListView)findViewById(R.id.project_manager_student_list);

        project = RuntimeData.projects.get(position);
        name.setText(project.getName());
        profile.setText(project.getProfile());
        date.setText("开始："+project.getStart_date().toString()+" 结束："+project.getEnd_date().toString());
        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HashMap<String, String>> list = new ArrayList();
                for (Reward reward : RuntimeData.projectRewards) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", reward.getStudent_name());
                    map.put("rank", reward.getRank());
                    list.add(map);
                }
                SimpleAdapter adapter = new SimpleAdapter(ProjectManager.this, list, R.layout.project_reward_dialog_item,
                        new String[]{"name", "rank"}, new int[]{R.id.dialog_item_name, R.id.dialog_item_rank});
                AlertDialog dialog = new AlertDialog.Builder(ProjectManager.this).setAdapter(adapter, null).setPositiveButton("确定", null).setTitle("竞赛结果").create();
                dialog.show();
            }
        });
        GetData.getDataForProject(handler, project.getName());
        applyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Todo 点击参赛者设置奖项

            }
        });
    }

    private void setAdapter(){
        List<HashMap<String,String>> list = new ArrayList<>();
        for(Apply apply : RuntimeData.projectApplys){
            HashMap<String,String> map = new HashMap<>();
            map.put("name",apply.getStudent_name());
            map.put("date",apply.getApply_time().toString());
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.apply_item,
                new String[]{"name","date"},new int[]{R.id.apply_item_name,R.id.apply_item_date});
        applyList.setAdapter(adapter);

    }
}
