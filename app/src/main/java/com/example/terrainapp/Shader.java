package com.example.terrainapp;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public abstract class Shader {

    protected String vertexShaderPath, fragmentShaderPath;
    protected String vertexShader, fragmentShader;
    protected int programHandle;
    protected int vertexShaderHandle;
    protected int fragmentShaderHandle;

    public Shader(String vertexShaderPath, String fragmentShaderPath, AssetManager assetManager) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
        readShaders(assetManager);
        this.vertexShaderHandle = loadShader(GLES31.GL_VERTEX_SHADER, this.vertexShader);
        this.fragmentShaderHandle = loadShader(GLES31.GL_FRAGMENT_SHADER, this.fragmentShader);
        this.programHandle = GLES31.glCreateProgram();
        GLES31.glAttachShader(this.programHandle, this.vertexShaderHandle);
        GLES31.glAttachShader(this.programHandle, this.fragmentShaderHandle);
        this.bindAttributes();
        GLES31.glLinkProgram(this.programHandle);
        GLES31.glValidateProgram(this.programHandle);
        this.getAllUniformLocations();
    }

    public void start() {
        GLES31.glUseProgram(this.programHandle);
    }

    public void stop() {
        GLES31.glUseProgram(0);
    }

    public void clean() {
        stop();
        GLES31.glDetachShader(this.programHandle, this.vertexShaderHandle);
        GLES31.glDetachShader(this.programHandle, this.fragmentShaderHandle);
        GLES31.glDeleteShader(this.vertexShaderHandle);
        GLES31.glDeleteShader(this.fragmentShaderHandle);
        GLES31.glDeleteProgram(this.programHandle);
    }

    protected void bindAttribute(int attribute, String variableName) {
        GLES31.glBindAttribLocation(this.programHandle, attribute, variableName);
    }

    protected abstract void bindAttributes();

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, shaderCode);
        GLES31.glCompileShader(shader);
        return shader;
    }

    public void readShaders(AssetManager assetManager) {
        try {
            InputStream stream = assetManager.open(vertexShaderPath);
            byte[] buffer = new byte[stream.available()];
            int readlen = stream.read(buffer);
            stream.close();
            this.vertexShader = new String(buffer);
            stream = assetManager.open(fragmentShaderPath);
            buffer = new byte[stream.available()];
            readlen = stream.read(buffer);
            stream.close();
            this.fragmentShader = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniformName) {
        return GLES31.glGetUniformLocation(this.programHandle, uniformName);
    }

    protected void loadFloat(int location, float value) {
        GLES31.glUniform1f(location, value);
    }

    protected void loadVector3f(int location, float[] vector) {
        GLES31.glUniform3f(location, vector[0], vector[1], vector[2]);
    }

    protected void loadVector4f(int location, float[] vector) {
        GLES31.glUniform4f(location, vector[0], vector[1], vector[2], vector[3]);
    }

    protected void loadMatrix(int location, float[] matrix) {
        GLES31.glUniformMatrix4fv(location, 1, false, matrix, 0);
    }
}
