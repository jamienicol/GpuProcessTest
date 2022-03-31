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
    SurfaceHolder mSurfaceHolder;
    int mFormat;
    int mWidth, mHeight;

    IGpuProcessService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate()");

        Log.d("MainActivity", "Starting GPU process service");
        Intent startIntent = new Intent(MainActivity.this, GpuProcessService.class);
        startService(startIntent);
        Intent bindIntent = new Intent(MainActivity.this, GpuProcessService.class);
        bindIntent.setAction(IGpuProcessService.class.getName());
        bindService(bindIntent, mConnection, Context.BIND_AUTO_CREATE);

        super.onCreate(savedInstanceState);
        mSurfaceView = new MySurfaceView(this, this);
        setContentView(mSurfaceView);
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

        mSurfaceHolder = surfaceHolder;
        mWidth = width;
        mHeight = height;

        try {
            if (mService != null) {
                mService.onSurfaceChanged(surfaceHolder.getSurface(), width, height);
            } else {
                Log.d("MainActivity", "Not connected to GpuProcessService");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        Log.d("MainActivity", "surfaceDestroyed()");
        if (mService != null) {
            try {
                mService.onSurfaceChanged(null, 0, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("MainActivity", "Not connected to GpuProcessService");
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("MainActivity", "onServiceConnected()");
            mService = IGpuProcessService.Stub.asInterface(iBinder);
            if (mSurfaceHolder != null) {
                try {
                    mService.onSurfaceChanged(mSurfaceHolder.getSurface(), mWidth, mHeight);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("MainActivity", "onServiceDisconnected()");
            mService = null;
        }
    };
}