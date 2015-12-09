package com.example.blue.lesson6;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity implements View.OnTouchListener {

    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);
        gv.setOnTouchListener(this);
//        gv.setBackgroundColor(Color.WHITE);
        setContentView(gv);
    }

    @Override
    protected void onPause() {

        //        try {
//            FileOutputStream fOut = openFileOutput("GopherPokeData", MODE_PRIVATE);
//            BufferedOutputStream buffer = new BufferedOutputStream(fOut);
//            DataOutputStream dataOut = new DataOutputStream(buffer);
//
//            dataOut.writeInt(gv.getScore());
//            dataOut.close();
//            fOut.close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        SharedPreferences prefs = getSharedPreferences("GopherPokeData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int tempscore = gv.getScore();
        editor.putInt("GopherPokeScore", tempscore);

        editor.commit();


        super.onPause();
        gv.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }

    @Override
    protected void onResume() {

//        try {
//            FileInputStream fIn = openFileInput("GopherPokeData");
//            BufferedInputStream buffer= new BufferedInputStream(fIn);
//            DataInputStream dataIn = new DataInputStream(buffer);
//
//            gv.setScore(dataIn.readInt());
//        } catch (Exception e) {
//        }

        SharedPreferences prefs = getSharedPreferences("GopherPokeData", MODE_PRIVATE);
        int retrievedHighScore = prefs.getInt("GopherPokeScore", 0);

        gv.setScore(retrievedHighScore);

        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        gv.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gv.onTouch(v, event);
    }
}
