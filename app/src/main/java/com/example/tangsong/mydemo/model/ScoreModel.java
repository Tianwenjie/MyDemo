package com.example.tangsong.mydemo.model;

/**
 * 1  1000+(300*N)
   连点（400*L)

 * Created by tangsong on 2014/11/3.
 */
public class ScoreModel {

    long dishu = 1000;
    long mJiShuPuTongDianJi = 300;
    long mZongCiShu = 0;

    long mJiShuLianJi = 400;
    long mLianXuDianJiDeFenShu = 0;

    public long zongFenShu(){
        return this.puTongDianJiDeFenShu() + lianDianJiDeFenShu();
    }

    public long puTongDianJiDeFenShu(){
        return dishu + mJiShuPuTongDianJi * mZongCiShu;
    }

    public long lianDianJiDeFenShu(){
        return mJiShuLianJi * mLianXuDianJiDeFenShu;
    }


}
