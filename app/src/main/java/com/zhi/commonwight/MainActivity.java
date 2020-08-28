package com.zhi.commonwight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.zhi.commonwight.wight.EditTextWithBt;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTextWithBt edTextBt = findViewById(R.id.ed_bt);
        edTextBt.setOnChangeLisener(new EditTextWithBt.OnChangeLisener() {
            @Override
            public void onChange(String value) {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });


        EditTextWithBt edTextBt2 = findViewById(R.id.ed_bt2);
        edTextBt2.setOnChangeLisener(new EditTextWithBt.OnChangeLisener() {
            @Override
            public void onChange(String value) {
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
