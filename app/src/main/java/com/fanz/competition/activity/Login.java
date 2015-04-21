package com.fanz.competition.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.activity.admin.AdminMain;
import com.fanz.competition.activity.student.Main;
import com.fanz.competition.model.Administor;
import com.fanz.competition.model.Student;
import com.fanz.competition.utils.Constants;
import com.fanz.competition.R;
import com.fanz.competition.utils.SharedPref;
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


public class Login extends Activity {


    RequestQueue requestQueue;
    EditText name;
    EditText password;
    CheckBox checkBox;
    RadioButton loginStudent;
    RadioButton loginAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.login_user_name);
        password = (EditText)findViewById(R.id.login_password);
        checkBox = (CheckBox)findViewById(R.id.login_checkbox);
        loginStudent = (RadioButton)findViewById(R.id.login_student);
        loginAdmin = (RadioButton)findViewById(R.id.login_admin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocalUser();

    }

    public void register(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);

    }

    public void login(View view) {

        RequestParams params = new RequestParams();
        //输入非法判断
        if(!checkInput()){
            return;
        }
            params.addQueryStringParameter("name",name.getText().toString());
            params.addQueryStringParameter("password",password.getText().toString());
            if(loginStudent.isChecked()){
                params.addQueryStringParameter("tag",Tag.LOGIN_STUDENT);
                RuntimeData.isLoginStudent = true;
            }else{params.addQueryStringParameter("tag",Tag.LOGIN_ADMIN);RuntimeData.isLoginStudent = false;}
        HttpUtils http = new HttpUtils(5000);
        http.send(HttpRequest.HttpMethod.POST,Constants.REQUEST_ENTRY,params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                try {
                    String result = new String(objectResponseInfo.result.getBytes(),"utf-8");
                    Log.d("result = ",result);
                    JSONObject jo = new JSONObject(result);
                    if(jo.getBoolean("status")){
                        //登陆成功保存个人信息并跳转到主界面
                        saveUser(jo);
                        if(loginStudent.isChecked()){
                            Intent intent1 = new Intent(Login.this,Main.class);
                            startActivity(intent1);
                        }else{
                            Intent intent = new Intent(Login.this,AdminMain.class);
                            startActivity(intent);
                        }

                        finish();
                    }else{
                        //登陆失败处理
                        Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        password.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d("fanz",e.getMessage());
                Toast.makeText(Login.this,"服务器错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInput(){
        if(name.getText().toString().trim().isEmpty()
                ||password.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"用户名或密码为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        saveUserToSharedPreferences();
        return true;
    }

    //保存用户信息到数据类
    private void saveUser(JSONObject jsonObject) throws JSONException{

        if(loginStudent.isChecked()){
            Student student = new Student(
                    -1,
                    jsonObject.getString("name"),
                    jsonObject.getString("password"),
                    jsonObject.getString("phone"),
                    jsonObject.getString("email")
            );
            RuntimeData.myAccount_student = student;
        }else{
            Administor admin = new Administor(
                    -1,
                    jsonObject.getString("name"),
                    jsonObject.getString("password"),
                    jsonObject.getString("phone"),
                    jsonObject.getString("email")
            );
            RuntimeData.myAccount_admin = admin;
        }

    }

    //保存用户登陆信息到本地
    private void saveUserToSharedPreferences(){

        if(checkBox.isChecked()){

            SharedPreferences.Editor editor = SharedPref.getInstance(this).editor;
            editor.putString("name",name.getText().toString().trim());
            editor.putString("password",password.getText().toString().trim());
        }
    }

    /*创建界面时读取本地保存的用户信息*/
    private void getLocalUser(){
        /*初始化SharedPreferences*/
        SharedPref.getInstance(getApplicationContext());
        name.setText(SharedPref.getInstance(this).sp.getString("name",""));
        password.setText(SharedPref.getInstance(this).sp.getString("password",""));
    }
}
