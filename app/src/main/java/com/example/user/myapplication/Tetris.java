package com.example.user.myapplication;
import com.example.user.myapplication.FrameUi;
import com.example.user.myapplication.PlayGround;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Tetris extends Activity {

    public FrameUi mFrameUi = null;
    private PlayGround mPlayGround = null;
    private MotionEventAction mMotionAction = null;
    TextView tv;
    int playi=0;
    Retrofit retrofit;
    APIservice apiService;
    SharedPreferences mPref;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMotionAction = new MotionEventAction();
        mPlayGround = new PlayGround((FrameUi)findViewById(R.id.imgFrameUi));
        tv = (TextView)findViewById(R.id.tetScore);

        ThreadEx thread = new ThreadEx();
        thread.start();

        if( savedInstanceState == null )
            mPlayGround.PlayGoGoGo();
    }



    public class ThreadEx extends Thread
    { private static final String TAG = "Thread";

        public ThreadEx() {

    }
        public void run() {
            while (true) {

                try {
                    if(playi !=mPlayGround.score)
                    {tv.setText("점수: " + mPlayGround.score);
                    playi=mPlayGround.score;}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onStart() {
        mPlayGround.PlayStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mPlayGround.PlayStop();
        super.onStop();
    }

    private class MotionEventAction
    {
        private float mDownX = -1;
        private float mDownY = -1;
        private float mUpX = -1;
        private float mUpY = -1;

        public  float IgnoreValue = 10;

        public MotionEventAction() {
            // TODO Auto-generated constructor stub
        }

        protected void ActionLeftMoveEvent(float fGapValue)
        {
            mPlayGround.ActionLeftMoveEvent(fGapValue);
        }

        protected void ActionRightMoveEvent(float fGapValue)
        {
            mPlayGround.ActionRightMoveEvent(fGapValue);
        }

        protected void ActionUpMoveEvent(float fGapValue)
        {
            mPlayGround.ActionUpMoveEvent(fGapValue);
        }

        protected void ActionDownMoveEvent(float fGapValue)
        {
            mPlayGround.ActionDownMoveEvent(fGapValue);
        }

        private void ActionCheck()
        {
            float GapX = mDownX - mUpX;
            float GapY = mDownY - mUpY;
            float AbsGapX = Math.abs(GapX);
            float AbsGapY = Math.abs(GapY);

            if( (AbsGapX< IgnoreValue) && (AbsGapY<IgnoreValue))
            {
                // 이 간격은 무시.
                return;
            }

            if( AbsGapX < AbsGapY )
            {
                if( mDownY < mUpY )
                    ActionDownMoveEvent(AbsGapY);
                else
                    ActionUpMoveEvent(AbsGapY);
            }else
            {
                if( mDownX < mUpX )
                    ActionRightMoveEvent(AbsGapX);
                else
                    ActionLeftMoveEvent(AbsGapX);
            }
        }

        public void ActionMotionEvent( MotionEvent event )
        {
            switch( event.getActionMasked() )
            {
                case MotionEvent.ACTION_DOWN :
                {
                    mDownX = event.getX();
                    mDownY = event.getY();
                    break;
                }

                case MotionEvent.ACTION_UP :
                {
                    mUpX = event.getX();
                    mUpY = event.getY();

                    ActionCheck();
                    break;
                }
            }
        }
    } // MotionEventAction class end


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMotionAction.ActionMotionEvent(event);
        return super.onTouchEvent(event);
    }


    public void onPause(){
        super.onPause();
        mPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token= mPref.getString("token","");
        int score = mPlayGround.score;
        Call<userConnect> comment2 = apiService.tetrisscore(score, token);
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