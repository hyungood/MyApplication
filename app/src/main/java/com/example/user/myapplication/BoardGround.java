package com.example.user.myapplication;

import com.example.user.myapplication.PublicDefine;

public class BoardGround {

  public int [][] mBoard = null;
  
  public BoardGround() {
    mBoard = new int[PublicDefine.MATRIX_BR_X][PublicDefine.MATRIX_BR_Y];

    for( int i=0 ; i<PublicDefine.MATRIX_BR_X ; i++ )
      for( int j=0 ; j<PublicDefine.MATRIX_BR_Y ; j++ )
      {
        if( (i<PublicDefine.MATRIX_BETWEEN ) || (i>=(PublicDefine.MATRIX_X+PublicDefine.MATRIX_BETWEEN)) ||
          (j<PublicDefine.MATRIX_BETWEEN ) || (j>=(PublicDefine.MATRIX_Y+PublicDefine.MATRIX_BETWEEN))  )
        {
          mBoard[i][j] = PublicDefine.PIECE_NO;
        } else
          mBoard[i][j] = PublicDefine.PIECE_BL;
      }
      //보드 주위에 접근 불가능한 벽을 만들기
  }
}
