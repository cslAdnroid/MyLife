package com.mxn.soul.flowingdrawer.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.mxn.soul.flowingdrawer.R;


/**
 * 自定义EditText,调用setCompoundDrawables为EditText绘制上去，并实现点击即可清除文本
 * Created by Huangzm on 2017/4/8 0008.
 */

public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private Drawable clearDrawable;
    private Context mContext;

    public ClearEditText(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        this.mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){

        //获取EditText的DrawableRight，假若没有设置我们就使用默认的图片
        clearDrawable = getCompoundDrawables()[2];
        if(clearDrawable == null){
            clearDrawable = ContextCompat.getDrawable(mContext,R.drawable.login_registration_passworddelete);
        }

        //设置边界,getIntrinsicWidth()为获取内在宽度
        clearDrawable.setBounds(0, 0,
                clearDrawable.getIntrinsicWidth(),clearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件,所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在EditText的宽度 - 图标到口径右边的间距 - 图标的宽度 和
     * EditText的宽度 - 图标到控件右边的间距之间就算我们点击了clear图标, 竖直方向没有考虑
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(getCompoundDrawables()[2] != null){
            boolean touchable = false;
            if(event.getAction() == MotionEvent.ACTION_UP){
                if(event.getX() > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()
                        && event.getX() < (getWidth() - getPaddingRight())){
                     touchable = true;
                }
                if(touchable){
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 设置清除图标的显示赫尔隐藏,调用setCompoundDrawables为EditText绘制上去
     * @param isVisible
     */
    protected void setClearIconVisible(boolean isVisible){
        Drawable right = isVisible? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);
    }





    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 当ClearEditText焦点发生变化的时候,判断里面字符串长度并设置清除图标的显示和隐藏
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            setClearIconVisible(getText().length() > 0);
        }else {
            setClearIconVisible(false);
        }
    }
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
    }
}
