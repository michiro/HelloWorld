/**
 * $Id$
 * Copyright (C) 2011 Mekira Net Systems Co,.Ltd.
 */

package jp.co.mekira.android.examples.helloworld1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.util.Log;

public class HelloWorld1Activity extends Activity {
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(gameView);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        if (gameView != null) {
            gameView.finish();
        }
        super.onDestroy();
    }

}
