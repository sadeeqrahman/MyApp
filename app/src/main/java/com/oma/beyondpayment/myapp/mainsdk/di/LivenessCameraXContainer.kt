package com.oma.beyondpayment.facelivelinesssdk.di

import android.app.Application
import android.content.Context
import com.oma.beyondpayment.camera.domain.model.FaceResult
import com.oma.beyondpayment.camera.domain.repository.checkliveness.CheckLivenessRepositoryFactory
import com.oma.beyondpayment.camera.domain.repository.resultliveness.ResultLivenessRepositoryFactory
import com.oma.beyondpayment.core.resourceprovider.ResourcesProvider
import com.oma.beyondpayment.core.resourceprovider.ResourcesProviderFactory
import com.oma.beyondpayment.domain.model.PhotoResultDomain
import com.oma.beyondpayment.domain.repository.LivenessRepository
import com.oma.beyondpayment.domain.repository.ResultLivenessRepository
import com.oma.beyondpayment.facelivelinesssdk.domain.usecase.GetStepMessageUseCase
import com.oma.beyondpayment.facelivelinesssdk.navigation.LivenessEntryPoint


internal class LivenessCameraXContainer(private val application: Application) {
    private val provideLivenessEntryPoint = LivenessEntryPoint

    private fun provideContext(): Context {
        return application.applicationContext
    }

    fun provideResourceProvider(): ResourcesProvider {
        return ResourcesProviderFactory(provideContext()).create()
    }

    fun provideResultLivenessRepository(): ResultLivenessRepository<PhotoResultDomain> {
        return ResultLivenessRepositoryFactory.apply {
            resultCallback = provideLivenessEntryPoint::postResultCallback
        }.create()
    }

    fun provideLivenessRepository(): LivenessRepository<FaceResult> {
        return CheckLivenessRepositoryFactory.create()
    }

    fun provideGetStepMessagesUseCase(
        resourcesProvider: ResourcesProvider = provideResourceProvider()
    ): GetStepMessageUseCase {
        return GetStepMessageUseCase(resourcesProvider)
    }
}
