package com.fanz.competition.widget.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanz.competition.R;
import com.fanz.competition.RuntimeData;

/**
 * Created by Fanz on 3/16/15.
 */
public class MyCountFragment extends Fragment {

    private View contentView;
    private TextView name;
    private TextView phone;
    private TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_mycount,container,false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = (TextView)contentView.findViewById(R.id.mycount_name);
        phone = (TextView)contentView.findViewById(R.id.mycount_phone);
        email = (TextView)contentView.findViewById(R.id.mycount_email);

        if(RuntimeData.isLoginStudent){
            name.setText(RuntimeData.myAccount_student.getName());
            phone.setText(RuntimeData.myAccount_student.getPhone());
            email.setText(RuntimeData.myAccount_student.getEmail());
        }else{
            name.setText(RuntimeData.myAccount_admin.getName());
            phone.setText(RuntimeData.myAccount_admin.getPhone());
            email.setText(RuntimeData.myAccount_admin.getEmail());
        }


    }
}
