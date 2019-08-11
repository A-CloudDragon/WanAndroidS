package com.jiyun.wanandroids.ui.home.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jiyun.wanandroids.R;
import com.jiyun.wanandroids.base.BaseActivtiy;
import com.jiyun.wanandroids.base.BaseApp;
import com.jiyun.wanandroids.base.IView;
import com.jiyun.wanandroids.model.entity.HomerPopFriendinfo;
import com.jiyun.wanandroids.model.entity.HomerPopHotkeyinfo;
import com.jiyun.wanandroids.ui.about.fragment.AboutFragment;
import com.jiyun.wanandroids.ui.collection.fragment.CollectionFragment;
import com.jiyun.wanandroids.ui.home.adapter.FriendAadapter;
import com.jiyun.wanandroids.ui.home.adapter.HotkeuyAdapter1;
import com.jiyun.wanandroids.ui.home.adapter.HotkeuyAdapter2;
import com.jiyun.wanandroids.ui.home.login.activity.LoginActivity;
import com.jiyun.wanandroids.ui.home.presenter.HomerPresenter;
import com.jiyun.wanandroids.ui.settin.fragment.SettinFragment;
import com.jiyun.wanandroids.ui.wan.fragment.WanFragment;
import com.jiyun.wanandroids.util.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//HomeActivity是用来和P层关联，从而避免内存泄露的
public class HomeActivity extends BaseActivtiy<HomerPresenter> implements IView {


    private static final int ITEM_DISTANCE = 0;
    @BindView(R.id.home_Toolbar)
    Toolbar homeToolbar;
    @BindView(R.id.home_FrameLayout)
    FrameLayout homeFrameLayout;
    @BindView(R.id.home_NavigationView)
    NavigationView homeNavigationView;
    @BindView(R.id.home_DrawerLayout)
    DrawerLayout homeDrawerLayout;
    @BindView(R.id.home_fab)
    FloatingActionButton homeFab;

    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;

    private int lastFragmentType;
    private ArrayList<HomerPopFriendinfo.DataBean> popFriends;
    private FriendAadapter friendAadapter;
    private HomerPresenter homerPresenter;
    private ArrayList<HomerPopHotkeyinfo.DataBean> hotkeys1;
    private ArrayList<HomerPopHotkeyinfo.DataBean> hotkeys2;
    private HomerPopHotkeyinfo.DataBean dataBean;
    private PopupWindow popupWindowhotkey;
    private PopupWindow popupWindowfriend;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {




        //toolbar设置
        homeToolbar.setTitle("首页");//设置toolbar标题
//        homeToolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(homeToolbar);//绑定
        homeToolbar.setTitleTextColor(getResources().getColor(R.color.colorBlue));


//        //抽屉按钮
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, homeDrawerLayout, homeToolbar, R.string.Open, R.string.Foo);
//        homeDrawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//

        //侧滑动画效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, homeDrawerLayout, homeToolbar, R.string.Open, R.string.Foo) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = homeDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        homeDrawerLayout.addDrawerListener(toggle);





        //获取fragment管理器
        fragmentManager = getSupportFragmentManager();

        //初始化fragment
        initFragment();

        //显示Wan Android
        addWanFragment();

        //点击登录
        TextView tv = homeNavigationView.getHeaderView(0).findViewById(R.id.homer_header_tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BaseApp.getContext(), LoginActivity.class));
            }
        });

        //侧滑中的菜单
        homeNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_item_wan_android:
                        switchFrament(0);
                        homeToolbar.setTitle("首页");//设置toolbar标题
                        break;

                    case R.id.nav_item_my_collect:
                        switchFrament(1);
                        homeToolbar.setTitle("收藏");//设置toolbar标题
                        break;

                    case R.id.nav_item_setting:
                        switchFrament(2);
                        homeToolbar.setTitle("设置");//设置toolbar标题
                        break;

                    case R.id.nav_item_about_us:
                        switchFrament(3);
                        homeToolbar.setTitle("关于我们");//设置toolbar标题
                        break;

                    case R.id.nav_item_logout:
                        switchFrament(4);
                        homeToolbar.setTitle("关于我们");//设置toolbar标题
                        break;
                }
                return true;
            }
        });


        //
        homerPresenter = new HomerPresenter(this);
        homerPresenter.startLoadData();
        homerPresenter.startLoadData2();


        //
        homeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "floatingActionBtn", Snackbar.LENGTH_SHORT)
                        .setAction("action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 滚动至顶部
//                                homeSv.fullScroll(ScrollView.FOCUS_UP);
                            }
                        }).show();
            }
        });

    }


    //显示Toolbar图标
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_meun, menu);
        return true;
    }


    //监听Toolbar图标点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //常用网站
            case R.id.friend:
                //解析一个xml
                View friend = LayoutInflater.from(this).inflate(R.layout.pop_friend, null);
                //创建一个popupwindow
                popupWindowfriend = new PopupWindow(friend, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //设置进出长动画
                popupWindowfriend.setAnimationStyle(R.style.popup_window_animation);

                //设置背景渐变
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 0.5f;
                window.setAttributes(attributes);

                //进行显示
                popupWindowfriend.showAtLocation(homeToolbar, Gravity.TOP, 0, 0);

                //设置背景渐变
                //设置隐藏时的监听器
                popupWindowfriend.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Window window = getWindow();
                        WindowManager.LayoutParams attributes = window.getAttributes();
                        attributes.alpha = 1.0f;
                        window.setAttributes(attributes);
                    }
                });


                //箭头退出的监听点击事件
                ImageView friend_exit = friend.findViewById(R.id.pop_friend_exit);
                friend_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindowfriend.dismiss();
                    }
                });


                //获取常用网站的rlvID
                RecyclerView friend_rlv = friend.findViewById(R.id.pop_friend_rlv);
                //设置布局管理器     为瀑布流
//                friend_rlv.setLayoutManager(new StaggeredGridLayoutManager(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS, StaggeredGridLayoutManager.VERTICAL));

                LinearLayoutManager manager = new LinearLayoutManager(BaseApp.getContext());
//                GridLayoutManager manager = new GridLayoutManager(BaseApp.getContext(),3);
                friend_rlv.setLayoutManager(manager);

                friendAadapter = new FriendAadapter(this, popFriends);
                friend_rlv.setAdapter(friendAadapter);

                friendAadapter.notifyDataSetChanged();


                friendAadapter.setmOnItemClickListener(new FriendAadapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int i) {
                        HomerPopFriendinfo.DataBean dataBean = popFriends.get(i);
                        String link = dataBean.getLink();

                        Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });
                return true;


            //搜索
            case R.id.hotkey:
                //解析一个xml
                View pop_hotkey = LayoutInflater.from(this).inflate(R.layout.pop_hotkey, null);

                //创建一个popupwindow
                popupWindowhotkey = new PopupWindow(pop_hotkey, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//                //设置点击外部可隐藏
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setOutsideTouchable(true);

                //设置进出长动画
                popupWindowhotkey.setAnimationStyle(R.style.popup_window_animation);


                //设置背景渐变
                Window window1 = getWindow();
                WindowManager.LayoutParams attributes1 = window1.getAttributes();
                attributes1.alpha = 0.5f;
                window1.setAttributes(attributes1);


                //进行显示
                popupWindowhotkey.showAtLocation(homeToolbar, Gravity.TOP, 0, 0);

                //设置背景渐变
                //设置隐藏时的监听器
                popupWindowhotkey.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        Window window = getWindow();
                        WindowManager.LayoutParams attributes = window.getAttributes();
                        attributes.alpha = 1.0f;
                        window.setAttributes(attributes);
                    }
                });


                //退出的监听事件
                ImageView hotkey_exit = pop_hotkey.findViewById(R.id.pop_hotkey_exit);
                hotkey_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindowhotkey.dismiss();
                    }
                });


                //获取ID
                SearchView pop_hotkeysearch = pop_hotkey.findViewById(R.id.pop_hotkey_search);
                Button pop_hotkeybutsearch = pop_hotkey.findViewById(R.id.pop_hotkey_but_search);
                TextView pop_hotkeyempty = pop_hotkey.findViewById(R.id.pop_hotkey_empty);
                RecyclerView poprlv1 = pop_hotkey.findViewById(R.id.pop_hotkey_rlv1);
                RecyclerView poprlv2 = pop_hotkey.findViewById(R.id.pop_hotkey_rlv2);

                //设置他们的布局管理器
                poprlv1.setLayoutManager(new StaggeredGridLayoutManager(3, 1));
                poprlv2.setLayoutManager(new LinearLayoutManager(this));

//                //分别为他们设置集合
//                hotkeys1 = new ArrayList<>();
//                hotkeys2 = new ArrayList<>();


                //设置Adapter
                HotkeuyAdapter1 hotkeuyAdapter1 = new HotkeuyAdapter1(this, hotkeys1);
                final HotkeuyAdapter2 hotkeuyAdapter2 = new HotkeuyAdapter2(this, hotkeys2);


                //绑定适配器
                poprlv1.setAdapter(hotkeuyAdapter1);
                poprlv2.setAdapter(hotkeuyAdapter2);


                //刷新适配器
                hotkeuyAdapter1.notifyDataSetChanged();
                hotkeuyAdapter2.notifyDataSetChanged();


                //点击事件
                hotkeuyAdapter1.setmOnItemClickListener(new HotkeuyAdapter1.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int i) {
                        HomerPopHotkeyinfo.DataBean dataBean = hotkeys1.get(i);


                        String link = dataBean.getLink();
                        Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                        intent.putExtra("url", link);
                        startActivity(intent);


                        hotkeys2.add(dataBean);
                        hotkeuyAdapter2.notifyDataSetChanged();


                    }
                });

                //清空的点击事件
                pop_hotkeyempty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hotkeys2.clear();
                        hotkeuyAdapter2.notifyDataSetChanged();
                    }
                });


                //点击事件
                hotkeuyAdapter2.setmOnItemClickListener(new HotkeuyAdapter2.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int i) {
                        HomerPopHotkeyinfo.DataBean dataBean = hotkeys1.get(i);
                        hotkeys2.add(dataBean);
                        hotkeuyAdapter2.notifyDataSetChanged();


                        String link = dataBean.getLink();
                        Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                        intent.putExtra("url", link);
                        startActivity(intent);
                    }
                });


                //搜索框的设置
                pop_hotkeysearch.setIconifiedByDefault(true);
                pop_hotkeysearch.setFocusable(true);
                pop_hotkeysearch.setIconified(false);
                pop_hotkeysearch.requestFocusFromTouch();


                // 设置搜索文本监听
                pop_hotkeysearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    // 当点击搜索按钮时触发该方法
                    @Override
                    public boolean onQueryTextSubmit(String query) {


                        String link = dataBean.getLink();
                        Intent intent = new Intent(BaseApp.getContext(), WebActivity.class);
                        intent.putExtra("url", link);
                        startActivity(intent);

                        hotkeys2.add(dataBean);
                        hotkeuyAdapter2.notifyDataSetChanged();
                        return true;
                    }

                    // 当搜索内容改变时触发该方法
                    @Override
                    public boolean onQueryTextChange(String newText) {
//                        if (!TextUtils.isEmpty(newText)){
                        dataBean = new HomerPopHotkeyinfo.DataBean();
                        dataBean.setName(newText);

//
//                        }else{
//
//                        }
                        return false;
                    }
                });

                pop_hotkeybutsearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected HomerPresenter createPresenter() {
        return new HomerPresenter(this);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    //显示
    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void loadDataHttpSucess(Object o) {

        //分别为他们设置集合
        hotkeys1 = new ArrayList<>();
        hotkeys2 = new ArrayList<>();

        if (o instanceof HomerPopHotkeyinfo) {
            HomerPopHotkeyinfo hpf = (HomerPopHotkeyinfo) o;
            List<HomerPopHotkeyinfo.DataBean> data = hpf.getData();
            hotkeys1.addAll(data);
        }
    }


    public void loadfriendSucess(Object o) {
        popFriends = new ArrayList<>();

        if (o instanceof HomerPopFriendinfo) {
            HomerPopFriendinfo hpf = (HomerPopFriendinfo) o;
            List<HomerPopFriendinfo.DataBean> data = hpf.getData();
            popFriends.addAll(data);

        }
    }


    @Override
    public void loadDataFaile(String errorMsg) {
        showToast(errorMsg);
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void addWanFragment() {
        //显示fragment    Wan页面
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_FrameLayout, fragments.get(0));
        fragmentTransaction.commit();
    }


    private void switchFrament(int type) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragments.get(type);

        //判断fragment是否添加
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.home_FrameLayout, fragment);
        }

        //隐藏上一个fragment
        Fragment lastFragment = fragments.get(this.lastFragmentType);
        fragmentTransaction.hide(lastFragment);

        //显示新的fragment
        fragmentTransaction.show(fragment);
        lastFragmentType = type;
        fragmentTransaction.commit();

        //侧滑菜单被点击后关闭抽屉
        homeDrawerLayout.closeDrawers();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new WanFragment());
        fragments.add(new CollectionFragment());
        fragments.add(new SettinFragment());
        fragments.add(new AboutFragment());
    }

    @Override
    protected int getResLayoutId() {
        return R.layout.activity_home;
    }


    public void onBackPressed() {
        showExitConfirmDialog();
    }

    /**
     * 用户在主界面按返回键，会弹出退出确认框
     */
    private void showExitConfirmDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("确认退出")
//                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .show();


        Snackbar.make(homeNavigationView, "确定要退出吗？", Snackbar.LENGTH_SHORT)
                .setAction("我知道了！", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }


    //view的弹出动画，首先是将view移动至其该所在的位置，其次是大小以及 透明度的变化了
    private AnimatorSet doAnimOpenItem(View view, int index) {
        float distance = index * ITEM_DISTANCE;
        AnimatorSet animatorSet = new AnimatorSet();                         //透明度
        animatorSet.play(                                                   //0
                ObjectAnimator.ofFloat(view, "alpha", 10, 10))
                .with(ObjectAnimator.ofFloat(view, "scaleX", 1, 1))//大小
                .with(ObjectAnimator.ofFloat(view, "scaleY", 1, 1))//大小
                .after(ObjectAnimator.ofFloat(view, "translationY", 520, -390, distance));//动画平移距离
        //设置的延时时间恰好是在前一个动画执行结束前 后一个动画就开始执行，给人一种连贯的感觉
        animatorSet.setStartDelay(index * 1);
        animatorSet.setDuration(2200);//动画执行大小
        return animatorSet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);



    }
}

