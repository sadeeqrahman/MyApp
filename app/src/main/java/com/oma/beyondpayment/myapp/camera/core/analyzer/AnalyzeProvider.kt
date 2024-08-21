package com.oma.beyondpayment.omafacelivelinesssdk.camera.core.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.lifecycleScope
import com.oma.beyondpayment.camera.core.processor.face.FaceFrameProcessor
import com.oma.beyondpayment.camera.core.processor.luminosity.LuminosityFrameProcessor
import com.oma.beyondpayment.camera.di.CameraModule.container
import com.oma.beyondpayment.camera.di.CameraModule.lifecycleOwner
import com.oma.beyondpayment.domain.model.AnalyzeTypeDomain


internal class AnalyzeProvider {

    class Builder() {
        var analyzeType = AnalyzeTypeDomain.FACE_PROCESSOR
        var faceFrameProcessor: FaceFrameProcessor = container.provideFaceFrameProcessor()
        var cameraExecutors = container.provideExecutorService()
        var luminosityFrameProcessor: LuminosityFrameProcessor =
            container.provideLuminosityFrameProcessor()

        private fun getAnalyzerType() =
            when (analyzeType) {
                AnalyzeTypeDomain.FACE_PROCESSOR -> CameraXAnalyzer(
                    lifecycleOwner.lifecycleScope
                ).apply {
                    attachProcessor(
                        faceFrameProcessor
                    )
                }
                AnalyzeTypeDomain.LUMINOSITY -> CameraXAnalyzer(
                    lifecycleOwner.lifecycleScope
                ).apply {
                    attachProcessor(
                        luminosityFrameProcessor
                    )
                }
                AnalyzeTypeDomain.COMPLETE -> CameraXAnalyzer(
                    lifecycleOwner.lifecycleScope
                ).apply {
                    attachProcessor(
                        luminosityFrameProcessor,
                        faceFrameProcessor
                    )
                }
            }

        fun build(): ImageAnalysis {
            return ImageAnalysis.Builder()
                .build()
                .also { imageAnalysis ->
                    imageAnalysis.setAnalyzer(
                        cameraExecutors,
                        getAnalyzerType()
                    )
                }
        }
    }
}
