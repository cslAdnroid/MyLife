package com.mxn.soul.flowingdrawer.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.enity.Order;
import com.mxn.soul.flowingdrawer.enity.User;
import com.mxn.soul.flowingdrawer.util.ClearEditText;
import com.mxn.soul.flowingdrawer.util.CustomProgressDialog;
import com.mxn.soul.flowingdrawer.util.TitleBarView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 这个是发布的
 * Created by mk on 2017/6/1.
 */

public class PublishActivity extends AppCompatActivity {

    @BindView(R.id.titlePublish)
    TitleBarView titlePublish;
    @BindView(R.id.etUserName)
    ClearEditText etUserName;
    @BindView(R.id.etShopper)
    ClearEditText etShopper;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.lyGetTime)
    LinearLayout lyGetTime;
    @BindView(R.id.etMoney)
    ClearEditText etMoney;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.btnSend)
    Button btnSend;
    private User user;
    private Calendar mCalendar;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        user = BmobUser.getCurrentUser(PublishActivity.this,User.class);
        etUserName.setText(user.getUsername());
        etShopper.setText(user.getUsername());
        initTitle();
    }

    private void initTitle() {
        titlePublish.initTitleWithTXTDrawable("消费信息发布", "返回", R.drawable.btn_back_sel, 5, "");
        titlePublish.setOnLeftTxtClickListener(new TitleBarView.OnLeftTxtClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context,PublishActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.lyGetTime, R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lyGetTime:
                mCalendar = Calendar.getInstance();//获取当前系统时间
                DatePickerDialog datePickerDialog = new DatePickerDialog(PublishActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        time = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        tvTime.setText(time);
                    }
                },mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DATE));
                datePickerDialog.setTitle("选择日期");
                datePickerDialog.show();
                break;
            case R.id.btnSend:
                if (etContent.getText().toString() == null){
                    Toast.makeText(PublishActivity.this,"请输入消费事件",Toast.LENGTH_SHORT).show();
                }else if (time == null){
                    Toast.makeText(PublishActivity.this,"请选择时间",Toast.LENGTH_SHORT).show();
                }else if (etMoney.getText().toString() == null){
                    Toast.makeText(PublishActivity.this,"请输入消费金额",Toast.LENGTH_SHORT).show();
                }else {
                    Order order = new Order();
                    order.setContent(etContent.getText().toString());
                    order.setShopper(etShopper.getText().toString());
                    order.setTime(tvTime.getText().toString());
                    order.setUser(user);
                    order.setMoney(Double.parseDouble(etMoney.getText().toString()));
                    final CustomProgressDialog progressDialog = new CustomProgressDialog(PublishActivity.this,"网络君正在奔跑..", R.drawable.myprogressframe);
                    progressDialog.show();
                    order.save(PublishActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(PublishActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(PublishActivity.this,"添加失败"+s,Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
        }
    }
}
