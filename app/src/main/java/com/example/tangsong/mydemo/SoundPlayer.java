package com.example.tangsong.mydemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseIntArray;

/**
 * Created by tangsong on 11/3/14.
 */
public class SoundPlayer {


    private SoundPool mSoundPool;

    private SparseIntArray mSoundId;

    public void create(Context context) {
        mSoundId = new SparseIntArray();
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        mSoundId.put(R.raw.ball_bubble, mSoundPool.load(context, R.raw.ball_bubble, 1));
        mSoundId.put(R.raw.ball_hit_bound, mSoundPool.load(context, R.raw.ball_hit_bound, 1));
        mSoundId.put(R.raw.game_over_evil, mSoundPool.load(context, R.raw.game_over_evil, 1));
        mSoundId.put(R.raw.game_start, mSoundPool.load(context, R.raw.game_start, 1));
    }

    public void destory() {
        mSoundPool.release();
        mSoundPool = null;
    }

    public void play(int id) {
        mSoundPool.play(mSoundId.get(id), 1, 1, 0, 0, 1);
    }


}
