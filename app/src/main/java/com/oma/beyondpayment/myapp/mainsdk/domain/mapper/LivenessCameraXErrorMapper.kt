package com.oma.beyondpayment.facelivelinesssdk.domain.mapper

import com.oma.beyondpayment.domain.model.LivenessCameraXErrorDomain
import com.oma.beyondpayment.facelivelinesssdk.domain.model.LivenessCameraXError

fun LivenessCameraXErrorDomain.toPresentation(): LivenessCameraXError {
    return LivenessCameraXError(
        message = this.message,
        cause = this.cause,
        exception = this.exception
    )
}