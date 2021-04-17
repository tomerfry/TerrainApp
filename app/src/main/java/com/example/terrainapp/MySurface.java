package com.example.terrainapp;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class MySurface extends GLSurfaceView {

    public MySurface(Context context) {
        super(context);
        MyRenderer renderer = new MyRenderer();
        this.setRenderer(renderer);
    }

    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
