package com.oma.beyondpayment.camera.core.callback

import com.oma.beyondpayment.core.factory.Factory
import com.oma.beyondpayment.domain.model.PhotoResultDomain


object CameraXCallbackFactory : Factory<CameraXCallback> {

    var onImageSavedAction: (PhotoResultDomain, Boolean) -> Unit = { _, _ -> }
    var onErrorAction: (Exception) -> Unit = {}

    override fun create(): CameraXCallback {
        return CameraXCallbackImpl(onImageSavedAction, onErrorAction)
    }
}