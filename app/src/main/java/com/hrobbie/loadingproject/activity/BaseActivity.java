package com.hrobbie.loadingproject.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hrobbie.loadingproject.R;

public class BaseActivity extends AppCompatActivity {

    private ImageView iv_loading;
    private RotateAnimation rotateAnimation;

    private LinearLayout ll_error_refresh;


    private LinearLayout ll_no_data;
    private LinearLayout ll_loading;

    private Toolbar tool_bar;

    private FrameLayout fl_content;
    private View addView;
    private FrameLayout parentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
       View rootView = LayoutInflater.from(this).inflate(R.layout.activity_base,null,false);
        addView = LayoutInflater.from(this).inflate(layoutResID, null, false);


        //content
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView.setLayoutParams(params);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        fl_content.addView(addView);
        getWindow().setContentView(rootView);

        initView();
        showLoading();
    }



    protected void setToolBar(){
        setSupportActionBar(tool_bar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        iv_loading= (ImageView) findViewById(R.id.iv_loading);
        ll_error_refresh = (LinearLayout)findViewById(R.id.ll_error_refresh);
        ll_no_data = (LinearLayout)findViewById(R.id.ll_no_data);
        ll_loading = (LinearLayout)findViewById(R.id.ll_loading);
        tool_bar = (Toolbar) findViewById(R.id.tool_bar);
//        fl_content = (FrameLayout)findViewById(R.id.fl_content);

        ll_error_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reLoading();
                onRefresh();
            }
        });

        setToolBar();
        showLoading();
    }

    public void setTitle(String text){
        tool_bar.setTitleTextColor(getResources().getColor(R.color.white_1));
        tool_bar.setTitle(text);
    }

    //显示加载动画
    protected void showLoading() {
        if(addView.getVisibility()!=View.GONE){
            addView.setVisibility(View.GONE);//内容消失
        }
        if(ll_loading.getVisibility()!=View.VISIBLE){
            ll_loading.setVisibility(View.VISIBLE);//加载出现
        }
        if(ll_error_refresh.getVisibility()!=View.GONE){
            ll_error_refresh.setVisibility(View.GONE);//加载错误布局消失
        }
        if(ll_no_data.getVisibility()!=View.GONE){
            ll_no_data.setVisibility(View.GONE);//没有数据布局消失
        }
        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);//设置动画持续时间
        rotateAnimation.setInterpolator(new LinearInterpolator());//不停顿
        rotateAnimation.setRepeatCount(10);
        iv_loading.setAnimation(rotateAnimation);
        rotateAnimation.startNow();
    }
    //取消动画
    protected void cancelLoading(){
        if(ll_loading.getVisibility()!=View.GONE){
            ll_loading.setVisibility(View.GONE);
        }
        rotateAnimation.cancel();
    }


    /**
     * 加载成功
     */
    protected void loadSuccess(){
        cancelLoading();//取消加载动画
        if(addView.getVisibility()!=View.VISIBLE){
            addView.setVisibility(View.VISIBLE);//内容展示
        }
        if(ll_loading.getVisibility()!=View.GONE){
            ll_loading.setVisibility(View.GONE);//加载消失
        }
        if(ll_error_refresh.getVisibility()!=View.GONE){

            ll_error_refresh.setVisibility(View.GONE);//加载错误布局消失
        }
        if(ll_no_data.getVisibility()!=View.GONE){
            ll_no_data.setVisibility(View.GONE);//没有数据布局消失
        }
    }
    /**
     * 再次加载数据
     */
    protected void reLoading(){
        if(addView.getVisibility()!=View.GONE){
            addView.setVisibility(View.GONE);//内容消失
        }
        if(ll_loading.getVisibility()!=View.VISIBLE){
            ll_loading.setVisibility(View.VISIBLE);//加载出现
        }
        if(ll_error_refresh.getVisibility()!=View.GONE){
            ll_error_refresh.setVisibility(View.GONE);//加载错误布局消失
        }
        if(ll_no_data.getVisibility()!=View.GONE){
            ll_no_data.setVisibility(View.GONE);//没有数据布局消失
        }
        rotateAnimation.startNow();//展示加载动画
//        lazyLoad();//联网请求数据
    }

    /**
     * 加载失败
     */
    protected void loadError(){
        cancelLoading();//取消加载动画
        if(addView.getVisibility()!=View.GONE){
            addView.setVisibility(View.GONE);//内容消失
        }
        if(ll_loading.getVisibility()!=View.GONE){
            ll_loading.setVisibility(View.GONE);//加载消失
        }
        if(ll_error_refresh.getVisibility()!=View.VISIBLE){
            ll_error_refresh.setVisibility(View.VISIBLE);//加载错误布局出现
        }
        if(ll_no_data.getVisibility()!=View.GONE){
            ll_no_data.setVisibility(View.GONE);//没有数据布局消失
        }

    }

    /**
     * 没有数据
     */
    protected void noData(){
        cancelLoading();//取消加载动画
        if(addView.getVisibility()!=View.GONE){
            addView.setVisibility(View.GONE);//内容消失
        }
        if(ll_loading.getVisibility()!=View.GONE){
            ll_loading.setVisibility(View.GONE);//加载消失
        }
        if(ll_error_refresh.getVisibility()!=View.GONE){
            ll_error_refresh.setVisibility(View.GONE);//加载错误布局消失
        }
        if(ll_no_data.getVisibility()!=View.VISIBLE){
            ll_no_data.setVisibility(View.VISIBLE);//没有数据布局出现
        }
    }


    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }
}
