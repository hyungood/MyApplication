package com.example.user.myapplication;
/*
 * PiceItem - ��Ʈ������  ���� Ŭ����
 * */

import  com.example.user.myapplication.PublicDefine;

public class PieceItem {
  
  public int[][] Piece;
  
  public PieceItem(int[][] pieceinf) {
    Piece = new int[PublicDefine.PIECE_SIZE][PublicDefine.PIECE_SIZE];
    
    for( int i=0 ; i<PublicDefine.PIECE_SIZE ; i++ )
      for( int j=0 ; j<PublicDefine.PIECE_SIZE ; j++ )
        Piece[i][j] = pieceinf[i][j];
  }
  
  public PieceItem MakeClone()
  {
    PieceItem Result = new PieceItem( this.Piece );
    return Result;
  }

  // ������ ������
  public final PieceItem getRotateItem()
  {
    int[][] tmpPiece = new int[PublicDefine.PIECE_SIZE][PublicDefine.PIECE_SIZE];

    for( int i=0 ; i<PublicDefine.PIECE_SIZE ; i++ )
      for( int j=0 ; j<PublicDefine.PIECE_SIZE ; j++ )
        tmpPiece[i][j] = this.Piece[i][j];
    
    // 반시계 방향으로 회전
      for( int i1=0 ; i1<PublicDefine.PIECE_SIZE ; i1++ )
          for( int i2=0 ; i2<PublicDefine.PIECE_SIZE ; i2++ )
            this.Piece[i1][i2] = tmpPiece[PublicDefine.PIECE_SIZE-i2-1][i1];

      tmpPiece = null;
      return this;
  }
}
