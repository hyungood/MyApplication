package com.example.user.myapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class matchActivity extends AppCompatActivity {
    ImageView one, two, three, four;
    LinearLayout linearLayout;
    ViewGroup.MarginLayoutParams mg;;
    int x[] = {30,290,550,820};
    ArrayList<Integer> result = new ArrayList<>();
    ArrayList<Integer> correct =new ArrayList<>();
    TextView tv;
    Button hint;

    boolean chkcorrect;

    Chronometer chron;

    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    int score=0;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.matching_game);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        tv=(TextView)findViewById(R.id.score);
        hint = (Button)findViewById(R.id.hint);

        chron = (Chronometer) findViewById(R.id.chron);
        chron.setBase(SystemClock.elapsedRealtime());
        chron.setOnChronometerTickListener(new OnChronometerTickListener()
        { @Override public void onChronometerTick(Chronometer chronometer)
        { // TODO Auto-generated method stub

            int sec = (int) (((SystemClock.elapsedRealtime() - chron.getBase()) / 1000) % 5);

             }
        });

             chron.start();





        tv.setText("점수: "+score);
        correct.add(30);
        correct.add(290);
        correct.add(550);
        correct.add(820);


        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mg = new ViewGroup.MarginLayoutParams(layoutParams);
        mg.setMargins(25,10,25,0);
        mg.width=220;
        mg.height=230;


        one = new ImageView(matchActivity.this);
        one.setImageResource(R.drawable.heart);
        one.setLayoutParams(mg);
        one.setId(Integer.parseInt("30"));
        linearLayout.addView(one);
//            one.setVisibility(View.INVISIBLE);





        two = new ImageView(matchActivity.this);
        two.setImageResource(R.drawable.rect);
        two.setLayoutParams(mg);
        two.setId(Integer.parseInt("290"));
        linearLayout.addView(two);

        three = new ImageView(matchActivity.this);
        three.setImageResource(R.drawable.rect2);
        three.setLayoutParams(mg);
        three.setId(Integer.parseInt("550"));
        linearLayout.addView(three);

        four = new ImageView(matchActivity.this);
        four.setImageResource(R.drawable.cir);
        four.setLayoutParams(mg);
        four.setId(Integer.parseInt("820"));
        linearLayout.addView(four);


        ExampleThread thread = new ExampleThread();
        thread.start();


        ImageView.OnClickListener onClickListener = new ImageView.OnClickListener(){

            public void onClick(View v) {
                switch (v.getId()) {
                    case 30:
                        result.add(30);
                        Log.e("1번",result.toString());
                        break;
                    case 290:
                        result.add(290);
                        Log.e("2번",result.toString());
                        break;
                    case 550:
                        result.add(550);
                        Log.e("3번",result.toString());
                        break;
                    case 820:
                        result.add(820);
                        Log.e("4번",result.toString());
                        break;

                    default:
                        break;
                }
                int b = result.size();
                if(b==4)
                {
                    chkcorrect = chk(result);
                    result.clear();

                if(chkcorrect)
                {

                    score+=12;
                    tv.setText("점수: "+score);
                    MoregameThread thread1 = new MoregameThread();
                    thread1.start();
                    chkcorrect=false;
                }
            }




        }

        };
        one.setOnClickListener(onClickListener);
        two.setOnClickListener(onClickListener);
        three.setOnClickListener(onClickListener);
        four.setOnClickListener(onClickListener);


       hint.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               HintThread thread2 = new HintThread();
               thread2.start();
           }
       });


    }



    public boolean chk(ArrayList<Integer> list){

        if(list.equals(correct))
        {
            Toast.makeText(this, "정답", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
         {Toast.makeText(this, "오답", Toast.LENGTH_SHORT).show();
        return false;
        }



    }


    private static int[] shuffle(int[] array) {

        // TODO Auto-generated method stub



        int temp, seed;



        for(int i=0;i<array.length;i++){

            seed=(int)(Math.random()*3);

            temp = array[i];

            array[i]=array[seed];

            array[seed]=temp;

        }
//       for(int i =0;i<4;i++)
//          System.out.print(result[i]);


        return array;


    }





    public class ExampleThread extends Thread
    { private static final String TAG = "ExampleThread";
        public ExampleThread() {

        }
        public void run() {


            try { Thread.sleep(5000);
            }
            catch (InterruptedException e)
            { e.printStackTrace(); ; }

            shuffle(x);
            one.setX(x[0]);
            one.setY(1000);
            two.setX(x[1]);
            two.setY(1000);
            three.setX(x[2]);
            three.setY(1000);
            four.setX(x[3]);
            four.setY(1000);


        }

    }

    public class MoregameThread extends Thread
    { private static final String TAG = "ExampleThread";
        public MoregameThread() {

        }
        public void run() {
            shuffle(x);
            one.setX(x[0]);
            one.setY(5);
            one.setId(x[0]);

            two.setX(x[1]);
            two.setY(5);
            two.setId(x[1]);

            three.setX(x[2]);
            three.setY(5);
            three.setId(x[2]);

            four.setX(x[3]);
            four.setY(5);
            four.setId(x[3]);


            try { Thread.sleep(5000);
            }
            catch (InterruptedException e)
            { e.printStackTrace(); ; }

            shuffle(x);
            one.setX(x[0]);
            one.setY(1000);
            two.setX(x[1]);
            two.setY(1000);
            three.setX(x[2]);
            three.setY(1000);
            four.setX(x[3]);
            four.setY(1000);


        }

    }


    public class HintThread extends Thread
    { private static final String TAG = "ExampleThread";
        public HintThread() {

        }
        public void run() {

            one.setX(one.getId());
            one.setY(5);

            two.setX(two.getId());
            two.setY(5);

            three.setX(three.getId());
            three.setY(5);

            four.setX(four.getId());
            four.setY(5);

            try { Thread.sleep(5000);
            }
            catch (InterruptedException e)
            { e.printStackTrace(); ; }

            one.setX(x[0]);
            one.setY(1000);
            two.setX(x[1]);
            two.setY(1000);
            three.setX(x[2]);
            three.setY(1000);
            four.setX(x[3]);
            four.setY(1000);


        }


        }


    public void onPause(){
        super.onPause();
        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token= mPref.getString("token","");

        Call<userConnect> comment2 = apiService.shapescore(score, token);
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
