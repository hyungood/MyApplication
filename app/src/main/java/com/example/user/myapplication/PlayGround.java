package com.example.user.myapplication;
import java.util.Random;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.user.myapplication.BoardGround;
import  com.example.user.myapplication.PieceList;
import  com.example.user.myapplication.Tetris;
import com.example.user.myapplication.PieceItem;
import  com.example.user.myapplication.PublicDefine;
import  com.example.user.myapplication.FrameUi;


public class PlayGround {

    private static final String LOGTAG = "PlayGround";
    public static final int   DELAY_GAP = 1100;

    // ���� ���� ���� �ð�
    private int mDelayTime = DELAY_GAP;

    // ���� ����/���� ����
    private boolean mPlayGo = true;

    public Tetris t = new Tetris();
    // ��� ����
    public BoardGround mBoardGround = null;
    public BoardGround mBoardInforGround = null;
    // ������ ����
    private PieceList mPieceList = null;

    // ���� Ÿ�̸�? ��? ��,��;
    private RefreshHandler mRedrawHandler = null;

    private FrameUi mFrameUi = null;

    private PieceItem mPieceItem = null;

    private Random randGen = new Random();

    // �������� �������� ��ġ
    private int mPosX = 0;
    private int mPosY = 0;
    int score=0;
  public PlayGround( final FrameUi ui )
  {
    mFrameUi = ui;
    mBoardGround = new BoardGround();
    mBoardInforGround = new BoardGround();
    mFrameUi.setBoard( mBoardGround );

    mPieceList = new PieceList();
    mRedrawHandler = new RefreshHandler();
    mRedrawHandler.sleep(mDelayTime);
  }

  // ���� �������� ���´�.
  // �̶� ��ġ�� ���� ��������.
  private static final int NONE_INDEX = -1;
  private void SelectRandomItem()
  {
    mPosX = NONE_INDEX;
    mPosY = randGen.nextInt(PublicDefine.MATRIX_Y - PublicDefine.PIECE_SIZE);
    mPieceItem = mPieceList.getRandomItem();

    // 방향을 회전 시킨다.~ 휘리릭~ break가 없는 것은 Trick.
    switch( randGen.nextInt(4) )
    {
      case 1 : mPieceItem.getRotateItem();
      case 2 : mPieceItem.getRotateItem();
      case 3 : mPieceItem.getRotateItem();
    }

    for( int i=0 ; (i<PublicDefine.PIECE_SIZE) && (mPosX==NONE_INDEX) ; i++)
      for( int j=0 ; j<PublicDefine.PIECE_SIZE ; j++)
        if( mPieceItem.Piece[i][j] != PublicDefine.PIECE_NO )
        {
          mPosX = i;
          break;
        }
  }
  private int getMoveGapValue( float fGapValue )
  {
    return ( fGapValue < 100 )? 1:2;
  }
  private void RemoveFullLineBoard()
  {

    int iSum = 0, k=PublicDefine.MATRIX_BR_X-PublicDefine.MATRIX_BETWEEN-1;
    //내가 -1로 고침.

    int delLine = k;
    for( int i=PublicDefine.MATRIX_WORK_X-1 ; i>=PublicDefine.MATRIX_BETWEEN ; i-- )
    //내가 >=로 고침.
    {
      iSum = 0;
      for( int j=PublicDefine.MATRIX_BETWEEN ; j<PublicDefine.MATRIX_WORK_Y ; j++ )
        iSum += ( mBoardGround.mBoard[i][j] == PublicDefine.PIECE_BL )? 0:1;

      if( iSum == PublicDefine.MATRIX_Y )
      {
        delLine--;
        score+=12;
        continue;
      }

      for( int j=PublicDefine.MATRIX_BETWEEN ; j<PublicDefine.MATRIX_WORK_Y ; j++ )
        mBoardGround.mBoard[k][j] = mBoardGround.mBoard[i][j];
      //k는 마지막으로 없어진 라인. 즉, k바로다음은 없어짐. 지금 라인에서 내려주기.
        //i : 현재 라인 위치 파악 변수, d: 없어진 라인 갯수 세는 변수.
      k--;
    }

    k = PublicDefine.MATRIX_BR_X-PublicDefine.MATRIX_BETWEEN - delLine -1;
    //k는 삭제된 라인 갯수
    for( int i=PublicDefine.MATRIX_BETWEEN ; i<k ; i++ )
      for( int j=PublicDefine.MATRIX_BETWEEN ; j<(PublicDefine.MATRIX_WORK_Y) ; j++ )
        mBoardGround.mBoard[i][j] = PublicDefine.PIECE_BL;
    //없어진 라인 수만큼 윗 칸은 0으로 만들기.

    for( int i=PublicDefine.MATRIX_BETWEEN ; i<PublicDefine.MATRIX_WORK_X ; i++ )
      for( int j=PublicDefine.MATRIX_BETWEEN ; j<PublicDefine.MATRIX_WORK_Y ; j++ )
        mBoardInforGround.mBoard[i][j] = mBoardGround.mBoard[i][j];
    //보드의 현재 상태 mBoardInforGround 에 옮기기.
  }

  private void CheckEndGame()
  {
    boolean bCheck = true;
    int i=PublicDefine.MATRIX_BETWEEN ;

    for( int j=PublicDefine.MATRIX_BETWEEN ; (j<PublicDefine.MATRIX_WORK_Y) && bCheck ; j++ )
       bCheck = mBoardInforGround.mBoard[i][j] == PublicDefine.PIECE_BL;
    //맨윗줄이 차면 게임끝

    if( ! bCheck )
    {
      Log.i(LOGTAG, "Play end!");
      PlayStop();
    }
  }

  public void PlayGoGoGo()
  {
    SelectRandomItem();
    RemoveFullLineBoard();
    CheckEndGame();
  }

  // ����
  public void PlayStart()
  {
    SelectRandomItem();
    mPlayGo = true;
    mDelayTime = DELAY_GAP;
    mRedrawHandler.sleep( mDelayTime );
  }

  // ����
  public void PlayStop()
  {
    mPlayGo = false;
    mDelayTime = DELAY_GAP;
  }

  class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
          // �ݺ��ؼ� �̺�Ʈ�� ȣ�� �ȴ�.
          Log.i(LOGTAG, "run Delvil run run" );
          PlayGround.this.update();
        }

        public void sleep(long delayMillis) {
          this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };

  public void ActionLeftMoveEvent(float fGapValue)
  {
    // 왼쪽으로 이동  Y 축을 감소시킨다.
    int iGap = getMoveGapValue(fGapValue);
    for( int i=iGap ; i>=1 ; i-- )
      if( PossibleMove( mPosX, mPosY-i ) )
      {
        MergeBoardPiece( mPosX, mPosY-i );
        break;
      }

    this.mFrameUi.invalidate();
  }

  public void ActionRightMoveEvent(float fGapValue)
  {
    int iGap = getMoveGapValue(fGapValue);
    for( int i=iGap ; i>=1 ; i-- )
      if( PossibleMove( mPosX, mPosY+i ) )
      {
        MergeBoardPiece( mPosX, mPosY+i );
        break;
      }

    this.mFrameUi.invalidate();
  }

  public void ActionUpMoveEvent(float fGapValue)
  {
    // ���� ���忡 �ִ°��� ��������.
    ShadowRemove();

    mPieceItem.getRotateItem();
    if( PossibleMove( mPosX, mPosY ) )
      MergeBoardPiece( mPosX, mPosY );
    else
      PlayGoGoGo();

    this.mFrameUi.invalidate();
  }

  public void ActionDownMoveEvent(float fGapValue)
  {
    for( int i=mPosX+1 ; i<(PublicDefine.MATRIX_WORK_X) ; i++ )
      if( ! PossibleMove( i, mPosY ) )
      {
        MergeBoardPiece( i-1, mPosY );
        PlayGoGoGo();
        break;
      }

    this.mFrameUi.invalidate();
  }


  private boolean PossibleMove(final int x, final int y)
  {
    boolean Result = true;

    // Piece�� ������ ������ �ִ� ������ Board�� Black�� �ƴ� �ٸ� ���� �� ���� ���
    for( int i=0 ; (i<PublicDefine.PIECE_SIZE) && (Result) ; i++ )
      for( int j=0 ; (j<PublicDefine.PIECE_SIZE) && (Result); j++ )
        if( mPieceItem.Piece[i][j] != PublicDefine.PIECE_NO )
          Result = mBoardInforGround.mBoard[i+x][j+y] == PublicDefine.PIECE_BL;
    return Result;
  }

  private void ShadowRemove()
  {
    for( int i=0 ; i<PublicDefine.PIECE_SIZE ; i++ )
      for( int j=0 ; j<PublicDefine.PIECE_SIZE; j++ )
        if( (mBoardGround.mBoard[i+mPosX][j+mPosY] == mPieceItem.Piece[i][j]) &&
          (mBoardGround.mBoard[i+mPosX][j+mPosY] != PublicDefine.PIECE_BL) &&
          (mBoardGround.mBoard[i+mPosX][j+mPosY] != PublicDefine.PIECE_NO) )
          mBoardGround.mBoard[i+mPosX][j+mPosY] = PublicDefine.PIECE_BL;
  }

  private void MergeBoardPiece(final int newX, final int newY )
  {
    // ����  ���ο� ������ �׸���.
    ShadowRemove();

    for( int i=0 ; i<PublicDefine.PIECE_SIZE ; i++ )
      for( int j=0 ; j<PublicDefine.PIECE_SIZE; j++ )
        if( mPieceItem.Piece[i][j] != PublicDefine.PIECE_NO )
          mBoardGround.mBoard[i+newX][j+newY] = mPieceItem.Piece[i][j];

    mPosX = newX;
    mPosY = newY;
  }

  // ��ĭ�� �����´�.
  private void PieceOneStepDown()
  {
    Log.i(LOGTAG, "Piece one step down~");
    // X ���� �����Ѵ�.

    if( PossibleMove( mPosX+1, mPosY ) )
      MergeBoardPiece( mPosX+1, mPosY );
    else
      PlayGoGoGo();
  }

  private void update()
  {
    this.PieceOneStepDown();

    this.mFrameUi.invalidate();
    if( mPlayGo )
      mRedrawHandler.sleep( mDelayTime );
  }

}
