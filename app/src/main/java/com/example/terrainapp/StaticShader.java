package com.example.terrainapp;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.util.Log;


public class StaticShader extends Shader {

    public static final int POSITION_ATTRIB_HANDLE = 0;
    private static final String VERTEX_FILE = "TerrainVertex.glsl", FRAGMENT_FILE = "TerrainFragment.glsl";
    private int locationTransformationMatrix;
    private int locationColor;

    public StaticShader(AssetManager assetManager) {
        super(VERTEX_FILE, FRAGMENT_FILE, assetManager);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(POSITION_ATTRIB_HANDLE, "vPosition");
    }

    @Override
    protected void getAllUniformLocations() {
        this.locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        this.locationColor = super.getUniformLocation("vColor");
    }

    public void loadTransformationMatrix(float[] transformationMatrix) {
        super.loadMatrix(this.locationTransformationMatrix, transformationMatrix);
    }

    public void loadColorVector(float[] vColor) {
        super.loadVector4f(this.locationColor, vColor);
    }

}
