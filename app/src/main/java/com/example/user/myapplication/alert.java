package com.example.user.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class alert extends Activity{

    final Context context = this;
    int howmany;
    MainActivity mainActivity;
    Button login;



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);


//        linearLayout =(LinearLayout)findViewById(R.id.linearlayout);
//
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        Intent intent = getIntent();
        howmany = intent.getExtras().getInt("howmany");
        mainActivity = new MainActivity();
        login = mainActivity.login;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // 제목셋팅
        alertDialogBuilder.setTitle("계약 " + howmany + "개 도착");
        alertDialogBuilder.setMessage("약속을 확인하시겠습니까?");
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), alert_listContract.class);
                startActivity(i);
            }
        });
        alertDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("로그아웃", "로그아웃");
                i.putExtra("num", 1);
                startActivity(i);

            }
        });

        // AlertDialog 셋팅
//        alertDialogBuilder
//                .setMessage("약속을 확인하시겠습니까?")
//                .setCancelable(false)
//                .setPositiveButton("확인",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(
//                                    DialogInterface dialog, int id) {
//                                Intent i = new Intent(getApplicationContext(), alert_listContract.class);
//                                startActivity(i);
//                            }
//                        })
//                .setNegativeButton("취소",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(
//                                    DialogInterface dialog, int id) {
//                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(i);
//                                // 다이얼로그를 취소한다
////                                dialog.cancel();
////                                finish();
//                                login.setText("로그아웃");
//                            }
//                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }
}



