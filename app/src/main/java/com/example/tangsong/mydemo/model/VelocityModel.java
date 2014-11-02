package com.example.tangsong.mydemo.model;

/**
 * Created by tangsong on 11/2/14.
 */
public class VelocityModel {

    private float mV0 = 5;

    private float mV = mV0;

    private float mDeltaV = 2;
    /**
     * 从左至右
     */
    private boolean mDirect = true;

    public void changeDirect() {
        mDirect = !mDirect;
    }

    public void addViticor() {
        mV = mV + mDeltaV;
    }

    public float getCurrentV() {
        return mV;
    }

}
