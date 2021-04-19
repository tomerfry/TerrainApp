package com.example.terrainapp;

import android.content.res.AssetManager;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer implements GLSurfaceView.Renderer {

    private final AssetManager assetManager;
    private World world;
    private Loader loader;
    private final float[] projectionMatrix = new float[4*4];
    private float[] viewMatrix;
    private final float[] transformationMatrix = new float[4*4];

    static final float[] vColor = {0.0f, 1.0f, 0.0f, 1.0f};

    float[] squareCoords = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f };

    int[] indices = {
            0, 1, 3,
            3, 1, 2
    };

    private Mesh mesh;
    private StaticShader shader;

    public MyRenderer(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES31.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        this.loader = new Loader();
        RawModel model = this.loader.loadToVAO(this.squareCoords, this.indices);
        this.shader = new StaticShader(this.assetManager);
        this.world = new World(model);

        new Thread(new Runnable() {
            @Override
            public void run() {
                world.worldLoop();
            }
        }).start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES31.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(this.projectionMatrix, 0, -ratio, ratio, -1, 1, 3f, 1000f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT);
        GLES31.glClearColor(1, 0, 0, 1);
        this.viewMatrix = this.world.getCamera().getViewMatrix();
        shader.start();
        render(this.world.getMesh(), this.shader);
        shader.stop();
    }

    public void render(Mesh mesh, StaticShader shader) {
        RawModel rawModel = mesh.getRawModel();

        GLES31.glBindVertexArray(rawModel.getVaoHandle());
        GLES31.glEnableVertexAttribArray(0);

        constructTransformationMatrix(mesh);
        shader.loadTransformationMatrix(this.transformationMatrix);
        shader.loadColorVector(vColor);
        shader.loadViewMatrix(this.viewMatrix);
        shader.loadProjectionMatrix(this.projectionMatrix);

        GLES31.glDrawElements(GLES31.GL_TRIANGLES, rawModel.getVertexCount(), GLES31.GL_UNSIGNED_INT, 0);
        GLES31.glDisableVertexAttribArray(0);
        GLES31.glBindVertexArray(0);
    }

    private void constructTransformationMatrix(Mesh mesh) {
        Matrix.setIdentityM(this.transformationMatrix, 0);
        Matrix.translateM(this.transformationMatrix, 0, mesh.getWorldX(), mesh.getWorldY(), mesh.getWorldZ());
        Matrix.rotateM(this.transformationMatrix, 0, (float)Math.toRadians((double)mesh.getRotX()), 1, 0, 0);
        Matrix.rotateM(this.transformationMatrix, 0, (float)Math.toRadians((double)mesh.getRotY()), 0, 1, 0);
        Matrix.rotateM(this.transformationMatrix, 0, (float)Math.toRadians((double)mesh.getRotZ()), 0, 0, 1);
        Matrix.scaleM(this.transformationMatrix, 0, mesh.getScale(), mesh.getScale(), mesh.getScale());
    }
}
