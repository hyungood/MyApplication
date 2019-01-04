package com.example.user.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class puzzleActivity extends AppCompatActivity {

    private int[][] cellData;
    private int BtnWidth;
    private RelativeLayout cv;
    Button startBtn;
    boolean result2 = false;
    ArrayList<Drawable> arrayd = new ArrayList<Drawable>();
    int n=0;
    TextView tv;
    int score= 0;

    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);


        MyView myview =new MyView(getApplicationContext());
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(10000, TimeUnit.SECONDS)
                .readTimeout(30000, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(apiService.API_URL).client(client).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(APIservice.class);


        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++)
                arrayd.add( new BitmapDrawable(myview.imgPic[i][j]));
        }

        RelativeLayout gameView = new RelativeLayout(this);
        setContentView(gameView);


        tv = new TextView(this);
        tv.setText("점수: "+score);
        tv.setTextSize(32);
        RelativeLayout.LayoutParams lpone = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpone.setMargins(30,20,10,10);
        tv.setLayoutParams(lpone);
        gameView.addView(tv);


        cv = new RelativeLayout(this);
        cv.setBackgroundColor(Color.argb(180, 0, 0, 0));
        RelativeLayout.LayoutParams lptwo = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lptwo.setMargins(10,200,10,10);
        cv.setLayoutParams(lptwo);
        gameView.addView(cv);

        int sw = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int nb = 3;
        BtnWidth = sw / nb;
        int indexBtn = 0;

        cellData = new int[nb][nb];

        Button mb = null;
        RelativeLayout.LayoutParams lp = null;

        for (int ny = 0; ny < nb; ny++) {
            for (int nx = 0; nx < nb; nx++) {
                mb = new Button(this);
                mb.setBackground(arrayd.get(nx+n));
                lp = new RelativeLayout.LayoutParams(BtnWidth, BtnWidth);
                lp.leftMargin = nx * BtnWidth;
                lp.topMargin = ny * BtnWidth;
                mb.setLayoutParams(lp);
                mb.setTextSize(72);
                mb.setText(String.valueOf(++indexBtn));
                mb.setOnClickListener(BtnClick);
                cv.addView(mb);
                cellData[ny][nx] = 1;

            }
            n=n+3;

        }
        cv.removeViewAt(nb * nb - 1);
        cellData[nb - 1][nb - 1] = 0;

        startBtn = new Button(this);
        lp = new RelativeLayout.LayoutParams(sw, 200);
        lp.topMargin = BtnWidth * nb;
        lp.setMargins(5,1300,5,5);
        startBtn.setLayoutParams(lp);
        startBtn.setTextSize(32);
        startBtn.setText("START GAME");


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shufflePosition();
            }
        });
        gameView.addView(startBtn);
    }


    private View.OnClickListener BtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveButton(v, getEmptyCell(v));
            if (isCompleteGame()  ) {
                score+=12;
                startBtn.setText("COMPLETE GAME "  );
                result2=true;
                tv.setText("점수: "+score*12);


            }
            int nb = 3;
            Log.d("++GAME", "--------------------");
            for (int ny = 0; ny < nb; ny++) {
                Log.d("++GAME", String.valueOf(cellData[ny][0]) + ","
                        + String.valueOf(cellData[ny][1]) + "," + String.valueOf(cellData[ny][2]));
            }
        }
    };

    private int getEmptyCell(View v) {
        RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) v.getLayoutParams();
        int ny = Integer.valueOf(rp.topMargin / BtnWidth);
        int nx = Integer.valueOf(rp.leftMargin / BtnWidth);
        int result = (nx - 1 >= 0 && cellData[ny][nx - 1] == 0) ? 1 :
                (nx + 1 <= 2 && cellData[ny][nx + 1] == 0) ? 2 :
                        (ny - 1 >= 0 && cellData[ny - 1][nx] == 0) ? 3 :
                                (ny + 1 <= 2 && cellData[ny + 1][nx] == 0) ? 4 : 0;
        return result;
    }

    private void moveButton(View v, int dir) {
        RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) v.getLayoutParams();
        int ny = Integer.valueOf(rp.topMargin / BtnWidth);
        int nx = Integer.valueOf(rp.leftMargin / BtnWidth);
        switch (dir) {
            case 1:
                rp.leftMargin -= BtnWidth;
                cellData[ny][nx] = 0;
                cellData[ny][nx - 1] = 1;
                break;
            case 2:
                rp.leftMargin += BtnWidth;
                cellData[ny][nx] = 0;
                cellData[ny][nx + 1] = 1;
                break;
            case 3:
                rp.topMargin -= BtnWidth;
                cellData[ny][nx] = 0;
                cellData[ny - 1][nx] = 1;
                break;
            case 4:
                rp.topMargin += BtnWidth;
                cellData[ny][nx] = 0;
                cellData[ny + 1][nx] = 1;
                break;
        }
        v.setLayoutParams(rp);
    }

    private boolean isCompleteGame() {
        boolean result = true;
        for (int i = cv.getChildCount() - 1; i > 0; i--) {
            Button btn = (Button) cv.getChildAt(i);
            RelativeLayout.LayoutParams rp = (RelativeLayout.LayoutParams) btn.getLayoutParams();
            int ny = Integer.valueOf(rp.topMargin / BtnWidth);
            int nx = Integer.valueOf(rp.leftMargin / BtnWidth);
            int id = Integer.valueOf(btn.getText().toString());
            if (
                    (ny == 0 && nx + 1 == id) ||
                            (ny == 1 && nx + 4 == id) ||
                            (ny == 2 && nx + 7 == id)
                    ) {

            } else {
                result=false;
                break;
            }
        }

        return result;
    }

    private void shufflePosition() {
        Random rnd = new Random();
        for (int i = cv.getChildCount() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            RelativeLayout.LayoutParams first = (RelativeLayout.LayoutParams) ((Button) cv.getChildAt(index)).getLayoutParams();
            RelativeLayout.LayoutParams next = (RelativeLayout.LayoutParams) ((Button) cv.getChildAt(i)).getLayoutParams();
            ((Button) cv.getChildAt(index)).setLayoutParams(next);
            ((Button) cv.getChildAt(i)).setLayoutParams(first);
        }
        timeCount = 0;
        if (handler == null) {
            handler = new Handler();
            checkTime();
        }
    }

    private void shuffleLable() {
        Random rnd = new Random();
        for (int i = cv.getChildCount() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String lbl = ((Button) cv.getChildAt(index)).getText().toString();
            String tmp = ((Button) cv.getChildAt(i)).getText().toString();
            ((Button) cv.getChildAt(index)).setText(tmp);
            ((Button) cv.getChildAt(i)).setText(lbl);
        }
    }

    private int timeCount = 0;
    Handler handler;

    private void checkTime() {
        handler.postDelayed(new Runnable() {
            public void run() {
                if(!result2){
                    Log.d(result2+"","fffff");
                    timeCount++;
                    checkTime();
                    if (timeCount < 60) {
                        startBtn.setText(String.valueOf(timeCount) + " 초 경과.");
                    } else {
                        int m = timeCount / 60;
                        int s = timeCount - (m * 60);
                        startBtn.setText(String.valueOf(m) + "분 " + String.valueOf(s) + "초 경과.");
                    }

                }}
        }, 1000);


    }



    class MyView extends View {

        int width, height;
        int left, top;
        int orgW, orgH;
        int picW, picH;
        Bitmap imgBack, imgOrg;
        Bitmap imgPic[][] = new Bitmap[3][3];

        public MyView(Context context) {
            super(context);
            Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            width=display.getWidth();
            height=display.getHeight()-500;

            //비트맵 이미지로 바꾸기
            imgBack= BitmapFactory.decodeResource(context.getResources(),R.drawable.black);
            imgBack=Bitmap.createScaledBitmap(imgBack,width,height,true);


            imgOrg=BitmapFactory.decodeResource(context.getResources(),R.drawable.like);
            imgOrg=Bitmap.createScaledBitmap(imgOrg,width-width/10,height-height/10,true);


            orgW=imgOrg.getWidth();
            orgH=imgOrg.getHeight();

            //너비와 높이 3으로 쪼개기
            picW=orgW/3;
            picH=orgH/3;

            //왼쪽, 위쪽 마진
            left=(width-orgW)/2;
            top=(height-orgH)/2;

            //쪼개기
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++)
                    imgPic[i][j]=Bitmap.createBitmap(imgOrg, j*picW, i*picH, picW, picH);
            }

            imgPic[2][2]=Bitmap.createBitmap(imgOrg, 0, 0, 1,1);
        }

//        public void onDraw(Canvas canvas) {
//            Paint paint = new Paint();
//            EmbossMaskFilter emboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 1, 1);
//            paint.setMaskFilter(emboss);
//
////            canvas.drawBitmap(imgBack, 0, 0, null);
//            for(int i=0; i<3; i++) {
//                for(int j=0; j<3; j++)
//                    canvas.drawBitmap(imgPic[i][j], left+j*picW, top+i*picH, paint);
//            }
//        }
    }

    public void onPause(){
        super.onPause();
        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token= mPref.getString("token","");

        Call<userConnect> comment2 = apiService.puzzlescore(score, token);
        comment2.enqueue(new Callback<userConnect>() {
            @Override
            public void onResponse(Call<userConnect> call, Response<userConnect> response) {
//                Log.v("Test", "Response Body is" + response.body().toString());
                userConnect u = response.body();

                if( u.getMessage().equals("성공")) {
                    finish(); //다시 메인화면으로 돌아가기
                    Toast.makeText(getApplicationContext(), "점수 업로드 완료!", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<userConnect> call, Throwable t) {
                Log.d("userRetrofit", t.toString());
            }


        });
    }

}