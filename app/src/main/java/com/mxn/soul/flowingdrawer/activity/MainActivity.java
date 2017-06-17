package com.mxn.soul.flowingdrawer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.adapter.MyItemAdapter;
import com.mxn.soul.flowingdrawer.enity.Order;
import com.mxn.soul.flowingdrawer.enity.User;
import com.mxn.soul.flowingdrawer.util.MenuListFragment;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.BmobDialogButtonListener;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private RecyclerView rvFeed;
    private FlowingDrawer mDrawer;
    private User user = null;
    private TextView tvUserName;
    private MenuListFragment mMenuFragment;
    private ImageView imgAdd;
    private List<Order> mLists = new ArrayList<Order>();
    private MyItemAdapter myItemAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bmob.initialize(getApplication(), "8641a3984f7c4a4e3d19558a69423ecf");
        user = BmobUser.getCurrentUser(this, User.class);
        rvFeed = (RecyclerView) findViewById(R.id.rvFeed);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        query();
        setupToolbar();
        setupFeed();
        setupMenu();
        setListener();
        update();
    }

    /**
     * 这个是进行app的版本的升级
     */
    private void update() {
        //BmobUpdateAgent.setUpdateOnlyWifi(true);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                // TODO Auto-generated method stub
                if (updateStatus == UpdateStatus.Yes) {//版本有更新
                }else if(updateStatus == UpdateStatus.No){
                    //Toast.makeText(MainActivity.this, "版本无更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.EmptyField){//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                    //Toast.makeText(MainActivity.this, "请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.IGNORED){
                    //Toast.makeText(MainActivity.this, "该版本已被忽略更新", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.ErrorSizeFormat){
                    //Toast.makeText(MainActivity.this, "请检查target_size填写的格式，请使用file.length()方法获取apk大小。", Toast.LENGTH_SHORT).show();
                }else if(updateStatus==UpdateStatus.TimeOut){
                    Toast.makeText(MainActivity.this, "查询出错或查询超时", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //发起自动更新
        BmobUpdateAgent.update(this);
        //设置对对话框按钮的点击事件的监听
        BmobUpdateAgent.setDialogListener(new BmobDialogButtonListener() {
            @Override
            public void onClick(int status) {
                // TODO Auto-generated method stub
                switch (status) {
                    case UpdateStatus.Update:
                        Toast.makeText(MainActivity.this, "立即更新" , Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NotNow:
                        Toast.makeText(MainActivity.this, "以后再说" , Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Close:
                        //只有在强制更新状态下才会在更新对话框的右上方出现close按钮,
                        // 如果用户不点击”立即更新“按钮，这时候开发者可做些操作，比如直接退出应用等
                        Toast.makeText(MainActivity.this, "对话框关闭按钮" , Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setListener() {
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    PublishActivity.startAction(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        myItemAdapter.setOnMyClickItemListener(new MyItemAdapter.onMyClickItemListener() {
            @Override
            public void clickItem(View view, int position) {
                Toast.makeText(MainActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = BmobUser.getCurrentUser(this, User.class);
        if (user != null) {
            tvUserName = mMenuFragment.getTvUserName();
            tvUserName.setText(user.getUsername());
            Toast.makeText(MainActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFeed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };

        rvFeed.setLayoutManager(linearLayoutManager);
        /*rvFeed.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));//两列瀑布流*/

        /*rvFeed.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvFeed.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rvFeed.setArrowImageView(R.drawable.iconfont_downgrey);
        rvFeed.setAdapter(myItemAdapter);*/

        /*FeedAdapter feedAdapter = new FeedAdapter(this);
        rvFeed.setAdapter(feedAdapter);
        feedAdapter.updateItems();*/
        myItemAdapter = new MyItemAdapter(mLists,MainActivity.this);
        rvFeed.setAdapter(myItemAdapter);

    }

    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
        mMenuFragment.setOnMenuClick(new MenuListFragment.onMenuClick() {
            @Override
            public void setClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, menuItem.getTitle() + "这是点击事件", Toast.LENGTH_SHORT).show();
                mDrawer.closeMenu();
            }
        });
        mMenuFragment.setOnUserImageClick(new MenuListFragment.onUserImageClick() {
            @Override
            public void imageClick(View view) {
                LoginOrRegister.startAction(MainActivity.this);
                mDrawer.closeMenu();
            }
        });

//        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
//            @Override
//            public void onDrawerStateChange(int oldState, int newState) {
//                if (newState == ElasticDrawer.STATE_CLOSED) {
//                    Log.i("MainActivity", "Drawer STATE_CLOSED");
//                }
//            }
//
//            @Override
//            public void onDrawerSlide(float openRatio, int offsetPixels) {
//                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    public void query() {
        BmobQuery<Order> queryData = new BmobQuery<Order>();
        queryData.setLimit(15);
        queryData.include("user");
        queryData.findObjects(MainActivity.this, new FindListener<Order>() {
            @Override
            public void onSuccess(List<Order> list) {
                if (list != null) {
                    mLists = list;
                    myItemAdapter.updateItems(mLists);
                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void refresh() {
        BmobQuery<Order> queryData = new BmobQuery<Order>();
        queryData.setLimit(15);
        queryData.include("user");
        queryData.findObjects(MainActivity.this, new FindListener<Order>() {
            @Override
            public void onSuccess(List<Order> list) {
                if (list != null) {
                    mLists.clear();
                    mLists.addAll(list);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    myItemAdapter.updateItems(mLists);
                    refreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(MainActivity.this, "网络出错", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
