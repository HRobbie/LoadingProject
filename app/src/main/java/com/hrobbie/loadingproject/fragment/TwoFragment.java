package com.hrobbie.loadingproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hrobbie.loadingproject.MainActivity;
import com.hrobbie.loadingproject.R;


public class TwoFragment extends BaseFragment implements View.OnClickListener {
    private Button btn_two;
    private int number=1;
    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;


    private MainActivity mActivity;




    private ImageView iv_loading;
    private RotateAnimation rotateAnimation;

    private LinearLayout ll_error_refresh;

    private FrameLayout fl_content;

    private LinearLayout ll_no_data;
    private LinearLayout ll_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isPrepared=true;
//        mHasLoadedOnce=false;
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        btn_two = (Button) view.findViewById(R.id.btn_two);
        ll_loading= (LinearLayout) view.findViewById(R.id.ll_loading);
        iv_loading= (ImageView) view.findViewById(R.id.iv_loading);
        btn_two.setOnClickListener(this);

        ll_error_refresh= (LinearLayout) view.findViewById(R.id.ll_error_refresh);
        ll_error_refresh.setOnClickListener(this);

        ll_no_data= (LinearLayout) view.findViewById(R.id.ll_no_data);
        fl_content= (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) getActivity();

        showLoading();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared||!isVisible ) {
            //已加载的fragment不需要重新加载
            return;
        }
        if(mHasLoadedOnce){
            loadSuccess();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadSuccess();
                }
            },2000);

            mHasLoadedOnce=true;
        }

    }


    //显示加载动画
    private void showLoading() {
        if(fl_content.getVisibility()!=View.GONE){
            fl_content.setVisibility(View.GONE);//内容消失
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
    private void cancelLoading(){
        if(ll_loading.getVisibility()!=View.GONE){
            ll_loading.setVisibility(View.GONE);
        }
        rotateAnimation.cancel();
    }


    /**
     * 加载成功
     */
    private void loadSuccess(){
        cancelLoading();//取消加载动画
        if(fl_content.getVisibility()!=View.VISIBLE){
            fl_content.setVisibility(View.VISIBLE);//内容展示
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
    private void reLoading(){
        if(fl_content.getVisibility()!=View.GONE){
            fl_content.setVisibility(View.GONE);//内容消失
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
        lazyLoad();//联网请求数据
    }

    /**
     * 加载失败
     */
    private void loadError(){
        cancelLoading();//取消加载动画
        if(fl_content.getVisibility()!=View.GONE){
            fl_content.setVisibility(View.GONE);//内容消失
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
    private void noData(){
        cancelLoading();//取消加载动画
        if(fl_content.getVisibility()!=View.GONE){
            fl_content.setVisibility(View.GONE);//内容消失
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_two:
                number++;
                mActivity.updateTwo(number);
                break;
            case R.id.ll_error_refresh:
                reLoading();
                break;
        }
    }
}
