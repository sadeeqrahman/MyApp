package com.oma.beyondpayment.camera.core.processor.face

import com.oma.beyondpayment.camera.core.processor.FrameProcessor
import com.oma.beyondpayment.camera.domain.model.FaceResult
import kotlinx.coroutines.flow.Flow

internal interface FaceFrameProcessor : FrameProcessor {
    fun observeFaceList(): Flow<List<FaceResult>>
}