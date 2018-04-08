package com.example.said.color;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public Timer timer;
    public View root;
    public TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        time = findViewById(R.id.time);

    }
    protected void onResume() {

        super.onResume();
        startClock();
    }
    protected void onPause(){
        super.onPause();
        if (timer != null){
            timer.cancel();
        }
    }
    private void startClock(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final String hexatime = getHexaTime();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        applyColor(hexatime);
                    }
                });
            }
        },0,1000);
    }
    private String getHexaTime(){
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY) * 255/23;
        int m = c.get(Calendar.MINUTE) * 255 /59;
        int s = c.get(Calendar.SECOND) * 255/59;

        return "#" + String.format("%02X",h) + String.format("%02X",m)+ String.format("%02X",s);

    }
    private void applyColor(String hexatime){
        int color = Color.parseColor(hexatime);
        root.setBackgroundColor(color);

        int tmp = (int) (0.2126 * Color.red(color)) + (int) (0.7152 * Color.green(color)) + (int) (0.722 * Color.blue(color));

        time.setText(hexatime);
        time.setTextColor(tmp < 128 ? Color.WHITE : Color.BLACK);
    }
}

