package com.hrobbie.loadingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrobbie.loadingproject.activity.OneActivity;
import com.hrobbie.loadingproject.adapter.MyFragmentPagerAdapter;
import com.hrobbie.loadingproject.fragment.FourFragment;
import com.hrobbie.loadingproject.fragment.OneFragment;
import com.hrobbie.loadingproject.fragment.ThreeFragment;
import com.hrobbie.loadingproject.fragment.TwoFragment;
import com.hrobbie.loadingproject.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;

    private RelativeLayout rl_1;
    private RelativeLayout rl_2;
    private RelativeLayout rl_3;
    private RelativeLayout rl_4;

    private RadioButton rb_1;
    private RadioButton rb_2;
    private RadioButton rb_3;
    private RadioButton rb_4;

    private ViewPager vp_main;
    private List<Fragment> mFragments;


    /**
     * -1:表示没有新消息
     * -2:表示新消息用红点的方式显示
     * 0-99：直接显示数字
     * >=100:用99+显示
     */
    private int messageNum=-1;//表示没有新消息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initData();
        initView();
    }
    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new OneFragment());
        mFragments.add(new TwoFragment());
        mFragments.add(new ThreeFragment());
        mFragments.add(new FourFragment());



    }
    private void initView() {
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_2 = (TextView)findViewById(R.id.tv_2);
        tv_3 = (TextView)findViewById(R.id.tv_3);
        tv_4 = (TextView)findViewById(R.id.tv_4);

        rl_1 = (RelativeLayout)findViewById(R.id.rl_1);
        rl_2 = (RelativeLayout)findViewById(R.id.rl_2);
        rl_3 = (RelativeLayout)findViewById(R.id.rl_3);
        rl_4 = (RelativeLayout)findViewById(R.id.rl_4);

        rb_1 = (RadioButton)findViewById(R.id.rb_1);
        rb_2 = (RadioButton)findViewById(R.id.rb_2);
        rb_3 = (RadioButton)findViewById(R.id.rb_3);
        rb_4 = (RadioButton)findViewById(R.id.rb_4);

        vp_main = (ViewPager)findViewById(R.id.vp_main);


        rl_1.setOnClickListener(this);
        rl_2.setOnClickListener(this);
        rl_3.setOnClickListener(this);
        rl_4.setOnClickListener(this);


        vp_main.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments));
        vp_main.addOnPageChangeListener(this);

        vp_main.setCurrentItem(1);
        //这个是缓存fragment的个数
        vp_main.setOffscreenPageLimit(3);//最好是一屏能显示的fragment数-1。

        //消息提示形状
        messageTips(100,tv_4);//>=100:用99+显示
        messageTips(-1,tv_1);//-1:表示没有新消息
        messageTips(-2,tv_2);//-2:表示新消息用红点的方式显示
        messageTips(5,tv_3);//0-99：直接显示数字
    }

    private void messageTips(int num, TextView tv) {
        if(num==-1){
           tv.setVisibility(View.GONE);
        }else if(num==-2){
            tv.setVisibility(View.VISIBLE);
            tv.setText("");
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            layoutParams.height= DensityUtil.dip2px(this,10);
            layoutParams.width= DensityUtil.dip2px(this,10);
            tv.setLayoutParams(layoutParams);
        }else if(num>=0&&num<=99){
            tv.setVisibility(View.VISIBLE);
            tv.setText(num+"");
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            layoutParams.height= DensityUtil.dip2px(this,16);
            layoutParams.width= DensityUtil.dip2px(this,16);
            tv.setLayoutParams(layoutParams);
        }else if(num>=100){
            tv.setVisibility(View.VISIBLE);
            tv.setText("99+");
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
            layoutParams.height= DensityUtil.dip2px(this,16);
            layoutParams.width= DensityUtil.dip2px(this,16);
            tv.setTextSize(DensityUtil.sp2px(this,3));
            tv.setLayoutParams(layoutParams);
        }else{
            tv.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            startActivity(new Intent(MainActivity.this, OneActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_1:
                changeRadioButton(0);
                vp_main.setCurrentItem(0,false);
                break;
            case R.id.rl_2:
                changeRadioButton(1);
                vp_main.setCurrentItem(1,false);
                break;
            case R.id.rl_3:
                changeRadioButton(2);
                vp_main.setCurrentItem(2,false);
                break;
            case R.id.rl_4:
                changeRadioButton(3);
                vp_main.setCurrentItem(3,false);
                break;
        }
    }

    /**
     * 根据index更改radioButton的选中状态
     * @param index
     */
    private void changeRadioButton(int index){

        switch (index){
            case 0:
                rb_1.setChecked(true);
                rb_2.setChecked(false);
                rb_3.setChecked(false);
                rb_4.setChecked(false);

                break;
            case 1:
                rb_1.setChecked(false);
                rb_2.setChecked(true);
                rb_3.setChecked(false);
                rb_4.setChecked(false);
                break;
            case 2:
                rb_1.setChecked(false);
                rb_2.setChecked(false);
                rb_3.setChecked(true);
                rb_4.setChecked(false);
                break;
            case 3:
                rb_1.setChecked(false);
                rb_2.setChecked(false);
                rb_3.setChecked(false);
                rb_4.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * viewpager切换时，更改radioButton的点击状态
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        changeRadioButton(position);
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 在oneFragment中更新，底部导航栏的数字
     * @param num
     */
    public void updateOne(int num){
        messageTips(num,tv_1);
    }
    /**
     * 在TwoFragment中更新，底部导航栏的数字
     * @param num
     */
    public void updateTwo(int num){
        messageTips(num,tv_2);
    }
    /**
     * 在ThreeFragment中更新，底部导航栏的数字
     * @param num
     */
    public void updateThree(int num){
        messageTips(num,tv_3);
    }
    /**
     * 在FourFragment中更新，底部导航栏的数字
     * @param num
     */
    public void updateFour(int num){
        messageTips(num,tv_4);
    }
}
