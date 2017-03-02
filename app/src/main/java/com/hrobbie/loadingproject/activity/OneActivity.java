package com.hrobbie.loadingproject.activity;

import android.os.Bundle;
import android.os.Handler;

import com.hrobbie.loadingproject.R;

public class OneActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        initData();
    }

    private void initData() {
        setTitle("OneActivity");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadError();
            }
        },2000);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadSuccess();
    }
}
