package me.jamie.gpuprocess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class GpuProcessService extends Service {
    public GpuProcessService() {
        Log.d("GpuProcessService", "constructor");
    }

    @Override
    public void onCreate() {
        Log.d("GpuProcessService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GpuProcessService", "onStartCommand() " + startId + ": " + intent);
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
        public void hello(String name) throws RemoteException {
            Log.d("GpuProcessService", String.format("Hello %s", name));
        }
    };
}