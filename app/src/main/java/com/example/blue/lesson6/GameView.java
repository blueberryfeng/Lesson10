package com.example.blue.lesson6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Blue on 12/2/2015.
 */
public class GameView extends SurfaceView {

    private SurfaceHolder holder;
    private Bitmap mouse1;
    private boolean touched;

    private float mouseX = -300;
    private float mouseY = -300;
    private Timer timer;
    private TimerTask timerTask;

    private int score = 0;
    private Paint scorePaint;

    private GameThread gthread1 = null;

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                timer = new Timer(true);

                if (timerTask != null) {
                    timerTask.cancel();
                }


                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (Math.random() <= 0.33 && !(mouseX == 100 && mouseY == 100)) {
                            mouseX = 100;
                            mouseY = 100;
                        } else if (Math.random() <= 0.33 && !(mouseX == 300 && mouseY == 100)) {
                            mouseX = 300;
                            mouseY = 100;
                        } else if (Math.random() <= 0.33 && !(mouseX == 100 && mouseY == 300)) {
                            mouseX = 100;
                            mouseY = 300;
                        } else if (Math.random() <= 0.33 && !(mouseX == 300 && mouseY == 300)) {
                            mouseX = 300;
                            mouseY = 300;
                        } else {
                            mouseX = -300;
                            mouseY = -300;
                        }
                        touched = false;
                    }
                };


                timer.schedule(timerTask, 0, 1000);
                mouse1 = BitmapFactory.decodeResource(getResources(), R.drawable.mouse);

                scorePaint = new Paint();
                scorePaint.setColor(Color.BLACK);
                scorePaint.setTextSize(50.0f);

                makeThread();

                gthread1.setRunning(true);

                gthread1.start();


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    public void makeThread() {
        gthread1 = new GameThread(this);

    }

    public void killThread() {
        boolean retry = true;
        gthread1.setRunning(false);

        while (retry) {
            try {
                gthread1.join();

                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        canvas.drawBitmap(mouse1, mouseX, mouseY, null);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int scoreIn) {
        score = scoreIn;
    }

    public void onDestroy() {
        mouse1.recycle();
        mouse1 = null;
        System.gc();
    }


    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (!touched && x >= mouseX && x <= mouseX + mouse1.getWidth() && y >= mouseY && y <= mouseY + mouse1.getHeight()) {
                touched = true;
                score++;
            }
        }
        return true;
    }


}
