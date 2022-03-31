package me.jamie.gpuprocess;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;

public class RenderThread extends Thread {
    Surface mSurface = null;
    int mWidth = 0;
    int mHeight = 0;
    boolean mInitialized = false;

    float mRotation;

    public synchronized void onSurfaceChanged(SurfaceControl surfaceControl, int width, int height) {
        mSurface = new Surface(surfaceControl);
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setBufferSize(surfaceControl, width, height).apply();
        mWidth = width;
        mHeight = height;
        mInitialized = false;
    }

    @Override
    public void run() {
        while (true) {
            synchronized(this) {
                if (mInitialized == false) {
                    if (mSurface != null) {
                        Log.d("RenderThread", "Initializing context");

                        mInitialized = true;
                    } else {
                        Log.d("RenderThread", "No surface");
                        continue;
                    }
                }

            }

            // Log.d("RenderThread", "Render");
            Canvas canvas = mSurface.lockCanvas(null);
            canvas.drawRGB(0, 0, 0);
            canvas.save();
            canvas.rotate(mRotation, mWidth / 2, mHeight / 2);
            mRotation = (mRotation + 1) % 360;
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect(mWidth / 2 - 200, mHeight / 2 - 200, mWidth / 2 + 200, mHeight / 2 + 200, paint);
            canvas.restore();
            mSurface.unlockCanvasAndPost(canvas);
        }
    }
}
