package me.jamie.gpuprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    MySurfaceView mSurfaceView;
    RenderThread mRenderThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()");

        super.onCreate(savedInstanceState);
        mSurfaceView = new MySurfaceView(this, this);
        setContentView(mSurfaceView);

        mRenderThread = new RenderThread();
        mRenderThread.start();
    }

    @Override protected void onPause() {
        Log.d("MainActivity", "onPause()");
        super.onPause();
    }

    @Override protected void onResume() {
        Log.d("MainActivity", "onResume()");
        super.onResume();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("MainActivity", "surfaceCreated()");
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d("MainActivity", String.format("surfaceChanged() format=%d, width=%d, height=%d", format, width, height));
        mRenderThread.onSurfaceChanged(surfaceHolder.getSurface(), width, height);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("MainActivity", "surfaceDestroyed()");
    }
}