package com.oma.beyondpayment.facelivelinesssdk.domain.usecase

import com.oma.beyondpayment.core.resourceprovider.ResourcesProvider
import com.oma.beyondpayment.domain.model.StepLivenessDomain
import com.oma.beyondpayment.myapp.R


class GetStepMessageUseCase(private val resourcesProvider: ResourcesProvider) {

    operator fun invoke(requestedSteps: List<StepLivenessDomain>): String {
        requestedSteps.let {
            if (it.isNullOrEmpty()) {
                return resourcesProvider.getString(R.string.liveness_camerax_step_completed)
            }

            return when (it.first()) {
                StepLivenessDomain.STEP_LUMINOSITY -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_luminosity)
                }
                StepLivenessDomain.STEP_HEAD_FRONTAL -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_head_frontal)
                }
                StepLivenessDomain.STEP_HEAD_RIGHT -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_head_left)
                }
                StepLivenessDomain.STEP_HEAD_LEFT -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_head_right)
                }
                StepLivenessDomain.STEP_SMILE -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_smile)
                }
                StepLivenessDomain.STEP_BLINK -> {
                    resourcesProvider.getString(R.string.liveness_camerax_step_blink_eyes)
                }
                null -> resourcesProvider.getString(R.string.liveness_camerax_step_completed)
            }
        }
    }
}
