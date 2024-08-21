package com.oma.beyondpayment.camera.domain.repository.resultliveness

import com.oma.beyondpayment.core.factory.Factory
import com.oma.beyondpayment.domain.model.LivenessCameraXResultDomain
import com.oma.beyondpayment.domain.model.PhotoResultDomain
import com.oma.beyondpayment.domain.repository.ResultLivenessRepository

object ResultLivenessRepositoryFactory : Factory<ResultLivenessRepository<PhotoResultDomain>> {

    var resultCallback: (LivenessCameraXResultDomain) -> Unit = { }

    override fun create(): ResultLivenessRepository<PhotoResultDomain> {
        return ResultLivenessRepositoryImpl(resultCallback)
    }
}