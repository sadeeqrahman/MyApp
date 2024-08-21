package com.oma.beyondpayment.camera.domain.repository.resultliveness

import com.oma.beyondpayment.camera.domain.mapper.toPhotoResult
import com.oma.beyondpayment.domain.model.LivenessCameraXResultDomain
import com.oma.beyondpayment.domain.model.PhotoResultDomain
import com.oma.beyondpayment.domain.repository.ResultLivenessRepository


internal class ResultLivenessRepositoryImpl(
    private val resultCallback: (LivenessCameraXResultDomain) -> Unit
) : ResultLivenessRepository<PhotoResultDomain> {

    override fun success(photoResult: PhotoResultDomain, filesPath: List<String>) {
        val livenessCameraXResult =
            LivenessCameraXResultDomain(photoResult, filesPath.toPhotoResult())
        resultCallback(livenessCameraXResult)
    }

    override fun error(exception: Exception) {
        val livenessCameraXResult = LivenessCameraXResultDomain(exception)
        resultCallback(livenessCameraXResult)
    }
}