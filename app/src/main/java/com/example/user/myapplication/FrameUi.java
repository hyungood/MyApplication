package com.example.user.myapplication;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.example.user.myapplication.BoardGround;
import  com.example.user.myapplication.PublicDefine;

public class FrameUi extends View 
{
  private Context mContext = null;
  
  private int[] mImageIdGroup = {
      R.drawable.piece_00,  // ����
      R.drawable.piece_01,  // ����
      R.drawable.piece_02,  // ���
      R.drawable.piece_03,  // ����
      R.drawable.piece_04,  // ���
      R.drawable.piece_05,  // �Ķ�
      R.drawable.piece_06,  // ��Ȳ
      R.drawable.piece_07,  // ����
      R.drawable.piece_08   // ��û
  };
  
  private Bitmap mBmp00 = null;
  private Bitmap mBmp01 = null;
  private Bitmap mBmp02 = null;
  private Bitmap mBmp03 = null;
  private Bitmap mBmp04 = null;
  private Bitmap mBmp05 = null;
  private Bitmap mBmp06 = null;
  private Bitmap mBmp07 = null;
  private Bitmap mBmp08 = null;
  
  private Bitmap [] mBitmapGroup = {
      mBmp00,
      mBmp01,
      mBmp02,
      mBmp03,
      mBmp04,
      mBmp05,
      mBmp06,
      mBmp07,
      mBmp08
    };
  
  Point mPoint = null;
  private int mBitmapWidth = -1;
  private int mBitmapHeight = -1;
  
  private int mOffsetX = 0;
  private int mOffsetY = 0;
  
  private BoardGround mBoard = null;

  private void CreateInit(Context context)
  {
    if( mContext != null ) return;
    mContext = context;
    
    int iCount = mImageIdGroup.length;
    
    mPoint = new Point();

    for( int i=0 ; i<iCount ; i++ )
      mBitmapGroup[i] = getResBitmap( mImageIdGroup[i] );
    
//    Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();  
//    int x = display.getWidth();
//    int y = display.getHeight();
  }
  
  public FrameUi(Context context) {
    super(context);
    this.CreateInit( context );
  }
  
  public FrameUi(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.CreateInit( context );
  }
  
  public FrameUi(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.CreateInit( context );
  }
  
  // BoardGround Ŭ���� ����
  public void setBoard( BoardGround br)
  {
    mBoard = br;
  }
  
  // ȭ�� �ʱ�ȭ
  public void setBoardInit()
  {
  }
//bmpResId는 Drawable 내 이미지 파일 이름과 같음.
  private Bitmap getResBitmap(int bmpResId) {
        Options opts = new Options();


        opts.inDither = false;
        opts.inSampleSize = 1;


        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);

        if (bmp == null && isInEditMode())
        {
            // BitmapFactory.decodeResource doesn't work from the rendering
            // library in Eclipse's Graphical Layout Editor. Use this workaround instead.

            Drawable d = res.getDrawable(bmpResId, null);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            c.scale(200,200);
            d.setBounds(0, 0, w -1, h -1);
            d.draw(c);
        }

        return bmp;
    }


  
  @Override
  protected void onDraw(Canvas canvas) {

    if( mBoard == null ) return;
    
    if( (mBitmapWidth < 0) && (mBitmapHeight < 0) )
    {
      mBitmapWidth = mBitmapGroup[0].getScaledWidth( canvas ) ;
      mOffsetY = (canvas.getWidth() - (mBitmapWidth * PublicDefine.MATRIX_Y))/10 ;

      mBitmapHeight = mBitmapGroup[0].getScaledHeight( canvas );
      mOffsetX = (canvas.getHeight() - (mBitmapHeight * PublicDefine.MATRIX_X))/10;
    }
    
    for( int i=PublicDefine.MATRIX_BETWEEN, i1=0 ; i<PublicDefine.MATRIX_WORK_X ; i++, i1++ )
      for( int j=PublicDefine.MATRIX_BETWEEN, j1=0 ; j<PublicDefine.MATRIX_WORK_Y ; j++, j1++ )
        canvas.drawBitmap( mBitmapGroup[ mBoard.mBoard[i][j] ], j1*mBitmapWidth+mOffsetY, i1*mBitmapHeight+mOffsetX, null );
    //mBoard의 숫자값(예를 들어, Red는 1)과 비트맵그룹의 순서(레드는 1)가 같은걸 이용해, 보드를 훑으며 그림을 그려줌.
    super.onDraw(canvas);
  }
}
