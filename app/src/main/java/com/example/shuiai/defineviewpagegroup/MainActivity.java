package com.example.shuiai.defineviewpagegroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private DefineViewGroup defineViewGroup;
    private int[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineViewGroup = (DefineViewGroup) findViewById(R.id.defineViewGroup);
        setImage();
    }

    private void setImage() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
            defineViewGroup.addView(imageView);
        }
    }
}
