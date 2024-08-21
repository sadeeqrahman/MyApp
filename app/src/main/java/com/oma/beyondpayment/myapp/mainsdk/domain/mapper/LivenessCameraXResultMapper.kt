package com.oma.beyondpayment.facelivelinesssdk.domain.mapper

import com.oma.beyondpayment.domain.model.LivenessCameraXResultDomain
import com.oma.beyondpayment.facelivelinesssdk.domain.model.LivenessCameraXResult

fun LivenessCameraXResultDomain.toPresentation(): LivenessCameraXResult {
    return LivenessCameraXResult(
        createdByUser = this.createdByUser?.toPresentation(),
        createdBySteps = this.createdBySteps?.map { it.toPresentation() },
        error = this.error?.toPresentation()
    )
}