package com.oma.beyondpayment.camera.core.callback

import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.core.extensions.encoderFilePath
import com.oma.beyondpayment.domain.EditPhotoUseCase
import com.oma.beyondpayment.domain.model.PhotoResultDomain
import java.io.File


internal class CameraXCallbackImpl(
    private val onImageSavedAction: (PhotoResultDomain, Boolean) -> Unit,
    private val onErrorAction: (Exception) -> Unit,
    private val editPhotoUseCase: EditPhotoUseCase = container.provideEditPhotoUseCase()
) : CameraXCallback {

    override fun onSuccess(photoFile: File, takenByUser: Boolean) {
        editPhotoUseCase.editPhotoFile(photoFile)
        val photoResult = PhotoResultDomain(
            createdAt = photoFile.name,
            fileBase64 = photoFile.path.encoderFilePath()
        )
        onImageSavedAction(photoResult, takenByUser)
    }

    override fun onError(exception: Exception) {
        onErrorAction(exception)
    }
}
