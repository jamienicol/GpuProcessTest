package me.jamie.gpuprocess;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Surface;

public class RenderThread extends Thread {
    Surface mSurface = null;
    int mWidth = 0;
    int mHeight = 0;
    boolean mInitialized = false;

    public synchronized void onSurfaceChanged(Surface surface, int width, int height) {
        mSurface = surface;
        mWidth = width;
        mHeight = height;
        mInitialized = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("RenderThread", "Running iteration");
            synchronized(this) {
                if (mInitialized == false) {
                    if (mSurface != null) {
                        Log.d("RenderThread", "Initializing context");

                        mInitialized = true;
                    } else {
                        continue;
                    }
                }

            }

            Canvas canvas = mSurface.lockCanvas(null);
            canvas.drawRGB(255, 0, 0);
            mSurface.unlockCanvasAndPost(canvas);
        }
    }
}