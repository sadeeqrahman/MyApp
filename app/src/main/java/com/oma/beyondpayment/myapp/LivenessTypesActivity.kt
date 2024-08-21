package com.oma.beyondpayment.myapp

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.chip.Chip
import com.oma.beyondpayment.facelivelinesssdk.adapter.ImageListAdapter
import com.oma.beyondpayment.facelivelinesssdk.domain.model.CameraSettings
import com.oma.beyondpayment.facelivelinesssdk.domain.model.StepLiveness
import com.oma.beyondpayment.facelivelinesssdk.domain.model.StorageType
import com.oma.beyondpayment.facelivelinesssdk.navigation.LivenessEntryPoint
import com.oma.beyondpayment.myapp.databinding.ActivityLivelinessTypeBinding


class LivenessTypesActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityLivelinessTypeBinding
    private val livenessEntryPoint = LivenessEntryPoint
    private val mutableStepList = arrayListOf<StepLiveness>()
    private val imageListAdapter = ImageListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_liveliness_type)


        _binding.rvImageList.adapter = imageListAdapter

        launchLivenessActivity()
    }

    private fun launchLivenessActivity() {
        _binding.btnStartLiveness.setOnClickListener {
            getSelectedSteps()
            when (mutableStepList.isEmpty()) {
                true -> showToast()
                false ->
                    livenessEntryPoint.startLiveness(
                        cameraSettings = CameraSettings(
                            livenessStepList = mutableStepList,
                            storageType = StorageType.INTERNAL
                        ),
                        context = this,
                    ) { livenessCameraXResult ->
                        if (livenessCameraXResult.error == null) {
                            val listOfImages = arrayListOf<ByteArray>().apply {
                                livenessCameraXResult.createdBySteps?.let { photoResultList ->
                                    this.addAll(
                                        photoResultList.map {
                                            Base64.decode(it.fileBase64, Base64.NO_WRAP)
                                        }
                                    )
                                }
                            }

                            imageListAdapter.imageList = listOfImages
                            _binding.tvListResult.text =
                                getString(
                                    R.string.result_list,
                                    listOfImages.size.toString()
                                )
                            _binding.groupResultList.isVisible = true
                        } else {
                            livenessCameraXResult.error?.let {
                                Log.e(
                                    this.localClassName,
                                    it.toString()
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun getSelectedSteps() {
        mutableStepList.clear()
        _binding.chipGroupSteps.forEach { chip ->
            (chip as Chip).takeIf { it.isChecked }?.let {
                mutableStepList.add(it.mapToLivenessStep())
            }
        }
    }

    private fun View.mapToLivenessStep(): StepLiveness {
        return when (this.id) {
            R.id.chipStepSmile -> StepLiveness.STEP_SMILE
            R.id.chipStepBlink -> StepLiveness.STEP_BLINK
            R.id.chipStepMovementLeft -> StepLiveness.STEP_HEAD_LEFT
            R.id.chipStepMovementRight -> StepLiveness.STEP_HEAD_RIGHT
            else -> StepLiveness.STEP_LUMINOSITY
        }
    }

    private fun showToast() {
        Toast.makeText(
            this,
            "You need to select at least one step",
            Toast.LENGTH_LONG
        ).show()
    }
}