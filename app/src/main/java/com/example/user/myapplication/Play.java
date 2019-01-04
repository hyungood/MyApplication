//package com.androidstudy.Tetris;
//
//import com.androidstudy.Tetris.FrameUi;
//import com.androidstudy.Play.PlayGround;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.MotionEvent;
//
//public class Tetris extends Activity {
//
//    public FrameUi mFrameUi = null;
//    private PlayGround mPlayGround = null;
//    private MotionEventAction mMotionAction = null;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        mMotionAction = new MotionEventAction();
//        mPlayGround = new PlayGround((FrameUi)findViewById(R.id.imgFrameUi));
//
//        if( savedInstanceState == null )
//            mPlayGround.PlayGoGoGo();
//    }
//
//    @Override
//    protected void onStart() {
//        mPlayGround.PlayStart();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        mPlayGround.PlayStop();
//        super.onStop();
//    }
//
//    private class MotionEventAction
//    {
//        private float mDownX = -1;
//        private float mDownY = -1;
//        private float mUpX = -1;
//        private float mUpY = -1;
//
//        public  float IgnoreValue = 10;
//
//        public MotionEventAction() {
//            // TODO Auto-generated constructor stub
//        }
//
//        protected void ActionLeftMoveEvent(float fGapValue)
//        {
//            mPlayGround.ActionLeftMoveEvent(fGapValue);
//        }
//
//        protected void ActionRightMoveEvent(float fGapValue)
//        {
//            mPlayGround.ActionRightMoveEvent(fGapValue);
//        }
//
//        protected void ActionUpMoveEvent(float fGapValue)
//        {
//            mPlayGround.ActionUpMoveEvent(fGapValue);
//        }
//
//        protected void ActionDownMoveEvent(float fGapValue)
//        {
//            mPlayGround.ActionDownMoveEvent(fGapValue);
//        }
//
//        private void ActionCheck()
//        {
//            float GapX = mDownX - mUpX;
//            float GapY = mDownY - mUpY;
//            float AbsGapX = Math.abs(GapX);
//            float AbsGapY = Math.abs(GapY);
//
//            if( (AbsGapX< IgnoreValue) && (AbsGapY<IgnoreValue))
//            {
//                // 이 간격은 무시.
//                return;
//            }
//
//            if( AbsGapX < AbsGapY )
//            {
//                if( mDownY < mUpY )
//                    ActionDownMoveEvent(AbsGapY);
//                else
//                    ActionUpMoveEvent(AbsGapY);
//            }else
//            {
//                if( mDownX < mUpX )
//                    ActionRightMoveEvent(AbsGapX);
//                else
//                    ActionLeftMoveEvent(AbsGapX);
//            }
//        }
//
//        public void ActionMotionEvent( MotionEvent event )
//        {
//            switch( event.getActionMasked() )
//            {
//                case MotionEvent.ACTION_DOWN :
//                {
//                    mDownX = event.getX();
//                    mDownY = event.getY();
//                    break;
//                }
//
//                case MotionEvent.ACTION_UP :
//                {
//                    mUpX = event.getX();
//                    mUpY = event.getY();
//
//                    ActionCheck();
//                    break;
//                }
//            }
//        }
//    } // MotionEventAction class end
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mMotionAction.ActionMotionEvent(event);
//        return super.onTouchEvent(event);
//    }
//}