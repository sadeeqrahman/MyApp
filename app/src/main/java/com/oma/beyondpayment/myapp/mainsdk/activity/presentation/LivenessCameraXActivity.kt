package com.oma.beyondpayment.myapp.mainsdk.activity.presentation

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oma.beyondpayment.camera.CameraX
import com.oma.beyondpayment.camera.core.callback.CameraXCallback
import com.oma.beyondpayment.camera.core.callback.CameraXCallbackFactory
import com.oma.beyondpayment.camera.di.CameraModule
import com.oma.beyondpayment.camera.navigation.CameraXNavigation
import com.oma.beyondpayment.core.extensions.observeOnce
import com.oma.beyondpayment.core.extensions.orFalse
import com.oma.beyondpayment.core.extensions.snack
import com.oma.beyondpayment.domain.model.PhotoResultDomain
import com.oma.beyondpayment.domain.model.exceptions.LivenessCameraXException
import com.oma.beyondpayment.domain.repository.ResultLivenessRepository
import com.oma.beyondpayment.facelivelinesssdk.di.LibraryModule
import com.oma.beyondpayment.facelivelinesssdk.domain.model.CameraSettings
import com.oma.beyondpayment.facelivelinesssdk.domain.model.toDomain
import com.oma.beyondpayment.facelivelinesssdk.navigation.EXTRAS_LIVENESS_CAMERA_SETTINGS
import com.oma.beyondpayment.facelivelinesssdk.presentation.viewmodel.LivenessViewModel
import com.oma.beyondpayment.facelivelinesssdk.presentation.viewmodel.LivenessViewModelFactory
import com.oma.beyondpayment.myapp.R
import com.oma.beyondpayment.myapp.databinding.ActivityLivenessCameraXactivityBinding
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
class LivenessCameraXActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLivenessCameraXactivityBinding
    private val cameraSettings: CameraSettings by lazy {
        intent?.extras?.getParcelable(EXTRAS_LIVENESS_CAMERA_SETTINGS) ?: CameraSettings()
    }

    private val resultLivenessRepository: ResultLivenessRepository<PhotoResultDomain> by lazy {
        LibraryModule.container.provideResultLivenessRepository()
    }

    private val livenessViewModel: LivenessViewModel by lazy {
        ViewModelProvider(this, LivenessViewModelFactory()).get(LivenessViewModel::class.java)
    }

    private val cameraXCallback: CameraXCallback by lazy {
        CameraXCallbackFactory.apply {
            onImageSavedAction = ::handlePictureSuccess
            onErrorAction = resultLivenessRepository::error
        }.create()
    }

    private val cameraX: CameraX by lazy {
        CameraXNavigation(this).provideCameraXModule(
            cameraSettings.toDomain(),
            cameraXCallback
        )
    }

    private val cameraManifest = Manifest.permission.CAMERA
    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            handleCameraPermission(granted.orFalse())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeModules()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_liveness_camera_xactivity)


        livenessViewModel.setupSteps(cameraSettings.livenessStepList)
        cameraPermission.launch(cameraManifest)
    }

    override fun onStop() {
        super.onStop()
        if (!isFinishing) {
            resultLivenessRepository.error(LivenessCameraXException.ContextSwitchException())
            finish()
        }
    }

    private fun handleCameraPermission(granted: Boolean) {
        when {
            granted -> permissionIsGranted()
            shouldShowRequestPermissionRationale(cameraManifest) -> {
                binding.clRoot.snack(R.string.liveness_camerax_message_permission_denied) {
                    resultLivenessRepository.error(LivenessCameraXException.PermissionDenied())
                    finish()
                }
            }

            else -> binding.clRoot.snack(R.string.liveness_camerax_message_permission_unknown) {
                resultLivenessRepository.error(LivenessCameraXException.PermissionUnknown())
                finish()
            }
        }
    }

    private fun permissionIsGranted() {
        startCamera()
        startObservers()
    }

    private fun startObservers() {
        lifecycle.addObserver(cameraX.getLifecycleObserver())

        livenessViewModel.state.observe(this) { state ->
            binding.tvStepText.text = state.messageLiveness
            binding.cameraCaptureButton.isVisible = state.isButtonEnabled
        }

        livenessViewModel.apply {
            observeFacesDetection(cameraX.observeFaceList())
            observeLuminosity(cameraX.observeLuminosity())
            hasBlinked.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
            hasSmiled.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
            hasGoodLuminosity.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
            hasHeadMovedLeft.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
            hasHeadMovedRight.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
            hasHeadMovedCenter.observeOnce(this@LivenessCameraXActivity) { cameraX.takePicture() }
        }
    }

    private fun startCamera() {
        cameraX.startCamera(binding.viewFinder)

        binding.cameraCaptureButton.setOnClickListener {
            cameraX.takePicture(true)
        }

        binding.overlayView.apply {
            init()
            invalidate()
            isVisible = true
        }

        binding.tvStepText.isVisible = true
    }

    private fun handlePictureSuccess(photoResult: PhotoResultDomain, takenByUser: Boolean) {
        if (takenByUser) {
            val filesPath = cameraX.getAllPictures()
            resultLivenessRepository.success(photoResult, filesPath)
            finish()
        } else {
            Timber.d(photoResult.toString())
        }
    }

    private fun initializeModules() {
        try {
            LibraryModule.initializeDI(application)
            CameraModule.initializeDI(application)
        } catch (e: ClassNotFoundException) {
            Log.e("LivenessCameraXActivity", "CameraModule class not found: ${e.message}")
        } catch (e: NoClassDefFoundError) {
            Log.e("LivenessCameraXActivity", "CameraModule could not be initialized: ${e.message}")
        }
    }
}