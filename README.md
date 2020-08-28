# CommonWight
# 步进器

## 介绍
> 步进器由增加按钮、减少按钮和输入框组成，用于在一定范围内输入、调整数字

![image](https://github.com/zhijinjin/CommonWight/blob/master/imgs/200828154938.png)
## 功能
> 点击增加或者减少 步大小数，长按按钮可以快速增减
## 使用方式
    `` 
    <com.zhi.commonwight.wight.EditTextWithBt
        android:layout_marginTop="20dp"
        android:id="@+id/ed_bt3"
        app:leftBtBackground="@drawable/cut"
        app:rightBtBackground="@drawable/add"
        app:btWidth="25dp"
        app:btHight="25dp"
        app:defaultValue="2"
        app:btSpec="10dp"
        app:ladderValue="1"
        app:miniValue="1"
        app:maxValue="10"
        android:layout_width="180dp"
        android:layout_height="30dp"/> 
        ``
## 回调方法
失去焦点，如键盘提交，都会触发该事件
`` 
EditTextWithBt.setOnChangeLisener(new EditTextWithBt.OnChangeLisener() {
            @Override
            public void onChange(String value) {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });
        `` 
## 自定义属性
        <!--按钮宽度-->
        <attr name="btWidth" format="dimension"/>
        <!--按钮高度-->
        <attr name="btHight" format="dimension"/>
        <!--按钮与输入框间距-->
        <attr name="btSpec" format="dimension"/>
        <!--左侧按钮文字-->
        <attr name="leftText" format="string"/>
        <!--左侧按钮背景-->
        <attr name="leftBtBackground" format="color|reference"/>
        <!--右侧按钮文字-->
        <attr name="rightText" format="string"/>
        <!--右侧按钮背景-->
        <attr name="rightBtBackground" format="color|reference"/>
        <!--输入框背景-->
        <attr name="editBackground" format="color|reference"/>
        <!--输入框默认值-->
        <attr name="defaultValue" format="integer"/>
        <!--输入框最小值-->
        <attr name="miniValue" format="integer"/>
        <!--输入框最大值-->
        <attr name="maxValue" format="integer"/>
        <!--步 大小-->
        <attr name="ladderValue" format="integer"/>
        <!--是否可用-->
        <attr name="enabled" format="boolean"/>
