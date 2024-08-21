package com.oma.beyondpayment.facelivelinesssdk.domain.model

import com.oma.beyondpayment.domain.model.StorageTypeDomain

enum class StorageType {
    INTERNAL,
    EXTERNAL
}

fun StorageType.toDomain(): StorageTypeDomain {
    return when (this) {
        StorageType.INTERNAL -> StorageTypeDomain.INTERNAL
        StorageType.EXTERNAL -> StorageTypeDomain.EXTERNAL
    }
}