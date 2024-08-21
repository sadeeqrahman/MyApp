package com.oma.beyondpayment.camera.navigation

import androidx.lifecycle.LifecycleOwner
import com.oma.beyondpayment.camera.CameraX
import com.oma.beyondpayment.camera.core.callback.CameraXCallback
import com.oma.beyondpayment.camera.di.CameraModule
import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.domain.model.CameraSettingsDomain


class CameraXNavigation(private val lifecycleOwner: LifecycleOwner) {

    init { initializeModuleLifecyle(lifecycleOwner) }

    fun provideCameraXModule(
        cameraSettings: CameraSettingsDomain,
        cameraXCallback: CameraXCallback,
    ): CameraX {
        return container.provideCameraX(cameraSettings, cameraXCallback, lifecycleOwner)
    }

    private fun initializeModuleLifecyle(lifecycleOwner: LifecycleOwner) {
        CameraModule.lifecycleOwner = lifecycleOwner
    }
}
