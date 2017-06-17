package com.mxn.soul.flowingdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer.R;
import com.mxn.soul.flowingdrawer.enity.User;
import com.mxn.soul.flowingdrawer.util.ClearEditText;
import com.mxn.soul.flowingdrawer.util.CustomProgressDialog;
import com.mxn.soul.flowingdrawer.util.TitleBarView;

import cn.bmob.v3.listener.SaveListener;

/**
 * 这个是登录或者注册的
 * Created by mk on 2017/5/31.
 */

public class LoginOrRegister extends AppCompatActivity{
    TitleBarView titleBarView;
    ClearEditText clearEditTextName;
    ClearEditText clearEditTextPassword;
    ClearEditText clearEditTextPhone;
    ClearEditText clearEditTextNewUserName;
    ClearEditText clearEditTextNewPassword;
    LinearLayout lyLogin,lyRegister;
    Button btnLoginOrReg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        titleBarView = (TitleBarView) findViewById(R.id.titleBarView);
        clearEditTextName = (ClearEditText) findViewById(R.id.userName);
        clearEditTextPassword = (ClearEditText) findViewById(R.id.password);
        clearEditTextPhone = (ClearEditText) findViewById(R.id.Phone);
        clearEditTextNewPassword = (ClearEditText) findViewById(R.id.newPassword1);
        clearEditTextNewUserName = (ClearEditText) findViewById(R.id.newUserName);
        lyLogin = (LinearLayout) findViewById(R.id.lyLogin);
        lyRegister = (LinearLayout) findViewById(R.id.lyRegister);
        btnLoginOrReg = (Button) findViewById(R.id.btnLogin);
        initTitle();
        initView();
        setListen();
    }

    private void setListen() {
        btnLoginOrReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnLoginOrReg.getText().toString().equals("登录")){
                    final CustomProgressDialog progressDialog = new CustomProgressDialog(LoginOrRegister.this,"网络君正在奔跑..", R.drawable.myprogressframe);
                    progressDialog.show();
                    User user = new User();
                    user.setUsername(clearEditTextName.getText().toString());
                    user.setPassword(clearEditTextPassword.getText().toString());
                    user.login(LoginOrRegister.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginOrRegister.this,"登录成功",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(LoginOrRegister.this,"登录失败"+s,Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }else {
                    final CustomProgressDialog progressDialog = new CustomProgressDialog(LoginOrRegister.this,"网络君正在奔跑..", R.drawable.myprogressframe);
                    progressDialog.show();
                    User user = new User();
                    user.setPassword(clearEditTextNewPassword.getText().toString());
                    user.setUsername(clearEditTextNewUserName.getText().toString());
                    user.setSex(false);
                    user.setMobilePhoneNumber(clearEditTextPhone.getText().toString());
                    user.signUp(LoginOrRegister.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(LoginOrRegister.this,"注册成功",Toast.LENGTH_SHORT).show();
                            setShowLogin(2);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(LoginOrRegister.this,s,Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void initView() {

    }

    private void initTitle() {
        titleBarView.initTitleWithTXTDrawable("登录", "返回", R.drawable.btn_back_sel, 5, "注册");
        titleBarView.setOnLeftTxtClickListener(new TitleBarView.OnLeftTxtClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        titleBarView.setOnRightTxtClickListener(new TitleBarView.OnRightTxtClickListener() {
            @Override
            public void onClick() {
                if (titleBarView.getRightTxtView().getText().toString().equals("注册")){
                    setShowLogin(1);
                }else {
                    setShowLogin(2);

                }
            }
        });
    }
    public void setShowLogin(int number){
        if (number == 1){
            titleBarView.setRightTxt("登录");
            titleBarView.setTitle("注册");
            lyLogin.setVisibility(View.GONE);
            lyRegister.setVisibility(View.VISIBLE);
            btnLoginOrReg.setText("注册");
        }else {
            titleBarView.setRightTxt("注册");
            titleBarView.setTitle("登录");
            lyLogin.setVisibility(View.VISIBLE);
            lyRegister.setVisibility(View.GONE);
            btnLoginOrReg.setText("登录");
        }
    }

    /**
     * 这个是进入这个界面的入口
     * @param context
     */
    public static void startAction(Context context){
        Intent intent = new Intent(context,LoginOrRegister.class);
        context.startActivity(intent);
    }
}
