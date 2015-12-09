package com.example.blue.lesson6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Blue on 12/5/2015.
 */
public class Splash extends Activity implements View.OnTouchListener {
    Context content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        TextView textView = new TextView(this);
        textView.setText("Mouses are eating corps! Farmers need help!");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
        textView.setGravity(Gravity.CENTER);
        textView.setHeight(500);
        ImageView imageView = new ImageView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.title));
        layout.addView(textView);
        layout.addView(imageView);
        layout.setOnTouchListener(this);
        setContentView(layout);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent callMain = new Intent(content, MainActivity.class);
        startActivity(callMain);
        return false;
    }
}
