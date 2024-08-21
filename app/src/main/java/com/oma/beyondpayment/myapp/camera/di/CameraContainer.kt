package com.oma.beyondpayment.camera.di

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.oma.beyondpayment.camera.CameraX
import com.oma.beyondpayment.camera.CameraXImpl
import com.oma.beyondpayment.camera.core.callback.CameraXCallback
import com.oma.beyondpayment.camera.core.detector.VisionFaceDetector
import com.oma.beyondpayment.camera.core.processor.face.FaceFrameProcessor
import com.oma.beyondpayment.camera.core.processor.face.FaceFrameProcessorFactory
import com.oma.beyondpayment.camera.core.processor.luminosity.LuminosityFrameProcessor
import com.oma.beyondpayment.camera.core.processor.luminosity.LuminosityFrameProcessorFactory
import com.oma.beyondpayment.camera.di.CameraModule.application
import com.oma.beyondpayment.camera.domain.repository.file.FileRepositoryFactory
import com.oma.beyondpayment.camera.domain.usecase.EditPhotoUseCaseFactory
import com.oma.beyondpayment.domain.EditPhotoUseCase
import com.oma.beyondpayment.domain.model.CameraSettingsDomain
import com.oma.beyondpayment.domain.model.StorageTypeDomain
import com.oma.beyondpayment.domain.repository.FileRepository
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


internal class CameraContainer {
    fun provideContext(): Context {
        return application.applicationContext
    }

    fun provideCoroutineScope(): CoroutineScope {
        return CameraModule.lifecycleOwner.lifecycleScope
    }

    fun provideExecutorService(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    fun provideLuminosityFrameProcessor(): LuminosityFrameProcessor {
        return LuminosityFrameProcessorFactory.create()
    }

    fun provideFaceFrameProcessor(): FaceFrameProcessor {
        return FaceFrameProcessorFactory.create()
    }

    fun provideVisionFaceDetector(): VisionFaceDetector {
        return VisionFaceDetector()
    }

    fun provideEditPhotoUseCase(): EditPhotoUseCase {
        return EditPhotoUseCaseFactory.create()
    }

    private fun provideFileRepository(storageType: StorageTypeDomain): FileRepository {
        return FileRepositoryFactory.apply { this.storageType = storageType }.create()
    }

    fun provideCameraX(
        cameraSettings: CameraSettingsDomain,
        cameraXCallback: CameraXCallback,
        lifecycleOwner: LifecycleOwner
    ): CameraX {
        return CameraXImpl(
            settings = cameraSettings,
            cameraXCallback = cameraXCallback,
            lifecycleOwner = lifecycleOwner,
            fileRepository = provideFileRepository(cameraSettings.storageType)
        )
    }
}
