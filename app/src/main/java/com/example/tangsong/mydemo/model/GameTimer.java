package com.example.tangsong.mydemo.model;

import android.os.Handler;

/**
 * 游戏计时器类
 * 主要用到了android.os.Handler进行时间控制
 * Created by tangsong on 11/2/14.
 */
public class GameTimer implements IGameTimer {

    private int mGameTime = 5000;//一秒=1000毫秒

    /**
     *
     */
    private Handler mHandler = new Handler();

    private GameTimerListener mListener;

    @Override
    public void start() {
        //延迟5秒
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onEnd();
            }
        }, mGameTime);
    }

    @Override
    public void setTimerListener(GameTimerListener listener) {
        mListener = listener;
    }
}
