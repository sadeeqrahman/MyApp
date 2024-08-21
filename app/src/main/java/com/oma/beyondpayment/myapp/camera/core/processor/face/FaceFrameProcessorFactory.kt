package com.oma.beyondpayment.camera.core.processor.face

import com.oma.beyondpayment.camera.core.detector.VisionFaceDetector
import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.core.factory.Factory


internal object FaceFrameProcessorFactory : Factory<FaceFrameProcessor> {

    private val detector: VisionFaceDetector = container.provideVisionFaceDetector()

    override fun create(): FaceFrameProcessor {
        return FaceFrameProcessorImpl(container.provideCoroutineScope(), detector)
    }
}