package com.example.tangsong.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tangsong.mydemo.model.GameTimer;
import com.example.tangsong.mydemo.model.IGameTimer;
import com.example.tangsong.mydemo.model.VelocityModel;


public class MyActivity extends Activity {

    /**
     * 当前游戏状态
     */
    private GameState mGameState = GameState.IDEAL;
    /**
     *
     */
    private VelocityModel mVelocityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    /**
     * 开始游戏按钮事件
     *
     * @param v
     */
    public void startGame(final View v) {
        if (mGameState == GameState.PLAYING) {
            return;
        }

        mGameState = GameState.PLAYING;
        mVelocityModel = new VelocityModel();

        ((Button) v).setText("游戏进行中" + mGameState);

        GameTimer gameTimer = new GameTimer();
        gameTimer.setTimerListener(new IGameTimer.GameTimerListener() {
            @Override
            public void onEnd() {
                mGameState = GameState.ENDING;
                ((Button) v).setText("游戏结束" + mGameState + ", " + mVelocityModel.getCurrentV());
            }
        });
        gameTimer.start();
    }

    /**
     * 点击球时的按钮事件
     *
     * @param v
     */
    public void clickBall(View v) {
        mVelocityModel.addViticor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 游戏状态枚举
     */
    private enum GameState {
        /**
         * 空闲
         */
        IDEAL,
        /**
         * 游戏中
         */
        PLAYING,
        /**
         * 结束
         */
        ENDING;
    }

}
