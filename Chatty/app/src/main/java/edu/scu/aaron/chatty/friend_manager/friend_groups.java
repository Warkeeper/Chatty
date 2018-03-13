package edu.scu.aaron.chatty.friend_manager;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.scu.aaron.chatty.R;

/**
 * 日期：2018.3.12
 * 作者：yrp
 * 内容：好友管理界面
 *
 * 添加的好友默认在我的好友的组里
 显示好友头像、名称、备注
 点击好友头像进入好友信息的界面（管理分组和备注）
 点击好友名称进入对话界面
 */

public class friend_groups extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView mrecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
//    friend_groups_bar_bean mfriend;

    private List<friend_groups_bar_bean> friend01 = new ArrayList<friend_groups_bar_bean>();

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_groups);

        initData();
        addFriend01();
       //        设置刷新组件的监听器
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initrecycleView();
    }

    //         添加信息
    public void addFriend01(){
        friend01.add(new friend_groups_bar_bean(R.drawable.doghead, "CPT"));
        friend01.add(new friend_groups_bar_bean(R.drawable.doghead, "btn11"));
        friend01.add(new friend_groups_bar_bean(R.drawable.doghead, "btn11"));
        friend01.add(new friend_groups_bar_bean(R.drawable.doghead, "btn11"));
    }

    //初始化swiperRefreshLayout01\RecycleView01
    public void initData() {
        mSwipeRefreshLayout = findViewById(R.id.SwipeRfL1);
        mrecyclerView = findViewById(R.id.friend_groups_rv1);
    }


    //重写刷新的方法,2s后刷新的组件消失
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
    //设置recycleView
    public void initrecycleView(){
        //        设置RecycleView的manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//        绑定RecycleView的manager和Adapter
        mrecyclerView.setLayoutManager(manager);
//        设置rv1的Adapter
        friend_groups_rv1Adapter adapter = new friend_groups_rv1Adapter(R.layout.friend_groupbar, friend01);
        adapter.openLoadAnimation();
        mrecyclerView.setAdapter(adapter);
    }
}
