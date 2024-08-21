package com.oma.beyondpayment.domain.model

data class LivenessCameraXErrorDomain(
    val message: String,
    val cause: String,
    val exception: Exception
)