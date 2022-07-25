package ru.stroesku.kmm.presentation.ui.base.view.camera

import androidx.camera.core.CameraSelector

enum class CameraMode(val id: Int) {
    Back(CameraSelector.LENS_FACING_BACK),
    Front(CameraSelector.LENS_FACING_FRONT)
}
