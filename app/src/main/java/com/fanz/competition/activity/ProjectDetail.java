package com.fanz.competition.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.model.Project;
import com.fanz.competition.utils.Constants;
import com.fanz.competition.utils.Tag;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Fanz on 4/16/15.
 */
public class ProjectDetail extends Activity {


    TextView name,profile,start,end;
    int position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_detail);
        end = (TextView)findViewById(R.id.project_detail_end);
        start = (TextView)findViewById(R.id.project_detail_start);
        name = (TextView)findViewById(R.id.project_detail_title);
        profile = (TextView)findViewById(R.id.project_detail_profile);


        position = getIntent().getIntExtra("position",-1);
        if(position == -1){throw new RuntimeException("position == -1 error");}else {
            Project project = RuntimeData.projects.get(position);
            name.setText(project.getName());
            profile.setText(project.getProfile());
            start.setText(project.getStart_date().toString());
            end.setText(project.getEnd_date().toString());

        }
    }

    public void apply(View view){
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("tag", Tag.JOIN_PROJECT);
        params.addQueryStringParameter("student_name",RuntimeData.myAccount_student.getName());
        params.addQueryStringParameter("project_name",name.getText().toString());
        HttpUtils http = new HttpUtils(5000);
        http.send(HttpRequest.HttpMethod.POST, Constants.REQUEST_ENTRY,params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                try {
                    if(objectResponseInfo.result.isEmpty()){return;}
                    String result = new String(objectResponseInfo.result.getBytes(),"utf-8");
                    JSONObject jo = new JSONObject(result);
                    if(jo.getBoolean("status")){
                        Toast.makeText(ProjectDetail.this,"报名成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProjectDetail.this,Main.class));
                    }else{
                        Toast.makeText(ProjectDetail.this,"报名失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }
}
