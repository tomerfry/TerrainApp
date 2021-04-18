package com.example.terrainapp;

import android.opengl.GLES31;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, int[] indices) {
        int vaoHandle = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, positions);
        unbindVAO();

        return new RawModel(vaoHandle, indices.length);
    }

    private int createVAO() {
        int[] names = new int[1];
        GLES31.glGenVertexArrays(1, names, 0);
        int vaoHandle = names[0];
        this.vaos.add(vaoHandle);
        GLES31.glBindVertexArray(vaoHandle);
        return vaoHandle;
    }

    private void storeDataInAttributeList(int attributeNumber, float[] data) {
        int[] names = new int[1];
        GLES31.glGenBuffers(1, names, 0);
        int vboHandle = names[0];
        this.vbos.add(vboHandle);
        GLES31.glBindBuffer(GLES31.GL_ARRAY_BUFFER, vboHandle);

        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GLES31.glBufferData(GLES31.GL_ARRAY_BUFFER, buffer.capacity() * Float.BYTES, buffer, GLES31.GL_STATIC_DRAW);
        GLES31.glVertexAttribPointer(attributeNumber, 3, GLES31.GL_FLOAT, false, 0, 0);

    }

    private void unbindVAO() {
        GLES31.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * Float.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(data);
        floatBuffer.position(0);
        return floatBuffer;
    }

    public void clean() {
        int[] vaosArr = new int[this.vaos.size()];
        int[] vbosArr = new int[this.vbos.size()];

        for (int i=0; i < this.vaos.size(); i++) {
            vaosArr[i] = this.vaos.get(i);
        }


        for (int i=0; i < this.vbos.size(); i++) {
            vbosArr[i] = this.vbos.get(i);
        }

        GLES31.glDeleteVertexArrays(this.vaos.size(), vaosArr, 0);
        GLES31.glDeleteBuffers(this.vbos.size(), vbosArr, 0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int[] names = new int[1];
        GLES31.glGenBuffers(1, names, 0);
        int vboHandle = names[0];
        GLES31.glBindBuffer(GLES31.GL_ELEMENT_ARRAY_BUFFER, vboHandle);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GLES31.glBufferData(GLES31.GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * Integer.BYTES, buffer, GLES31.GL_STATIC_DRAW);

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(data.length * Integer.BYTES);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);
        intBuffer.position(0);
        return intBuffer;
    }
}
