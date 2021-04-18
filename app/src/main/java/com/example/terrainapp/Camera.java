package com.example.terrainapp;

import android.opengl.Matrix;

public class Camera {
    private float x, y, z, rotX, rotY, rotZ;
    private final float[] viewMatrix = new float[4*4];

    public Camera(float x, float y, float z, float rotX, float rotY, float rotZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void updateCamera() {
        Matrix.setLookAtM(this.viewMatrix, 0, this.x, this.y, this.z, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }

    public float[] getViewMatrix() {
        return viewMatrix;
    }
}
