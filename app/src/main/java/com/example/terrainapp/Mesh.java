package com.example.terrainapp;

public class Mesh {
    private float worldX, worldY, worldZ, rotX, rotY, rotZ, scale;
    private RawModel rawModel;

    public Mesh(RawModel rawModel, float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        this.rawModel = rawModel;
        this.worldX = x;
        this.worldY = y;
        this.worldZ = z;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.worldX += dx;
        this.worldY += dy;
        this.worldZ += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public float getWorldX() {
        return worldX;
    }

    public void setWorldX(float worldX) {
        this.worldX = worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    public void setWorldY(float worldY) {
        this.worldY = worldY;
    }

    public float getWorldZ() {
        return worldZ;
    }

    public void setWorldZ(float worldZ) {
        this.worldZ = worldZ;
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

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public RawModel getRawModel() {
        return rawModel;
    }
}
