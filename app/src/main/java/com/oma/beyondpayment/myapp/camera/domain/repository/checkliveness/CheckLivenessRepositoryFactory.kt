package com.oma.beyondpayment.camera.domain.repository.checkliveness

import com.oma.beyondpayment.camera.domain.model.FaceResult
import com.oma.beyondpayment.core.factory.Factory
import com.oma.beyondpayment.domain.repository.LivenessRepository

object CheckLivenessRepositoryFactory : Factory<LivenessRepository<FaceResult>> {
    override fun create(): LivenessRepository<FaceResult> {
        return LivenessRepositoryImpl()
    }
}