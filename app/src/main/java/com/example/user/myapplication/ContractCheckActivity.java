package com.example.user.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContractCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_check);

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("로그아웃","로그아웃");
                startActivity(i);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
