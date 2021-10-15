package me.jamie.gpuprocess;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView {

    public MySurfaceView(Context context, SurfaceHolder.Callback callback) {
        super(context);

        getHolder().addCallback(callback);
    }


}
