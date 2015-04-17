package com.fanz.competition.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;
import com.fanz.competition.network.GetData;
import com.fanz.competition.widget.fragment.AboutFragment;
import com.fanz.competition.widget.fragment.MyCountFragment;
import com.fanz.competition.widget.fragment.MyGradeFragment;
import com.fanz.competition.widget.fragment.ProjectFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.bgaannotation.BGAA;
import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgamenu.BGAMenu;

@BGAALayout(R.layout.activity_main)
public class Main extends FragmentActivity implements AdapterView.OnItemClickListener{

    @BGAAView(R.id.menu)
    BGAMenu menu;
    @BGAAView(R.id.menu_list)
    ListView menuList;

    FragmentManager fragmentManager;
    AboutFragment aboutFrag;
    MyGradeFragment myGradeFrag;
    MyCountFragment myCountFrag;
    ProjectFragment projectFrag;

    private int nowPosition;
    private boolean isReadyExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);
        setMenuList();
        setFragment();
        GetData.getMyRewardData();
    }




    public void toggle(View view){
        menu.toggle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            menu.toggle();
        }
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(!isReadyExit){
                isReadyExit = true;
                Toast.makeText(this,"双击退出！",Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isReadyExit = false;
                    }
                },2000);
            }else{
                this.finish();
            }

        }
        return true;
    }

    /*设置菜单项ListView*/
   private void setMenuList(){
       String[] items = new String[]{
               "竞赛项目",
               "我的成绩",
               "我的账号",
               "关于我们",
       };
       List<Map<String,String>> list = new ArrayList<>();
       for(String item:items){
           Map<String,String> map = new HashMap<>();
           map.put("menu_item_text",item);
           Log.d("fanz",item);
           list.add(map);
       }

       ListAdapter adapter = new SimpleAdapter(this,list,R.layout.menu_item
               ,new String[]{"menu_item_text"},new int[]{R.id.menu_item});
       menuList.setAdapter(adapter);

       menuList.setOnItemClickListener(this);

   }

    public void exit(View view){
        this.finish();
    }

    private void setFragment() {
        aboutFrag=new AboutFragment();
        myCountFrag=new MyCountFragment();
        myGradeFrag=new MyGradeFragment();
        projectFrag = new ProjectFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
        .replace(R.id.fragment_container,projectFrag)
        .commit();

    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("fanz", "the menu item position is : " + position);
        view.setBackgroundColor(Color.BLUE);
        menuList.getChildAt(nowPosition).setBackgroundColor(getResources().getColor(R.color.menu_bg_alpha));
        nowPosition = position;
        switch(position){
            case 0:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,projectFrag).commit();
                menu.toggle();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,myGradeFrag).commit();
                menu.toggle();
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,myCountFrag).commit();
                menu.toggle();
                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,aboutFrag).commit();
                menu.toggle();
                break;
            case 4:

                break;
            case 5:

                break;
            default:
                fragmentManager.beginTransaction().replace(R.id.fragment_container,projectFrag).commit();
                menu.toggle();
        }
    }
}
