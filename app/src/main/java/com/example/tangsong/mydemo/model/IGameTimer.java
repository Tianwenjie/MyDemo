package com.example.tangsong.mydemo.model;

/**
 * 游戏时间控制器接口，用时间管理游戏进度
 * Created by tangsong on 11/2/14.
 */
public interface IGameTimer {

    void start();


    void setTimerListener(GameTimerListener listener);


    public static interface GameTimerListener {
        void onEnd();
    }

}
