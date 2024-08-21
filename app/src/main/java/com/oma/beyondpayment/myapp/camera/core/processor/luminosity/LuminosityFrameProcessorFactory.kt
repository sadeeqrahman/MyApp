package com.oma.beyondpayment.camera.core.processor.luminosity

import com.oma.beyondpayment.core.factory.Factory

internal object LuminosityFrameProcessorFactory : Factory<LuminosityFrameProcessor> {
    override fun create(): LuminosityFrameProcessor {
        return LuminosityFrameProcessorImpl()
    }
}
