package com.oma.beyondpayment.facelivelinesssdk.domain.model

import com.oma.beyondpayment.domain.model.CaptureQualityDomain

enum class CaptureQuality {
    CAPTURE_MODE_MINIMIZE_LATENCY,
    CAPTURE_MODE_MAXIMIZE_QUALITY,
}

fun CaptureQuality.toDomain(): CaptureQualityDomain {
    return when (this) {
        CaptureQuality.CAPTURE_MODE_MINIMIZE_LATENCY ->
            CaptureQualityDomain.CAPTURE_MODE_MINIMIZE_LATENCY
        CaptureQuality.CAPTURE_MODE_MAXIMIZE_QUALITY ->
            CaptureQualityDomain.CAPTURE_MODE_MAXIMIZE_QUALITY
    }
}