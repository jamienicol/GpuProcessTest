package me.jamie.gpuprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceControl;

public class GpuProcessService extends Service {
    RenderThread mRenderThread;

    public GpuProcessService() {
        Log.d("GpuProcessService", "constructor");
    }

    @Override
    public void onCreate() {
        Log.d("GpuProcessService", "onCreate");
        mRenderThread = new RenderThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GpuProcessService", "onStartCommand() " + startId + ": " + intent);

        mRenderThread.start();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (IGpuProcessService.class.getName().equals(intent.getAction())) {
            return mBinder;
        }
        return null;
    }

    private final IGpuProcessService.Stub mBinder = new IGpuProcessService.Stub() {
        @Override
        public void onSurfaceChanged(SurfaceControl surfaceControl, int width, int height) {
            Log.d("GpuProcessService", String.format("onSurfaceChanged() %d %d", width, height));
            mRenderThread.onSurfaceChanged(surfaceControl, width, height);
        }
    };
}
