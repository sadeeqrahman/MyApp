package com.oma.beyondpayment.camera.core.processor.luminosity

import com.oma.beyondpayment.camera.core.processor.FrameProcessor
import kotlinx.coroutines.flow.Flow

internal interface LuminosityFrameProcessor : FrameProcessor {
    fun observeLuminosity(): Flow<Double>
}