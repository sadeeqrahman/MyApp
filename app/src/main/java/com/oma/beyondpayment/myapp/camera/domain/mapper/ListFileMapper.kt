package com.oma.beyondpayment.camera.domain.mapper

import com.oma.beyondpayment.core.extensions.encoderFilePath
import com.oma.beyondpayment.core.extensions.getFileNameWithoutExtension
import com.oma.beyondpayment.domain.model.PhotoResultDomain

internal fun List<String>.toPhotoResult(): List<PhotoResultDomain> {
    return this.map {
        PhotoResultDomain(
            createdAt = it.getFileNameWithoutExtension(),
            fileBase64 = it.encoderFilePath()
        )
    }
}