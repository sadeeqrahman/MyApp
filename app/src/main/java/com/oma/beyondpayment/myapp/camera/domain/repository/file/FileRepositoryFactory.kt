package com.oma.beyondpayment.camera.domain.repository.file

import android.content.Context
import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.core.factory.Factory
import com.oma.beyondpayment.domain.model.StorageTypeDomain
import com.oma.beyondpayment.domain.repository.FileRepository

internal object FileRepositoryFactory : Factory<FileRepository> {

    private val context: Context by lazy { container.provideContext() }
    var storageType: StorageTypeDomain = StorageTypeDomain.INTERNAL

    override fun create(): FileRepository {
        return FileRepositoryImpl(storageType, context)
    }
}