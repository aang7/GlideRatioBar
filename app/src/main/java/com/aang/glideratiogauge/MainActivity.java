package com.aang.glideratiogauge;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private GlideRatioBar glideRatioBar;
    private float value = 10f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glideRatioBar = (GlideRatioBar) findViewById(R.id.view);
    }

    public void onClickIncrementButton(View view){
        value += 10;
        glideRatioBar.setValue(value);
    }

    public void onClickDecrementButton(View view){
        value -= 10;
        glideRatioBar.setValue(value);
    }
}
