package com.example.user.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class timeAct extends AppCompatActivity{
        TextView time;
        long curTime;
        long hours, minutes, seconds;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.matching_game);
        time =(TextView)findViewById(R.id.time);
        Timer timer = new Timer();
               timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                mHandler.obtainMessage().sendToTarget();
            }
        }, 0, 1000);




    }
    // 필드에 선언
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            curTime = SystemClock.currentThreadTimeMillis();
            hours = curTime/(1000*60*60);
            minutes = (curTime-hours)/(1000*60);
            seconds = (curTime-hours-minutes)/1000;

            time.setText(minutes+":"+seconds);

        }

    };


    }
