package com.fanz.competition;

import com.fanz.competition.model.Administor;
import com.fanz.competition.model.Apply;
import com.fanz.competition.model.Project;
import com.fanz.competition.model.Reward;
import com.fanz.competition.model.Student;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fanz on 4/2/15.
 */
public class RuntimeData {

    public static List<Project> projects = new ArrayList<>();
    public static List<Reward> myRewards = new ArrayList<>();
    public static List<Reward> projectRewards = new ArrayList<>();
    public static List<Apply> projectApplys = new ArrayList<>();
    public static Student myAccount_student = new Student();
    public static Administor myAccount_admin = new Administor();
    public static boolean isLoginStudent = true;


    /*---------模拟数据------------*/
    public static void setProjects(){
        Project project = new Project("书法竞赛","为了学习传统文化，弘扬书法","电信学院",new Date(2015,04,10),new Date(2015,05,10));
        Project project2 = new Project("数学建模竞赛","提供学生数学兴趣","理学院",new Date(2015,05,10),new Date(2015,05,20));
        projects = new ArrayList<>();
        projects.add(project);
        projects.add(project2);
    }
    public static void setMyAccount_student(){
        myAccount_student = new Student(12221,"李白","libai1234","02011238890","libai@gmail.com");
    }

    public static void setMyRewards(){
        myRewards = new ArrayList<>();
        Reward reward = new Reward("书法竞赛","李白","二等奖",2);
        Reward reward2 = new Reward("数学建模竞赛","李白","优秀奖",1);
        myRewards.add(reward);
        myRewards.add(reward2);
    }

    public static void setMyAccount_admin(){
        myAccount_admin = new Administor(1212,"谢盛","xieshen1111","13234528944","xieshen@112.com");
    }
    /*---------模拟数据------------*/
}
