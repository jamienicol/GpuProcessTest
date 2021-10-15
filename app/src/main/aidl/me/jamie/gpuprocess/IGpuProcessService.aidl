// IGpuProcessService.aidl
package me.jamie.gpuprocess;

import android.view.Surface;

interface IGpuProcessService {
    void onSurfaceChanged(in Surface surface, int width, int height);
}
