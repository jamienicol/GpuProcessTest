// IGpuProcessService.aidl
package me.jamie.gpuprocess;

import android.view.SurfaceControl;

interface IGpuProcessService {
    void onSurfaceChanged(in SurfaceControl surface, int width, int height);
}
