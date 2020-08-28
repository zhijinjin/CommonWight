package com.zhi.commonwight.wight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhi.commonwight.R;

import java.util.Timer;
import java.util.TimerTask;

public class EditTextWithBt extends LinearLayout {
    Context context;
    //最初值
    String firstValule = "";
    //左侧按钮文字
    private String leftText;
    //右侧按钮文字
    private String rightText;
    //加减 梯度，默认1
    private int ladderValue;
    //输入框默认值
    private int defaultValue = 0;
    //最小值
    private int miniValue = 1;
    //最大值
    private int maxValue = 0;
    //按钮背景
    private int btLeftBackground;
    private int btRightBackground;
    //输入框背景
    private int edBackground;
    //按钮宽高
    private float btWidth;
    //按钮宽高
    private float btHight;
    //是否可操作
    private boolean enabled;

    private float btSpec;
    private EditText editText;
    private Timer timer;
    //倒计时
    private Timer cutDownTimer;
    private OnChangeLisener onChangeLisener;

    public EditTextWithBt(Context context) {
        this(context,null);
    }

    public EditTextWithBt(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditTextWithBt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setGravity(Gravity.CENTER_VERTICAL);
        init(attrs);
        initView();
    }


    private void init(AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithBt);
        leftText = typedArray.getString(R.styleable.EditTextWithBt_leftText);
        rightText = typedArray.getString(R.styleable.EditTextWithBt_rightText);
        defaultValue = typedArray.getInt(R.styleable.EditTextWithBt_defaultValue,0);
        miniValue = typedArray.getInt(R.styleable.EditTextWithBt_miniValue,1);
        maxValue = typedArray.getInt(R.styleable.EditTextWithBt_maxValue,0);
        ladderValue = typedArray.getInt(R.styleable.EditTextWithBt_ladderValue,1);
        btLeftBackground = typedArray.getResourceId(R.styleable.EditTextWithBt_leftBtBackground, R.drawable.selector_bt);
        btRightBackground = typedArray.getResourceId(R.styleable.EditTextWithBt_rightBtBackground, R.drawable.selector_bt);
        edBackground = typedArray.getResourceId(R.styleable.EditTextWithBt_editBackground, R.drawable.ed_bg);
        btWidth = typedArray.getDimension(R.styleable.EditTextWithBt_btWidth, 90);
        btHight = typedArray.getDimension(R.styleable.EditTextWithBt_btHight, 90);
        btSpec = typedArray.getDimension(R.styleable.EditTextWithBt_btSpec, 10);
        enabled = typedArray.getBoolean(R.styleable.EditTextWithBt_enabled, true);
    }

    private void initView(){
        //水平方向
        this.setOrientation(LinearLayout.HORIZONTAL);
        View leftBt = createBtView(leftText);
        leftBt.setBackgroundResource(btLeftBackground);
        leftBt.setId(R.id.bt_subtract);
        leftBt.setOnTouchListener(onTouchListener);
        addView(leftBt);

        editText = createEditView();
        addView(editText);

        View rightBt = createBtView(rightText);
        rightBt.setId(R.id.bt_add);
        rightBt.setBackgroundResource(btRightBackground);
        rightBt.setOnTouchListener(onTouchListener);
        addView(rightBt);
    }

    private  OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(cutDownTimer!=null){
                        cutDownTimer.cancel();
                        cutDownTimer = null;
                    }
                    v.setPressed(true);
                    startChageData(v.getId()==R.id.bt_add ? true : false);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    timeCutDown();
                    if(timer!=null){
                        timer.cancel();
                        timer = null;
                    }
                    break;
            }
            return true;
        }
    };

    //倒计时
    private void timeCutDown(){
        cutDownTimer = new Timer();
        cutDownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        overInput();
                    }
                });
            }
        },200);
    }

    private void startChageData(final boolean isAdd){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isAdd){
                            addData();
                        }else {
                            subtractData();
                        }
                    }
                });
            }
        }, 0, 250);
    }

    private void addData(){
        if(editText.getText()==null){
            editText.setText(leftText);
        }else{
            int newNum = Integer.valueOf(editText.getText().toString()) + ladderValue;
            if(maxValue!=0 && newNum>maxValue){
                newNum = maxValue;
            }
            editText.setText(String.valueOf(newNum));
        }
    }

    private void subtractData(){
        if(editText.getText()==null){
            editText.setText(miniValue);
        }else{
            int newNum = Integer.valueOf(editText.getText().toString()) - ladderValue;
            if(newNum < miniValue){
                newNum = miniValue;
            }
            editText.setText(String.valueOf(newNum));
        }
    }

    private View createBtView(String name){
        LinearLayout.LayoutParams params = new LayoutParams((int)btWidth,(int)btHight);
        TextView textView = new TextView(context);
        textView.setBackgroundColor(Color.parseColor("#ffcecece"));
        textView.setTextSize(22);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#ff333333"));
        textView.setText(name);
        textView.setLayoutParams(params);
        textView.setEnabled(enabled);
        return textView;
    }

    private EditText createEditView(){
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT,1);
        params.leftMargin = (int) btSpec;
        params.rightMargin = (int)btSpec;

        EditText editText = new EditText(context);
        editText.setPadding(0,0,0,0);
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setLayoutParams(params);
        editText.setBackgroundResource(edBackground);
        editText.setMaxLines(1);
        editText.setText(defaultValue==0 ? "" : String.valueOf(defaultValue));
        editText.setOnEditorActionListener(actionListener);
        editText.setOnFocusChangeListener(onFocusChangeListener);
        firstValule = editText.getText()==null ? "": editText.getText().toString();
        editText.setEnabled(enabled);
        return editText;
    }

    OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus){
                overInput();
            }
        }
    };
    EditText.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_DONE   //完成
                    || actionId == EditorInfo.IME_ACTION_SEARCH  //搜索
                    ) {
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                overInput();
                return true;
            }
            return false;
        }
    };

    //输入完毕
    private void overInput(){
        if(null != onChangeLisener){
            String value = "";
            if(editText.getText() != null){
                value = editText.getText().toString();
            }
            //与最初值不一样在通知
            if(!firstValule.equals(value)){
                onChangeLisener.onChange(value);
                firstValule = value;
            }
        }
    }

    public OnChangeLisener getOnChangeLisener() {
        return onChangeLisener;
    }

    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public interface OnChangeLisener{
        void onChange(String value);
    }
}
