package com.example.user.myapplication;

/*
 * ��Ʈ������ ���� ����Ʈ
 * 
 * */

import java.util.ArrayList;
import java.util.Random;

import  com.example.user.myapplication.PublicDefine;

public class PieceList {

  private Random randGen = null;
  private ArrayList<PieceItem> PieceItems = null;
  
  // �׸� ���
  private final int[][] Piece01 = { 
      {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_RD,PublicDefine.PIECE_RD,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_RD,PublicDefine.PIECE_RD,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO} };
  
  // �� ���
  private final int[][] Piece02 = { 
      {PublicDefine.PIECE_NO,PublicDefine.PIECE_YW,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_YW,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_YW,PublicDefine.PIECE_YW,PublicDefine.PIECE_YW},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO} };
  
  // ���� ��ģ ���
  private final int[][] Piece03 = { 
      {PublicDefine.PIECE_NO,PublicDefine.PIECE_LG,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_LG,PublicDefine.PIECE_LG,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_LG,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_LG,PublicDefine.PIECE_NO} };
  
  // �� ���
  private final int[][] Piece04 = { 
      {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_GR,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_GR,PublicDefine.PIECE_GR,PublicDefine.PIECE_GR},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO} };
  
  // + ���
  private final int[][] Piece05 = { 
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_BU,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_BU,PublicDefine.PIECE_BU,PublicDefine.PIECE_BU},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_BU,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO} };
  
  // | ���
  private final int[][] Piece06 = { 
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_OR,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_OR,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_OR,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO},
            {PublicDefine.PIECE_NO,PublicDefine.PIECE_OR,PublicDefine.PIECE_NO,PublicDefine.PIECE_NO} };
  
  public PieceList() 
  {
    PieceItems = new ArrayList<PieceItem>();
    randGen = new Random();
    InitList();
  }
  
  // ���� �⺻ ����
  private void InitList()
  {
    PieceItems.add( new PieceItem(Piece01) );
    PieceItems.add( new PieceItem(Piece02) );
    PieceItems.add( new PieceItem(Piece03) );
    PieceItems.add( new PieceItem(Piece04) );
    PieceItems.add( new PieceItem(Piece05) );
    PieceItems.add( new PieceItem(Piece06) );
  }
  
  // ���� ����
  public int Length()
  {
    return PieceItems.size();
  }
  
  // index �� �ش��ϴ� ������ ����
  public PieceItem getItems( final int index )
  {
    PieceItem Result = PieceItems.get(index);
    return Result.MakeClone();
  }
  
  // ������ ������ ����
  public PieceItem getRandomItem()
  {
    return this.getItems( this.randGen.nextInt( this.Length() ));
  }
}
