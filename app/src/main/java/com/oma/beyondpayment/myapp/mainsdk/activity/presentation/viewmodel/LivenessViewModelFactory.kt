package com.oma.beyondpayment.facelivelinesssdk.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oma.beyondpayment.camera.domain.model.FaceResult
import com.oma.beyondpayment.core.resourceprovider.ResourcesProvider
import com.oma.beyondpayment.domain.repository.LivenessRepository
import com.oma.beyondpayment.facelivelinesssdk.di.LibraryModule.container
import com.oma.beyondpayment.facelivelinesssdk.domain.usecase.GetStepMessageUseCase


private const val UNKNOWN_VIEW_MODEL = "Unknown ViewModel class"

internal class LivenessViewModelFactory(
    private val resourcesProvider: ResourcesProvider = container.provideResourceProvider(),
    private val livenessRepository: LivenessRepository<FaceResult> = container.provideLivenessRepository(),
    private val getStepMessageUseCase: GetStepMessageUseCase = container.provideGetStepMessagesUseCase()
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LivenessViewModel::class.java)) {
            return LivenessViewModel(
                resourcesProvider,
                livenessRepository,
                getStepMessageUseCase
            ) as T
        }
        throw IllegalArgumentException(UNKNOWN_VIEW_MODEL)
    }
}
