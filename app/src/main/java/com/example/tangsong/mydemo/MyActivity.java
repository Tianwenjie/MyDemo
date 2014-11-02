package com.example.tangsong.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tangsong.mydemo.model.GameTimer;
import com.example.tangsong.mydemo.model.IGameTimer;
import com.example.tangsong.mydemo.model.ViticorModel;


public class MyActivity extends Activity {

    private GameState mGameState = GameState.IDEAL;
    private ViticorModel mVitivorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void startGame(final View v) {
        if (mGameState == GameState.PLAYING) {
            return;
        }

        mGameState = GameState.PLAYING;
        mVitivorModel = new ViticorModel();

        ((Button) v).setText("游戏进行中" + mGameState);

        GameTimer gameTimer = new GameTimer();
        gameTimer.setTimerListener(new IGameTimer.GameTimerListener() {
            @Override
            public void onEnd() {
                mGameState = GameState.ENDING;
                ((Button) v).setText("游戏结束" + mGameState + ", " + mVitivorModel.getCurrentV());
            }
        });
        gameTimer.start();
    }

    public void clickBall(View v) {
        mVitivorModel.addViticor();
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

    private enum GameState {
        IDEAL, PLAYING, ENDING;
    }
}
