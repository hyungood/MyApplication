package com.example.user.myapplication;

public class PublicDefine {
	
	public static final String  LOGTAG = "TETRIS";
	
	public static final int 	BACK_GR = 0;
	
	// �������� ũ��
	public static final int 	PIECE_SIZE = 4;

	// �������� ����
	public static final int   PIECE_NO = -1; // ĥ���� ����
	public static final int 	PIECE_BL = 0;  // ����-BLACK
	public static final int 	PIECE_RD = 1;  // ����-RED
	public static final int 	PIECE_YW = 2;  // ���-YELLOW
	public static final int 	PIECE_LG = 3;  // ����-LIGHT GREEN
	public static final int 	PIECE_GR = 4;  // ���-GREEN
	public static final int 	PIECE_BU = 5;  // �Ķ�-BLUE
	public static final int 	PIECE_OR = 6;  // ��Ȳ-ORANGE
	public static final int 	PIECE_PP = 7;  // ����-PURPLE
	public static final int 	PIECE_NV = 8;  // ��û-NAVY BLUE

	/*
	 * ���ϴ� X ��
	 * �¿�� Y ��
	 * ( X, Y ) 
	*/
	
	// ��Ʈ���� ���� ũ��
	public static final int	 	MATRIX_X = 19;
	public static final int 	MATRIX_Y = 13;
	
	public static final int   MATRIX_BETWEEN = PIECE_SIZE-1;
	
	private static final int 	MATRIX_GAP = MATRIX_BETWEEN*2;
	
	public static final int   MATRIX_BR_X = MATRIX_X + MATRIX_GAP;
	public static final int   MATRIX_BR_Y = MATRIX_Y + MATRIX_GAP;
	
	public static final int		MATRIX_WORK_X = MATRIX_BR_X - MATRIX_BETWEEN;
	public static final int		MATRIX_WORK_Y = MATRIX_BR_Y - MATRIX_BETWEEN;
}

