package me.jamie.gpuprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    MySurfaceView mSurfaceView;
    RenderThread mRenderThread;
    IGpuProcessService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()");

        super.onCreate(savedInstanceState);
        mSurfaceView = new MySurfaceView(this, this);
        setContentView(mSurfaceView);

        mRenderThread = new RenderThread();
        mRenderThread.start();

        Log.d("MainActivity", "Starting GPU process service");
        Intent startIntent = new Intent(MainActivity.this, GpuProcessService.class);
        startService(startIntent);
        Intent bindIntent = new Intent(MainActivity.this, GpuProcessService.class);
        bindIntent.setAction(IGpuProcessService.class.getName());
        bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE);
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

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "onServiceConnected()");
            mService = IGpuProcessService.Stub.asInterface(iBinder);
            try {
                mService.hello("Jamie");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "onServiceDisconnected()");
            mService = null;
        }
    };
}