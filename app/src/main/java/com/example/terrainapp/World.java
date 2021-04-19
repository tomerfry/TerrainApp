package com.example.terrainapp;

import android.os.SystemClock;
import android.util.Log;

public class World {
    private boolean working;
    private Camera camera;
    private Mesh mesh;

    public World(RawModel model) {
        this.working = true;
        this.camera = new Camera(0.0f, 4.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        this.mesh = new Mesh(model, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void worldLoop() {
        long firstTime = SystemClock.uptimeMillis();
        long prevTime = firstTime;
        long currentTime = SystemClock.uptimeMillis();

        while(this.working) {
            if(currentTime - prevTime >= 100) {
                this.camera.setZ((float)(currentTime - firstTime) / 2000);
                this.camera.updateCamera();
                prevTime = currentTime;
            }
            currentTime = SystemClock.uptimeMillis();
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return this.mesh;
    }
}
