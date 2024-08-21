package com.oma.beyondpayment.facelivelinesssdk.domain.mapper

import com.oma.beyondpayment.domain.model.PhotoResultDomain
import com.oma.beyondpayment.facelivelinesssdk.domain.model.PhotoResult

fun PhotoResultDomain.toPresentation(): PhotoResult {
    return PhotoResult(createdAt = this.createdAt, fileBase64 = this.fileBase64)
}