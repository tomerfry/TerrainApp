package com.example.terrainapp;

public class RawModel {
    private int vaoHandle;
    private int vertexCount;

    public RawModel(int vaoHandle, int vertexCount) {
        this.vaoHandle = vaoHandle;
        this.vertexCount = vertexCount;
    }

    public int getVaoHandle() {
        return vaoHandle;
    }

    public void setVaoHandle(int vaoHandle) {
        this.vaoHandle = vaoHandle;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
