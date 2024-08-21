package com.oma.beyondpayment.camera.domain.usecase

import android.content.Context
import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.core.factory.Factory
import com.oma.beyondpayment.domain.EditPhotoUseCase

internal object EditPhotoUseCaseFactory : Factory<EditPhotoUseCase> {

    private val context: Context by lazy { container.provideContext() }

    override fun create(): EditPhotoUseCase {
        return EditPhotoUseCaseImpl(context)
    }
}