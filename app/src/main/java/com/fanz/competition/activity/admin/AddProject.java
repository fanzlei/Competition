package com.fanz.competition.activity.admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fanz.competition.R;
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

import java.sql.Date;

/**
 * Created by Fanz on 4/21/15.
 */
public class AddProject extends Activity {

    EditText name,profile,sponsor;
    TextView startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_project);
        name = (EditText)findViewById(R.id.add_project_name);
        profile = (EditText)findViewById(R.id.add_project_profile);
        sponsor = (EditText)findViewById(R.id.add_project_sponsor);
        startDate = (TextView)findViewById(R.id.add_project_start_time);
        endDate = (TextView)findViewById(R.id.add_project_end_time);
    }

    public void addProject(View view){
        if(name.getText().toString().isEmpty()||
               profile.getText().toString().isEmpty()||
                sponsor.getText().toString().isEmpty()||
                startDate.getText().toString().isEmpty()||
                endDate.getText().toString().isEmpty()){
            Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
        }else{
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("tag", Tag.ADD_PROJECT);
            params.addQueryStringParameter("name",name.getText().toString());
            params.addQueryStringParameter("profile",profile.getText().toString());
            params.addQueryStringParameter("sponsor",sponsor.getText().toString());
            params.addQueryStringParameter("start_date",startDate.getText().toString());
            params.addQueryStringParameter("end_date",endDate.getText().toString());
            HttpUtils http = new HttpUtils(5000);
            http.send(HttpRequest.HttpMethod.POST, Constants.REQUEST_ENTRY,params,new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                    try {
                        JSONObject jo  = new JSONObject(objectResponseInfo.result);
                        if(jo.getBoolean("status")){
                            Toast.makeText(AddProject.this,"添加成功",Toast.LENGTH_SHORT);
                            startActivity(new Intent(AddProject.this,AdminMain.class));
                        }else{
                            Toast.makeText(AddProject.this,"添加失败",Toast.LENGTH_SHORT);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(AddProject.this,"网络错误",Toast.LENGTH_SHORT);
                }
            });
        }
    }

    public void selectEndDate(View view){
        new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                endDate.setText(new Date(year - 1900, monthOfYear, dayOfMonth).toString());
                Log.d("选择结束日期",new Date(year-1900,monthOfYear,dayOfMonth).toString());
            }
        },2015,05,01).show();
    }

    public void selectStartDate(View view){
        new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate.setText(new Date(year-1900,monthOfYear,dayOfMonth).toString());
                Log.d("选择开始日期",new Date(year-1900,monthOfYear,dayOfMonth).toString());
            }
        },2015,05,01).show();
    }
}
