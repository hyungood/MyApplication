package com.example.user.myapplication;


import java.util.List;
import java.util.Date;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIservice {

  public static final String API_URL = "http://ec2-52-78-231-121.ap-northeast-2.compute.amazonaws.com:3000/";
    @Headers({"Accept: application/json"})

//
    @FormUrlEncoded
    @POST("signup")
    Call<userConnect> register(@Field("name") String myName, @Field("id") String myId, @Field("pwd") int myPwd,
     @Field("phone") String myPhone,  @Field("birth") Date myBirth  );
//
    @FormUrlEncoded //중복확인
    @POST("chkid")
    Call<userConnect> chkId(@Field("id") String myId);

    @FormUrlEncoded //부모 자식 맺기
    @POST("relation")
    Call<userConnect> relation(@Header("token") String token, @Field("name_r") String relationN, @Field("phone_r") String relationP);

  //구체적 약속 맺기위한 가족이름 가져오기
    @GET("familylist")
    Call<Repo> postToken(@Header("token") String token);

    @FormUrlEncoded
    @POST("signin")
    Call<userConnect> signin(@Field("id") String myId,
                             @Field("pwd") int pwd) ;
    @FormUrlEncoded
    @POST("writeContract")
    Call<userConnect> writeCon(
                             @Header("token") String token,
                             @Field("id_p") String myName,
                             @Field("id_c") String yourName,
                             @Field("score") int score,
                             @Field("content") String content
                             ) ;

    // 내 정보
    @GET("profile")
    Call<profile> myProfile( @Header("token") String token );

    @FormUrlEncoded     // 내 정보 수정
    @POST("edit")
    Call<edit> edit( @Header("token") String token, @Field("pwd") int myPwd, @Field("birth") String myBirth );

  // 내 점수
  @GET("myscore")
  Call<myscore> myScore( @Header("token") String token );


    @GET("contractList")
    Call<chkconRepo> contractList(@Header("token") String token);

    @FormUrlEncoded
    @POST("orderingscore")
    Call<userConnect> orderingscore(@Field("ordering") int score,
            @Header("token") String token);
    @FormUrlEncoded
    @POST("puzzlescore")
    Call<userConnect> puzzlescore(@Field("puzzle") int score,
                                    @Header("token") String token);
    @FormUrlEncoded
    @POST("shapescore")
    Call<userConnect> shapescore(@Field("shape") int score,
                                    @Header("token") String token);
    @FormUrlEncoded
    @POST("tetrisscore")
    Call<userConnect> tetrisscore(@Field("tetris") int score,
                                    @Header("token") String token);


   @GET("undoneContract")
    Call<undoneCon> undoneContract(@Header("token") String token);

    //내 정보 보기
    @GET("showUser")
    Call<show> showUser(@Header("token") String token);

    //
    @GET("sendContract")
    Call<myname> sendContract(@Header("token") String token);

    @FormUrlEncoded
    @POST("modifyContract") //스크랩하기
    Call<userConnect> modifyContract(@Header("token") String token, @Field("idx") int idx, @Field("score") int score,
                                     @Field("content") String content);

}