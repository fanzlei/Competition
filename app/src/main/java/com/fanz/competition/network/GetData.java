package com.fanz.competition.network;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fanz.competition.RuntimeData;
import com.fanz.competition.model.Project;
import com.fanz.competition.model.Reward;
import com.fanz.competition.utils.Constants;
import com.fanz.competition.utils.Tag;
import com.fanz.competition.widget.fragment.MyGradeFragment;
import com.fanz.competition.widget.fragment.ProjectFragment;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fanz on 4/2/15.
 */
public class GetData {

    private static Handler handler;
    public static void getProjectData(Handler handler){
        GetData.handler = handler;
        RuntimeData.projects = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("tag", Tag.GET_PROJECTS);
        HttpUtils http = new HttpUtils(5000);
        http.send(HttpRequest.HttpMethod.POST, Constants.REQUEST_ENTRY,params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                try {
                    String result = new String(objectResponseInfo.result.getBytes(),"utf-8");
                    JSONArray ja = new JSONArray(result);
                    Log.d("get projects:",ja.toString());
                    for(int i = 0;i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        Project project = new Project(
                                jo.getString("name"),
                                jo.getString("profile"),
                                jo.getString("sponsor"),
                                Date.valueOf(jo.getString("start")),
                                Date.valueOf(jo.getString("end"))
                        );
                        RuntimeData.projects.add(project);
                        GetData.handler.sendEmptyMessage(ProjectFragment.msgWhat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }



    public static void getMyRewardData(){
        RuntimeData.myRewards  = new ArrayList<>();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("tag",Tag.GET_MY_REWARDS);
        params.addQueryStringParameter("name",RuntimeData.myAccount_student.getName());
        HttpUtils http = new HttpUtils(5000);
        http.send(HttpRequest.HttpMethod.POST,Constants.REQUEST_ENTRY,params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                if(objectResponseInfo.result.isEmpty()){Log.d("myReward size:","0");return;}
                try {
                    String result = new String(objectResponseInfo.result.getBytes(),"utf-8");
                    JSONArray ja = new JSONArray(result);
                    Log.d("get myReward:",ja.toString());
                    for(int i = 0;i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        Reward reward = new Reward(
                                jo.getString("project_name"),
                                jo.getString("student_name"),
                                jo.getString("rank"),
                                jo.getInt("type")
                        );
                        RuntimeData.myRewards.add(reward);
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
