package com.fanz.competition.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

public class Register extends ActionBarActivity {

    private EditText name;
    private EditText password;
    private EditText phone;
    private EditText clazz;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.register_name);
        password = (EditText)findViewById(R.id.register_pass);
        phone = (EditText)findViewById(R.id.register_phone);
        clazz = (EditText)findViewById(R.id.register_classes);
    }


   public void register(View view) throws JSONException{

       RequestParams params = new RequestParams();
       if(name.getText().toString().isEmpty()||
               password.getText().toString().isEmpty()||
               phone.getText().toString().isEmpty()||
               clazz.getText().toString().isEmpty()){
           Toast.makeText(this,"请输入正确格式",Toast.LENGTH_SHORT).show();
           return ;
       }
       params.addQueryStringParameter("tag",Tag.REGISTER_STUDENT);
       params.addQueryStringParameter("name",name.getText().toString());
       params.addQueryStringParameter("password",password.getText().toString());
       params.addQueryStringParameter("phone",phone.getText().toString());
       params.addQueryStringParameter("clazz",clazz.getText().toString());
       HttpUtils http = new HttpUtils(5000);
       http.send(HttpRequest.HttpMethod.POST,Constants.REQUEST_ENTRY,params,
               new RequestCallBack<String>() {
                   @Override
                   public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                       try {
                           JSONObject jo = new JSONObject(objectResponseInfo.result);
                           if(jo.getBoolean("status")){
                                /*注册成功后跳转到登录界面*/
                               Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                               Register.this.startActivity(new Intent(Register.this,Login.class));
                               Register.this.finish();
                               return;
                           }
                           //提示注册失败（用户名已经存在）
                           Toast.makeText(Register.this,"用户名已经存在",Toast.LENGTH_SHORT).show();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }

                   @Override
                   public void onFailure(HttpException e, String s) {
                       Log.d("fanz",e.getMessage());
                       Toast.makeText(Register.this,"服务器错误",Toast.LENGTH_SHORT).show();
                   }
               });
   }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
