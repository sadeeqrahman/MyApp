package com.oma.beyondpayment.facelivelinesssdk.domain.model

data class LivenessCameraXResult(
    val createdByUser: PhotoResult? = null,
    val createdBySteps: List<PhotoResult>? = null,
    val error: LivenessCameraXError? = null,
)